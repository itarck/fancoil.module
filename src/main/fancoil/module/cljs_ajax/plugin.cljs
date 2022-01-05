(ns fancoil.module.cljs-ajax.plugin
  (:require
   [cljs.core.async :refer [go]]
   [fancoil.base :as base]
   [ajax.core :refer [json-request-format json-response-format GET POST PUT DELETE]]
   [ajax.simple :refer [ajax-request]]))


;; helers

(def default-request 
  {:format          (json-request-format)
   :response-format (json-response-format {:keywords? true})})


(defn make-handler-from-keyword [core dispatch-keyword]
  (fn [response]
    (let [req {:request/method dispatch-keyword
               :ajax/response response}]
      (base/do! core :dispatch/request req))))

(defn make-handler-from-request [core request]
  (fn [response]
    (let [req (assoc request :ajax/response response)]
      (base/do! core :dispatch/request req))))

(defn make-handler-from-requests [core requests]
  (fn [response]
    (let [ajax-resp-injected-requests (mapv (fn [req]
                                              (if (keyword? req)
                                                {:request/method req
                                                 :ajax/response response}
                                                (assoc req :ajax/response response)))
                                            requests)]
      (base/do! core :dispatch/requests ajax-resp-injected-requests))))

;; base functions

(defmethod base/do! :ajax/request
  [core _ effect]
  (go
    (let [{:keys [request on-success on-failure] :or {on-failure #(js/console.error %)}} effect
          on-success-handler (cond
                               (fn? on-success) on-success
                               (keyword? on-success) (make-handler-from-keyword core on-success)
                               (map? on-success) (make-handler-from-request core on-success)
                               (vector? on-success) (make-handler-from-requests core on-success)
                               (nil? on-success) (js/console.error "You should add a on-success dispatch keyword for :ajax/request")
                               :else (js/console.error "on-success should be a keyword"))
          on-failure-handler (cond
                               (fn? on-failure) on-failure
                               (keyword? on-failure) (make-handler-from-keyword core on-failure)
                               (map? on-failure) (make-handler-from-request core on-failure)
                               (vector? on-failure) (make-handler-from-requests core on-failure)
                               (nil? on-failure) (js/console.error "You should add a on-success dispatch keyword for :ajax/request")
                               :else (js/console.error "on-failure should be a keyword"))
          handler (fn [[ok response]]
                    (if ok
                      (on-success-handler response)
                      (on-failure-handler response)))
          merged-request (->
                          (merge default-request request)
                          (assoc :handler handler))]
      (ajax-request merged-request))))


(derive :ajax/get :ajax/easy-request)
(derive :ajax/post :ajax/easy-request)
(derive :ajax/put :ajax/easy-request)
(derive :ajax/delete :ajax/easy-request)

(defmethod base/do! :ajax/easy-request
  [core method effect]
  (go
    (let [{:keys [uri opt on-success on-failure] 
           :or {opt {} on-failure #(js/console.error %)}
           on-finally :finally} effect
          on-success-handler (cond
                               (fn? on-success) on-success
                               (keyword? on-success) (make-handler-from-keyword core on-success)
                               (map? on-success) (make-handler-from-request core on-success)
                               (vector? on-success) (make-handler-from-requests core on-success)
                               (nil? on-success) (js/console.error "You should add a on-success dispatch keyword for :ajax/request")
                               :else (js/console.error "on-success should be a keyword"))
          on-failure-handler (cond
                               (fn? on-failure) on-failure
                               (keyword? on-failure) (make-handler-from-keyword core on-failure)
                               (map? on-failure) (make-handler-from-request core on-failure)
                               (vector? on-failure) (make-handler-from-requests core on-failure)
                               (nil? on-failure) (js/console.error "You should add a on-success dispatch keyword for :ajax/request")
                               :else (js/console.error "on-failure should be a keyword"))
          on-finally-handler (cond
                               (fn? on-finally) on-finally
                               (keyword? on-finally) #(let [req {:request/method on-finally}]
                                                       (base/do! core :dispatch/request req))
                               (map? on-finally) #(base/do! core :dispatch/request on-finally)
                               :else nil)
          opt (-> opt
                  (assoc :handler on-success-handler
                         :error-handler on-failure-handler)
                  ((fn [opt]
                    (if on-finally-handler
                      (assoc opt :finally on-finally-handler)
                      opt))))]
      (case method
        :ajax/get (GET uri opt)
        :ajax/post (POST uri opt)
        :ajax/put (PUT uri opt)
        :ajax/delete (DELETE uri opt)))))
