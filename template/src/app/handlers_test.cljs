(ns app.handlers-test
  (:require
   [com.rpl.specter :as sp :refer [select-one!]]
   [cljs.test :refer [deftest is testing]]
   [app.handlers :as handlers]
   [app.db :refer [default-app-db]]))

(defn setup-mocked-context [db]
  {:effects   {:db db}
   :coeffects {:db default-app-db}})

(defn validate [db]
  (handlers/validate-spec (setup-mocked-context db)))

(deftest handler-initialize-db
  (let [val (-> (handlers/initialize-db :na :na)
                (validate)
                (as-> context
                    (select-one! [:effects :db] context)))]
    (is (= val default-app-db))))

(deftest handler-set-theme-light
  (let [val (-> (handlers/set-theme default-app-db [:na :light])
                (validate)
                (as-> context
                    (select-one! [:effects :db :settings :theme] context)))]
    (is (= val :light))))

(deftest handler-set-version
  (let [val (-> (handlers/set-version default-app-db [:na "0.0.1"])
                (validate)
                (as-> context
                    (select-one! [:effects :db :version] context)))]
    (is (= val "0.0.1"))))
