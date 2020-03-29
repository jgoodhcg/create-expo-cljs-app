(ns create-expo-cljs-app.lib
  (:require [create-cljs-app.lib :as cca-lib]
            [create-cljs-app.utils :refer
             [should-use-yarn?]]
            ["chalk" :refer [blue green red yellow]]
            ["path" :refer [basename join]]
            ))

(defn get-commands
  [use-yarn]
  (if use-yarn
    {:install "yarn"
     :start   "yarn start"
     :shadow  "shadow-cljs watch app"}
    {:install "npm install"
     :start   "npm start"
     :shadow  "shadow-cljs watch app"}))

(defn almost-done-msg
  [name path abs-path commands]
  (.log
   js/console
   (str
    "\nCreated " name " at " abs-path "
Inside that directory, you can run several commands:

  " (blue (:shadow commands)) "
    Starts the shadow compiler.

  " (blue (:start commands)) "
    Starts the javascript bundler.

Recommended initial run:

  " (blue (str "cd " path)) "
  " (str (blue (:install commands)) "\n  ")
    (blue (:shadow commands)) "
  " (blue (:start commands)) "

")))

(defn create [cwd path]

  (let [abs-path (join cwd path)
        name     (basename abs-path)
        use-yarn (should-use-yarn?)
        commands (get-commands use-yarn)]

    (cca-lib/create cwd path false)

    ;; TODO figure out how to inject this as a done message to create
    ;; The cca-lib/create installs packages async then spits out a done-msg
    ;; The way this is set up the almost-done-msg prints before the packages are attempted
    (almost-done-msg name path abs-path commands)))

(def exports #js {:create create})
