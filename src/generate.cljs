(ns generate
  (:require [clojure.spec.alpha :as spec]))

(def vowel-phonemes
  "English vowel-phonemes.
  Taken from:
  http://www.lancsngfl.ac.uk/curriculum/literacy/lit_site/lit_sites/phonemes_001/"
  ["a" "e" "i" "o" "u" "ae" "ee" "ie" "oe" "ue" "oo" "ar" "ur" "or"
   "au" "er" "ow" "oi" "air" "ear"])

(def consonant-phonemes
  "English consonant-phonemes.
  Taken from:
  http://www.lancsngfl.ac.uk/curriculum/literacy/lit_site/lit_sites/phonemes_001/"
  ["b" "d" "f" "g" "h" "j" "k" "l" "m" "n" "p" "r" "s" "t" "v" "w"
   "wh" "y" "z" "th" "ch" "sh" "zh" "ng"])

(defn random-name
  ([]
   (random-name 5))
  ([length]
   "TODO: Determine what we have to do, and then implement it."
   ;; This rand-nth repeatedly call might not work
   [(take length (repeatedly #(rand-nth (interleave consonant-phonemes
                                                    vowel-phonemes))))]))
