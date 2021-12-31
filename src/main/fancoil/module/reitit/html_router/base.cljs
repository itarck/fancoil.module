(ns fancoil.module.reitit.html-router.base)


;; base

(defmulti html-router
  (fn [core method & args] method))
