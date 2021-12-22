(ns fancoil.module.datascript.unit
  (:require
   [datascript.core :as d]
   [integrant.core :as ig]
   [fancoil.module.datascript.base :as base]
   [fancoil.module.datascript.plugin]))


(defmethod ig/init-key :fancoil.module.datascript/schema
  [_ _]
  (let [schema-ref (atom {})]
    (doseq [[k f] (methods base/schema)]
      (let [schm (f)]
        (swap! schema-ref merge schm)))
    @schema-ref))


(defmethod ig/init-key :fancoil.module.datascript/conn [_k config]
  (let [{:keys [schema initial-tx initial-db] :or {schema {}}} config
        conn (d/create-conn schema)]
    (when initial-db
      (d/reset-conn! conn initial-db))
    (when initial-tx
      (d/transact! conn initial-tx))
    conn))