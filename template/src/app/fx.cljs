(ns app.fx
  (:require
   ["react-native-router-flux" :as nav]
   [re-frame.core :refer [reg-fx dispatch]]
   [applied-science.js-interop :as j]))

(reg-fx :navigate
        (fn [screen]
          (j/call nav/Actions screen)))
