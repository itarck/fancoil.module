(ns fancoil.module.reitit.html-router.unit
  (:require
   [reagent.core :as r]
   [integrant.core :as ig]
   [reitit.frontend :as rfront]
   [clerk.core :as clerk]
   [accountant.core :as accountant]
   [fancoil.module.reitit.html-router.base :as reitit.base]
   [fancoil.module.reitit.html-router.plugin]))

;; -------------------------
;; Routes


(defmethod ig/init-key :reitit/html-router
  [_ {:keys [routes dispatch on-navigate-request]}]
  (let [reitit-router (rfront/router routes)
        router-atom (r/atom {})
        router-core {:reitit-router reitit-router
                     :router-atom router-atom}]
    (clerk/initialize!)
    (accountant/configure-navigation!
     {:nav-handler
      (fn [path]
        (let [match (rfront/match-by-path reitit-router path)
              page-name (:name (:data  match))
              current-route {:page-name page-name
                             :page-path path
                             :path-params (:path-params match)}]
          (r/after-render clerk/after-render!)
          (swap! router-atom assoc :current-route current-route)
          (when on-navigate-request
            (dispatch on-navigate-request current-route))
          (clerk/navigate-page! path)))
      :path-exists?
      (fn [path]
        (boolean (rfront/match-by-path reitit-router path)))})
    (accountant/dispatch-current!)
    (partial reitit.base/html-router router-core)))
