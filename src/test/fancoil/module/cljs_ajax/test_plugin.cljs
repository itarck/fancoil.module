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


;; GET

(comment
  (base/do! {} :ajax/get {:uri (endpoint "articles")
                          :opt {:keywords? true}
                          :callback prn})
  )

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
 
 
 (base/do! {} :ajax/post {:uri (endpoint "articles")
                          :opt {:params {:article
                                         {:title "How to train your dragon"
                                          :description "Ever wonder how?"
                                          :body "You have to believe"
                                          :tagList ["reactjs", "angularjs", "dragons"]}}
                                :headers {:Authorization "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJpdGFyY2s0IiwiaXNzIjoiaXRhcmNrNEBnbWFpbC5jb20iLCJleHAiOjE2NDA5NDI0NzQsImlhdCI6MTY0MDMzNzY3NH0.7rT05GPxwkUQ8ClsGLhhKlZB7LueDxizI46XuhaZEQI"}
                                :format :json}
                          :callback prn})
 
;;   {"article" {"updatedAt" "2021-12-24T10:30:30.806Z", "body" "You have to believe", "createdAt" "2021-12-24T10:30:30.806Z", "author" {"username" "itarck4", "bio" nil, "image" nil, "following" false}, "favorited" false, "id" 5, "slug" "how-to-train-your-dragon-1640341830806", "tagList" ["angularjs" "dragons" "reactjs"], "favoritesCount" 0, "title" "How to train your dragon", "description" "Ever wonder how?"}}
  
  )


;; PUT
(comment 
  
  (base/do! {} :ajax/put {:uri (endpoint "articles" "how-to-train-your-dragon-1640341830806")
                          :opt {:params {:article
                                         {:description "XXXXXXX"}}
                                :headers {:Authorization "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJpdGFyY2s0IiwiaXNzIjoiaXRhcmNrNEBnbWFpbC5jb20iLCJleHAiOjE2NDA5NDI0NzQsImlhdCI6MTY0MDMzNzY3NH0.7rT05GPxwkUQ8ClsGLhhKlZB7LueDxizI46XuhaZEQI"}
                                :format :json}
                          :callback prn})
  ;; => #object[cljs.core.async.impl.channels.ManyToManyChannel]
;; {"article" {"updatedAt" "2021-12-24T10:35:40.768Z", "body" "You have to believe", "createdAt" "2021-12-24T10:30:30.806Z", "author" {"username" "itarck4", "bio" nil, "image" nil, "following" false}, "favorited" false, "id" 5, "slug" "how-to-train-your-dragon-1640341830806", "tagList" ["angularjs" "dragons" "reactjs"], "favoritesCount" 0, "title" "How to train your dragon", "description" "XXXXXXX"}}

  )


;; DELETE
(comment

  (base/do! {} :ajax/delete {:uri (endpoint "articles" "how-to-train-your-dragon-1640341830806")
                             :opt {:headers {:Authorization "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJpdGFyY2s0IiwiaXNzIjoiaXRhcmNrNEBnbWFpbC5jb20iLCJleHAiOjE2NDA5NDI0NzQsImlhdCI6MTY0MDMzNzY3NH0.7rT05GPxwkUQ8ClsGLhhKlZB7LueDxizI46XuhaZEQI"}}
                             :callback prn})
  ;; => #object[cljs.core.async.impl.channels.ManyToManyChannel]


  )

