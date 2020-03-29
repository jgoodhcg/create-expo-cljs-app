(ns create-expo-cljs-app.lib
  (:require [create-cljs-app.lib :as cca-lib]
            [create-cljs-app.utils :refer
             [should-use-yarn? has-binary-on-PATH?]]
            ["chalk" :refer [blue green red yellow]]
            ["path" :refer [basename join]]
            ["shelljs" :refer [exec]]))

(defn get-commands
  [use-yarn]
  (if use-yarn
    {:install "yarn"
     :start   "yarn start"
     :shadow  "shadow-cljs watch app"}
    {:install "npm install"
     :start   "npm start"
     :shadow  "shadow-cljs watch app"}))

(defn done-msg
  [commands name path abs-path install-failed?]
  (.log
   js/console
   (str
    "\nSuccess! Created " name " at " abs-path "
Inside that directory, you can run several commands:

  " (blue (:shadow commands)) "
    Starts the shadow compilter.

  " (blue (:start commands)) "
    Starts the javascript bundler.

We suggest that you begin by: \n  "

    (blue (str "cd " path)) "\n  "
    (when install-failed? (str (blue (:install commands)) "\n  "))
    (blue (:shadow commands)) "\n  "
    "Then in " (yellow "another") " terminal session run:\n  "
    (blue (:start commands)) "\n\n"
    "Happy hacking! \n")))

(defn install-expo-managed-deps []
  (if (has-binary-on-PATH? "expo")
    (exec "expo install react-native-gesture-handler react-native-reanimated react-native-screens react-native-safe-area-context @react-native-community/masked-view
")
    (.log js/console (str (red "Missing expo-cli tool!") "\n"
                          (red "Installation instructions here: https://docs.expo.io/versions/latest/workflow/expo-cli/#installation") "\n"
                          (yellow "Afterwards please install expo managed dependencies manually.") "\n"
                          (yellow "Installation instructions here: https://reactnavigation.org/docs/getting-started/#installing-dependencies-into-an-expo-managed-project")))))

(defn create [cwd path]

  (let [abs-path (join cwd path)
        name     (basename abs-path)
        use-yarn (should-use-yarn?)
        commands (get-commands use-yarn)]

    (cca-lib/create cwd path {:done-msg       (partial done-msg commands)
                              :install-extras install-expo-managed-deps})))

(def exports #js {:create create})
