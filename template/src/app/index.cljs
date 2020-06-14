(ns app.index
  (:require
   ["expo" :as ex]
   ["expo-constants" :as expo-constants]
   ["react-native" :as rn]
   ["react" :as react]
   ["react-native-router-flux" :as nav]
   ["react-native-paper" :as paper]
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

(defn stub-screen [props]
  (r/as-element
    [:> paper/Surface {:style (.-surface styles)}
     [:> rn/View
      [:> paper/Title (:title (js->clj props :keywordize-keys true))]]]))

(defn home-scene [props]
  (r/as-element
    (let [version         (<sub [:version])
          theme-selection (<sub [:theme])
          theme           (.-theme props)]
      [:> paper/Surface {:style (.-surface styles)}
       [:> rn/View
        [:> paper/Card
         [:> paper/Card.Title {:title    "A nice Expo/Shadow-cljs template"
                               :subtitle "For quick project startup"}]
         [:> paper/Card.Content
          [:> rn/View {:style (.-themeSwitch styles)}
           [:> paper/Text {:style {:color (->> theme .-colors .-accent)}}
            "Dark mode"]
           [:> paper/Switch {:value           (= theme-selection :dark)
                             :on-value-change #(>evt [:set-theme (if (= theme-selection :dark)
                                                                   :light
                                                                   :dark)])}]]
          [:> paper/Paragraph (str "Version: " version)]]]]])))

(defn root []
  (let [theme (<sub [:theme])]
    [:> paper/Provider {:theme (case theme
                                 :light paper/DefaultTheme
                                 :dark  paper/DarkTheme
                                 paper/DarkTheme)}
     [:> nav/Router
      [:> nav/Stack {:key "root"}
       [:> nav/Tabs {:key              "tabbar"
                     :tab-bar-on-press #(>evt [:navigate (-> %
                                                             (js->clj :keywordize-keys true)
                                                             (:navigation)
                                                             (:state)
                                                             (:key)
                                                             (keyword))])
                     :hide-nav-bar     true}
        [:> nav/Scene {:key          "home"
                       :title        "Home"
                       :hide-nav-bar true
                       :component    (paper/withTheme home-scene)}]
        [:> nav/Scene {:key          "screen2"
                       :hide-nav-bar true
                       :component    (paper/withTheme stub-screen)
                       :title        "Screen 2"}]
        [:> nav/Scene {:key          "screen3"
                       :hide-nav-bar true
                       :component    (paper/withTheme stub-screen)
                       :title        "Screen 3"}]]]]]))

(defn start
  {:dev/after-load true}
  []
  (expo/render-root (r/as-element [root])))

(def version (-> expo-constants
                 (.-default)
                 (.-manifest)
                 (.-version)))

(defn init []
  (dispatch-sync [:initialize-db])
  (dispatch-sync [:set-version version])
  (start))
