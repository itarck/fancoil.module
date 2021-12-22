(ns fancoil.module.datascript.base)


(defmulti schema
  (fn [config signal & args] signal))