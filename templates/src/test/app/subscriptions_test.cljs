(ns new-project-name.subscriptions-test
  (:require
   [com.rpl.specter :as sp :refer [select-one!]]
   [cljs.test :refer [deftest is testing]]
   [new-project-name.subscriptions :as subscriptions]))

(deftest subscription-version
  (is (= (subscriptions/version {:version "0.0.1"} :na) "0.0.1")))

(deftest subscription-theme
  (is (= (subscriptions/theme {:settings {:theme :dark}} :na) :dark)))
