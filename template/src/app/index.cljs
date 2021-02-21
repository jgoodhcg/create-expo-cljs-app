(ns app.index
  (:require
   ["@react-navigation/native" :as nav]
   ["@react-navigation/stack" :as rn-stack]
   ["expo" :as ex]
   ["expo-constants" :as expo-constants]
   ["react" :as react]
   ["react-native" :as rn]
   ["react-native-paper" :as paper]
   ["tailwind-rn" :default tailwind-rn]

   [applied-science.js-interop :as j]
   [reagent.core :as r]
   [re-frame.core :refer [dispatch-sync]]
   [shadow.expo :as expo]

   [app.fx]
   [app.handlers]
   [app.subscriptions]
   [app.helpers :refer [<sub >evt]]))

(defn tw [style-str]
  ;; https://github.com/vadimdemedes/tailwind-rn#supported-utilities
  (-> style-str
      tailwind-rn
      (js->clj :keywordize-keys true)))

;; must use defonce and must refresh full app so metro can fill these in
;; at live-reload time `require` does not exist and will cause errors
;; must use path relative to :output-dir
(defonce splash-img (js/require "../assets/shadow-cljs.png"))

(defn screen-main [props]
  (r/as-element
    (let [version         (<sub [:version])
          theme-selection (<sub [:theme])
          theme           (-> props (j/get :theme))
          expo-version    (-> expo-constants
                              (j/get :default)
                              (j/get :manifest)
                              (j/get :sdkVersion))]

      [:> rn/SafeAreaView {:style (tw "flex flex-1")}
       [:> rn/StatusBar {:visibility "hidden"}]
       [:> paper/Surface {:style (tw "flex flex-1 justify-center")}
        [:> rn/View
         [:> paper/Card
          [:> paper/Card.Cover {:source splash-img}]
          [:> paper/Card.Title {:title    "My new expo cljs app!"
                                :subtitle (str "Version: " version)}]
          [:> paper/Card.Content
           [:> paper/Paragraph (str "Using Expo SDK: " expo-version)]
           [:> rn/View {:style (tw "flex flex-row justify-between")}
            [:> paper/Text
             {:style {:color (-> theme
                                 (j/get :colors)
                                 (j/get :accent))}}
             "Dark mode"]
            [:> paper/Switch {:value           (= theme-selection :dark)
                              :on-value-change #(>evt [:set-theme (if (= theme-selection :dark)
                                                                    :light
                                                                    :dark)])}]]]]]]])))

(def stack (rn-stack/createStackNavigator))

(defn navigator [] (-> stack (j/get :Navigator)))

(defn screen [props] [:> (-> stack (j/get :Screen)) props])

(defn root []
  (let [theme           (<sub [:theme])
        !route-name-ref (clojure.core/atom {})
        !navigation-ref (clojure.core/atom {})]

    [:> paper/Provider
     {:theme (case theme
               :light paper/DefaultTheme
               :dark  paper/DarkTheme
               paper/DarkTheme)}

     [:> nav/NavigationContainer
      {:ref             (fn [el] (reset! !navigation-ref el))
       :on-ready        (fn []
                          (swap! !route-name-ref merge {:current (-> @!navigation-ref
                                                                     (j/call :getCurrentRoute)
                                                                     (j/get :name))}))
       :on-state-change (fn []
                          (let [prev-route-name    (-> @!route-name-ref :current)
                                current-route-name (-> @!navigation-ref
                                                       (j/call :getCurrentRoute)
                                                       (j/get :name))]
                            (when (not= prev-route-name current-route-name)
                              ;; This is where you can do side effecty things like analytics
                              (>evt [:some-fx-example (str "New screen encountered " current-route-name)]))
                            (swap! !route-name-ref merge {:current current-route-name})))}

      [:> (navigator) {:header-mode "none"}
       (screen {:name      "Screen1"
                :component (paper/withTheme screen-main)})]]]))

(defn start
  {:dev/after-load true}
  []
  (expo/render-root (r/as-element [root])))

(def version (-> expo-constants
                 (j/get :default)
                 (j/get :manifest)
                 (j/get :version)))

(defn init []
  (dispatch-sync [:initialize-db])
  (dispatch-sync [:set-version version])
  (start))
