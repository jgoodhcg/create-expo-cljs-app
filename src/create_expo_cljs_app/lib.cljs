(ns create-expo-cljs-app.lib
  (:require [create-cljs-app.utils :as utils]))

(def exports #js {:testing (utils/exit-with-reason "It's working!")})
