(ns game.main
  (:require [core.schema :as schema]
            [clojure.spec.alpha :as spec]
            [game.generate :as generate]))

(defn create-random-cadet
  "Given some parameters, return a ap of cadet parameters"
  ([] (create-random-cadet {}))
  ([params]
   (spec/conform ::schema/cadet {:name (generate/random-name)
                                 :vocation :infantry})))
