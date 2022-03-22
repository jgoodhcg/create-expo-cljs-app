(ns app.fx
  (:require
   ["@react-navigation/native" :as nav]
   [applied-science.js-interop :as j]
   [re-frame.core :refer [reg-fx]]
   ))

(def !navigation-ref (clojure.core/atom nil))

(defonce !last-nav-state (clojure.core/atom nil))

(reg-fx :some-fx-example
        (fn [x]
          (tap> x)
          (println x)))

(defn navigate [name] ;; no params yet
  ;; TODO implement a check that the navigation component has initialized
  ;; https://reactnavigation.org/docs/navigating-without-navigation-prop#handling-initialization
  ;; The race condition is in my favor if the user has to press a component within the navigation container
  (-> @!navigation-ref
      ;; no params yet for second arg
      (j/call :navigate name (j/lit {}))))

(reg-fx :navigate navigate)

(defn save-nav-state [state]
  (reset! !last-nav-state state))
(reg-fx :save-nav-state save-nav-state)

(defn reset-nav-state []
  (let [state @!last-nav-state]
    (when (some? state)
      (-> @!navigation-ref
          (j/call :dispatch
                  (-> nav
                      (j/get :CommonActions)
                      (j/call :reset state)))))))
(reg-fx :reset-nav-state reset-nav-state)
