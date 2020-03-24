(ns app.subscriptions-test
  (:require
   [com.rpl.specter :as sp :refer [select-one!]]
   [cljs.test :refer [deftest is testing]]
   [app.subscriptions :as subscriptions]))

(deftest subscription-version
  (is (= (subscriptions/version {:version "0.0.1"} :na) "0.0.1")))

(deftest subscription-theme
  (is (= (subscriptions/theme {:settings {:theme :dark}} :na) :dark)))
