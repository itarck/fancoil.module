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


(defn make-callback-fn [core callback-keyword]
  (fn [response]
    (let [req #:request {:method callback-keyword
                         :event response}]
      (base/do! core :dispatch/request req))))

;; base functions

(defmethod base/do! :ajax/request
  [core _ effect]
  (go
    (let [{:keys [request callback]} effect
          callback-fn (if (fn? callback)
                        callback
                        (fn [[ok response]]
                          (if ok
                            (let [req #:request {:method callback
                                                 :event response}]
                              (base/do! core :dispatch/request req))
                            (js/console.error (str response)))))
          merged-request (->
                          (merge default-request request)
                          (assoc :handler callback-fn))]
      (ajax-request merged-request))))


(defmethod base/do! :ajax/get
  [core _ effect]
  (go
    (let [{:keys [uri opt callback] :or {opt {}}} effect
          callback-handler (if (fn? callback)
                             callback
                             (make-callback-fn core callback))
          opt (assoc opt :handler callback-handler)]
      (GET uri opt))))

(defmethod base/do! :ajax/post
  [core _ effect]
  (go
    (let [{:keys [uri opt callback] :or {opt {}}} effect
          callback-handler (if (fn? callback)
                             callback
                             (make-callback-fn core callback))
          opt (assoc opt :handler callback-handler)]
      (POST uri opt))))

(defmethod base/do! :ajax/put
  [core _ effect]
  (go
    (let [{:keys [uri opt callback] :or {opt {}}} effect
          callback-handler (if (fn? callback)
                             callback
                             (make-callback-fn core callback))
          opt (assoc opt :handler callback-handler)]
      (PUT uri opt))))


(defmethod base/do! :ajax/delete
  [core _ effect]
  (go
    (let [{:keys [uri opt callback] :or {opt {}}} effect
          callback-handler (if (fn? callback)
                             callback
                             (make-callback-fn core callback))
          opt (assoc opt :handler callback-handler)]
      (DELETE uri opt))))




