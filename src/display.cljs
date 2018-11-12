(ns display
  "This module's job is to manufacture display components that does
  good enough."
  (:require [reagent.core :as reagent]))

;; The clojurian slack tells me it's time to move to reframe
;; We should look at reframe on how to update this event really
(defn progress [init max]
  "This function is a combined progress bar and a click function that
  increments it"
  (let [current (reagent/atom init)]
    (fn []
      [:div
       [:progress {:value @current :max max}]
       [:button {:on-click #(swap! current (fn [val]
                                             (if (< val max)
                                               (inc val)
                                               val)))}
        "Click me"]
       [:button {:on-click #(reset! current 0)}
        "Reset"]])))
