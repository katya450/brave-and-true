(ns clojure-noob.dailymizer (:gen-class))

(def team-members ["Joanna Kahila"
                   "Lilli Koskinen"
                   "Katja Lusma"
                   "Lilla Gerstenbrand"
                   "Laura Pimiä"
                   "Lauri Pöri"
                   "Mikael Tetenkin"
                   "Iris Tomaszewski"
                   "Christian Vilen"])


(println (shuffle team-members))

;; lein run -m clojure-noob.dailymizer
