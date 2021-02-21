(ns app.subscriptions-test
  (:require
   [cljs.test :refer [deftest is testing]]
   [app.subscriptions :as subscriptions]))

(deftest subscription-version
  (testing "version is correct"
    (is (= (subscriptions/version {:version "0.0.1"} :na) "0.0.1"))))

(deftest subscription-theme
  (testing "theme is correct"
    (is (= (subscriptions/theme {:settings {:theme :dark}} :na) :dark))))
