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


(defn make-on-success-handler [core on-success-keyword]
  (fn [response]
    (let [req #:request {:method on-success-keyword
                         :event response}]
      (base/do! core :dispatch/request req))))

;; base functions

(defmethod base/do! :ajax/request
  [core _ effect]
  (go
    (let [{:keys [request on-success]} effect
          on-success-fn (if (fn? on-success)
                          on-success
                          (fn [[ok response]]
                            (if ok
                              (let [req #:request {:method on-success
                                                   :event response}]
                                (base/do! core :dispatch/request req))
                              (js/console.error (str response)))))
          merged-request (->
                          (merge default-request request)
                          (assoc :handler on-success-fn))]
      (ajax-request merged-request))))


(defmethod base/do! :ajax/get
  [core _ effect]
  (go
    (let [{:keys [uri opt on-success] :or {opt {}}} effect
          on-success-handler (if (fn? on-success)
                             on-success
                             (make-on-success-handler core on-success))
          opt (assoc opt :handler on-success-handler)]
      (GET uri opt))))

(defmethod base/do! :ajax/post
  [core _ effect]
  (go
    (let [{:keys [uri opt on-success] :or {opt {}}} effect
          on-success-handler (if (fn? on-success)
                             on-success
                             (make-on-success-handler core on-success))
          opt (assoc opt :handler on-success-handler)]
      (POST uri opt))))

(defmethod base/do! :ajax/put
  [core _ effect]
  (go
    (let [{:keys [uri opt on-success] :or {opt {}}} effect
          on-success-handler (if (fn? on-success)
                             on-success
                             (make-on-success-handler core on-success))
          opt (assoc opt :handler on-success-handler)]
      (PUT uri opt))))


(defmethod base/do! :ajax/delete
  [core _ effect]
  (go
    (let [{:keys [uri opt on-success] :or {opt {}}} effect
          on-success-handler (if (fn? on-success)
                             on-success
                             (make-on-success-handler core on-success))
          opt (assoc opt :handler on-success-handler)]
      (DELETE uri opt))))




