(ns core.schema
  (:require [clojure.spec.alpha :as s]))

(s/def ::name string?)

(s/def ::vocation #{:infantry :armor :flight})

(s/def ::service-branch #{:army :navy})

(s/def ::cadet (s/keys :req-un [::name ::vocation]))
