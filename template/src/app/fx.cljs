(ns app.fx
  (:require
   [re-frame.core :refer [reg-fx dispatch]]
   [applied-science.js-interop :as j]))

(reg-fx :some-fx-example
        (fn [x]
          (println x)))
