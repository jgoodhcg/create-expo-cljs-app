(ns create-expo-cljs-app.lib
  (:require [create-cljs-app.lib :as cca-lib]
            [create-cljs-app.utils :refer [has-binary-on-PATH?]]
            ["chalk" :refer [blue green red yellow]]))

(defn get-commands
  [use-yarn]
  (if use-yarn
    {:install "yarn && expo install react-native-gesture-handler react-native-reanimated react-native-screens react-native-safe-area-context @react-native-community/masked-view"
     :start   "yarn start"
     :shadow  "shadow-cljs watch app"}
    {:install "npm install"
     :start   "npm start && expo install react-native-gesture-handler react-native-reanimated react-native-screens react-native-safe-area-context @react-native-community/masked-view"
     :shadow  "shadow-cljs watch app"
     :expo    "expo install react-native-gesture-handler react-native-reanimated react-native-screens react-native-safe-area-context @react-native-community/masked-view
"}))

(defn done-msg
  [name path abs-path commands install-failed?]
  (.log
    js/console
    (str
      "\nSuccess! Created " name " at " abs-path "\n"
      "Inside that directory, you can run several commands:\n"
      "  " (blue (:shadow commands)) "\n"
      "  Starts the shadow compiler.\n"
      "  " (blue (:start commands)) "\n"
      "  Starts the javascript bundler."


      "\n\nGet started by: \n  "

      (blue (str "cd " path)) "\n  "
      (when install-failed? (str (blue (:install commands)) "\n  "))
      (blue (:shadow commands)) "\n  "
      "Then in " (yellow "another") " terminal session run:\n  "
      (blue (str "cd " path)) "\n  "
      (blue (:start commands)) "\n  "
      "Then in the " (yellow "Expo Client") " Open this app.\n  "
      "\n\n")))

(defn expo-cli-warning []
  (.log js/console (str (red "Missing expo-cli tool!") "\n"
                        (red "Installation instructions here: https://docs.expo.io/versions/latest/workflow/expo-cli/#installation") "\n"
                        (yellow "Afterwards please install expo managed dependencies manually.") "\n"
                        (yellow "Installation instructions here: https://reactnavigation.org/docs/getting-started/#installing-dependencies-into-an-expo-managed-project"))))

(defn create [cwd path]
  (cca-lib/create cwd path {:done-msg       done-msg
                            :get-commands   get-commands
                            :has-extras?    (fn [] (has-binary-on-PATH? "expo"))
                            :extras-warning expo-cli-warning}))

(def exports #js {:create create})
