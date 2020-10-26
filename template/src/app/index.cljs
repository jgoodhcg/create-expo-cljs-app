(ns app.index
  (:require
   ["expo" :as ex]
   ["expo-constants" :as expo-constants]
   ["react-native" :as rn]
   ["react" :as react]
   ["@react-navigation/native" :as nav]
   ["@react-navigation/bottom-tabs" :as bottom-tabs]
   ["react-native-paper" :as paper]
   [applied-science.js-interop :as j]
   [camel-snake-kebab.core :as csk]
   [camel-snake-kebab.extras :as cske]
   [reagent.core :as r]
   [re-frame.core :refer [subscribe dispatch dispatch-sync]]
   [shadow.expo :as expo]
   [app.fx]
   [app.handlers]
   [app.subscriptions]
   [app.helpers :refer [<sub >evt]]))

;; must use defonce and must refresh full app so metro can fill these in
;; at live-reload time `require` does not exist and will cause errors
;; must use path relative to :output-dir
(defonce splash-img (js/require "../assets/shadow-cljs.png"))

(def styles
  ^js (-> {:surface
           {:flex            1
            :justify-content "center"}

           :theme-switch
           {:flex-direction  "row"
            :justify-content "space-between"}}
          (#(cske/transform-keys csk/->camelCase %))
          (clj->js)
          (rn/StyleSheet.create)))

(defn screen2 [props]
  (r/as-element
    [:> paper/Surface {:style (-> styles (j/get :surface))}
     [:> rn/View
      [:> paper/Title "Screen2"]]]))

(defn screen1 [props]
  (r/as-element
    (let [version         (<sub [:version])
          theme-selection (<sub [:theme])
          theme           (-> props (j/get :theme))]
      [:> paper/Surface {:style (-> styles (j/get :surface))}
       [:> rn/View
        [:> paper/Card
         [:> paper/Card.Title {:title    "A nice template"
                               :subtitle (str "Version: " version)}]
         [:> paper/Card.Content
          [:> paper/Paragraph "For quick project startup"]
          [:> rn/View {:style (-> styles (j/get :themeSwitch))}
           [:> paper/Text
            {:style {:color (-> theme
                                (j/get :colors)
                                (j/get :accent))}}
            "Dark mode"]
           [:> paper/Switch {:value           (= theme-selection :dark)
                             :on-value-change #(>evt [:set-theme (if (= theme-selection :dark)
                                                                   :light
                                                                   :dark)])}]]]]]])))

(def tab (bottom-tabs/createBottomTabNavigator))

(defn navigator [] (-> tab (j/get :Navigator)))

(defn screen [props] [:> (-> tab (j/get :Screen)) props])

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
                            (if (not= prev-route-name current-route-name)
                              ;; This is where you can do side effecty things like analytics
                              (>evt [:some-fx-example (str "New screen encountered " current-route-name)]))
                            (swap! !route-name-ref merge {:current current-route-name})))}

      [:> (navigator)
       (screen {:name      "Screen1"
                :component (paper/withTheme screen1)})
       (screen {:name      "Screen2"
                :component (paper/withTheme screen2)})]]]))

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
