(ns create-expo-cljs-app.lib
  (:require [create-cljs-app.lib :as cca-lib]))

(def exports #js {:create cca-lib/create})
