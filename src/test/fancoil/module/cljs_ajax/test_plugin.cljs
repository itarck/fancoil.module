(ns fancoil.module.cljs-ajax.test-plugin
  (:require
   [ajax.core :refer [json-request-format json-response-format GET POST]]
   [clojure.string :as str]
   [fancoil.base :as base]
   [fancoil.module.cljs-ajax.plugin]))


(def api-url "http://localhost:6003/api")

(defn endpoint
  "Concat any params to api-url separated by /"
  [& params]
  (str/join "/" (cons api-url params)))


;; GET

(comment
  (base/do! {} :ajax/get {:uri (endpoint "articles")
                          :opt {:keywords? true}
                          :callback prn}))

;; POST

(comment

  (base/do! {} :ajax/post {:uri (endpoint "users")
                           :opt {:params {:user {:username "Jake16"
                                                 :email "jake16@gmail.com"
                                                 :password "jakejake"}}
                                 :format :json}
                           :callback prn})
  ;; => #object[cljs.core.async.impl.channels.ManyToManyChannel]
;;   {"user" {"id" 16, "email" "jake16@gmail.com", "username" "Jake16", "image" nil, "bio" nil, "token" "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJKYWtlMTYiLCJpc3MiOiJqYWtlMTZAZ21haWwuY29tIiwiZXhwIjoxNjQwOTQ2MDIyLCJpYXQiOjE2NDAzNDEyMjJ9.0vTMqxuJwJMLNmfg57aUF_c307dymzpra555-_165L0"}}
  )

;; request

(comment
  (base/do! {} :ajax/request {:request {:method          :post
                                        :uri             (endpoint "users")     ;; evaluates to "api/users"
                                        :params          {:user {:username "Jake17"
                                                                 :email "Jake17@gmail.com"
                                                                 :password "jakejake"}}   ;; {:user {:username ... :email ... :password ...}}
                                        :format          (json-request-format)  ;; make sure it's json
                                        :response-format (json-response-format {:keywords? true}) ;; json response and all keys to keywords
                                        }
                              :callback prn})
  ;; => #object[cljs.core.async.impl.channels.ManyToManyChannel]
;; [true {:user {:id 17, :email "Jake17@gmail.com", :username "Jake17", :image nil, :bio nil, :token "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJKYWtlMTciLCJpc3MiOiJKYWtlMTdAZ21haWwuY29tIiwiZXhwIjoxNjQwOTQ2MDUzLCJpYXQiOjE2NDAzNDEyNTN9.l3E2mzjVNDrywR58F6M2sVAfHnYGG-RUaNiIJHsClZE"}}]
  )