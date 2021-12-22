(ns fancoil.module.posh.base)


(defmulti schema
  (fn [config signal & args] signal))