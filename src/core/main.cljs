(ns core.main
  (:require [reagent.core :as reagent]
            [re-frame.core :as reframe]
            [clojure.string :as str]
            [clojure.spec.alpha :as spec]
            [core.schema :as db-schema]
            game.main
            display))

;; A detailed walk-through of this source code is provided in the docs:
;; https://github.com/Day8/re-frame/blob/master/docs/CodeWalkthrough.md

;; -- Domino 1 - Event Dispatch -----------------------------------------------

;; -- Initialization -----------------------------------------------

(defonce default-db-schema
  {:app {}
   :game {:territory 1
          :cadets [(game.main/create-random-cadet)]}})

;; -- Domino 2 - Event Handlers -----------------------------------------------

(reframe/reg-event-db        ;; sets up initial application state
 :initialize                 ;; usage:  (dispatch [:initialize])
 (fn [_ _]                   ;; the two parameters are not important here, so use _
   default-db-schema))

(reframe/reg-event-db
 :update-world
 (fn [db _]
   (update-in db [:game :territory] inc)))

(reframe/reg-event-db
 :new-cadet
 (fn [db _]
   (update-in db [:game :cadets] conj (game.main/create-random-cadet))))

;; -- Domino 4 - Query  -------------------------------------------------------

(reframe/reg-sub
 :territory
 (fn [db _]
   (get-in db [:game :territory])))

(reframe/reg-sub
 :all-cadets
 (fn [db _]
   (get-in db [:game :cadets])))

;; -- Domino 5 - View Functions ----------------------------------------------

(defn display-territory []
   [:h1 "Territory: " @(reframe/subscribe [:territory])])

(defn update-everything []
   [:button {:on-click #(reframe/dispatch [:update-world])} "Update"])

(defn update-autoclick []
  "Defines a button that toggles between automatically updating, and not."
  (let [interval-id (reagent/atom nil),
        updater     #(reframe/dispatch [:update-world]),
        interval-ms 1000]
    (fn []
      [:button {:on-click #(if (nil? @interval-id)
                              (reset! interval-id (js/setInterval updater interval-ms))
                              (do (js/clearInterval @interval-id)
                                  (reset! interval-id nil)))}
       (if @interval-id
         "Autoclicking"
         "Autoclick")])))

(defn display-cadets []
  [:ul (for [x @(reframe/subscribe [:all-cadets])]
         [:li (str x)])])

(defn new-random-cadet []
  [:button {:on-click #(reframe/dispatch [:new-cadet])} "Create New Cadet"])

(defn todo []
  [:ul
   [:li "Figure out why we can't find game.main"]
   [:ul
    [:li "Reboot the daemon when you add a new file"]]
   [:li "Cadet creation function"]])

(defn ui []
  [:div
   [display-territory]
   [todo]
   [update-everything]
   [update-autoclick]
   [new-random-cadet]
   [display-cadets]])


;; -- Entry Point -------------------------------------------------------------

(defn ^:export ^:dev/after-load render []
  (reframe/dispatch-sync [:initialize])     ;; puts a value into application state
  (reagent/render [ui]                 ;; mount the application's ui into '<div id="app" />'
                  (js/document.getElementById "app")))
