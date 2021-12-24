(ns fancoil.module.cljs-ajax.plugin
  (:require
   [cljs.core.async :refer [go]]
   [fancoil.base :as base]
   [ajax.core :refer [json-request-format json-response-format GET POST]]
   [ajax.simple :refer [ajax-request]]))


(defmethod base/do! :ajax/get
  [config _ effect]
  (go
    (let [{:keys [uri opt callback] :or {opt {}}} effect
          callback-handler (if (fn? callback)
                             callback
                             (fn [response]
                               (let [req #:request {:method callback
                                                    :event response}]
                                 (base/do! config :dispatch/request req))))
          opt (assoc opt :handler callback-handler)]
      (GET uri opt))))

(defmethod base/do! :ajax/post
  [config _ effect]
  (go
    (let [{:keys [uri opt callback] :or {opt {}}} effect
          callback-handler (if (fn? callback)
                             callback
                             (fn [response]
                               (let [req #:request {:method callback
                                                    :event response}]
                                 (base/do! config :dispatch/request req))))
          opt (assoc opt :handler callback-handler)]
      (POST uri opt))))


(def default-request
  {:format          (json-request-format)
   :response-format (json-response-format {:keywords? true})})


(defmethod base/do! :ajax/request
  [config _ effect]
  (go
    (let [{:keys [request callback]} effect
          callback-fn (if (fn? callback)
                        callback
                        (fn [[ok response]]
                          (if ok
                            (let [req #:request {:method callback
                                                 :event response}]
                              (base/do! config :dispatch/request req))
                            (js/console.error (str response)))))
          merged-request (->
                          (merge default-request request)
                          (assoc :handler callback-fn))]
      (ajax-request merged-request))))


