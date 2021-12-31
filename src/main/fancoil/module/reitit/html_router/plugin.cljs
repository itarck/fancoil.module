(ns fancoil.module.reitit.html-router.plugin
  (:require
   [reagent.core :as r]
   [reitit.frontend :as rfront]
   [accountant.core :as accountant]
   [fancoil.base :as fancoil.base]
   [fancoil.module.reitit.html-router.base :as router.base]))

;; plugin 

(defmethod router.base/html-router :router/navigate-path
  [core _ path]
  (accountant/navigate! path))

(defmethod router.base/html-router :router/path-for
  [core _ page-name & [params]]
  (let [reitit-router (:reitit-router core)]
    (if params
      (:path (rfront/match-by-name reitit-router page-name params))
      (:path (rfront/match-by-name reitit-router page-name)))))

(defmethod router.base/html-router :router/current-route-atom
  [core _ _]
  (r/cursor (:router-atom core) [:current-route]))


;; plugin for fancoil

(defmethod fancoil.base/inject :reitit.html-router/route
  [{:keys [router]} _ request]
  (let [current-route @(router :current-route-atom)]
    (assoc request :router/route current-route)))

(defmethod fancoil.base/do! :reitit.html-router/navigate
  [{:keys [router]} _ {:keys [page-name params]}]
  (let [path (router :path-for page-name params)]
    (accountant/navigate! path)))
