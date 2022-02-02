(defproject clojure-noob "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.0"]]
  :main ^:skip-aot clojure-noob.core
  :target-path "target/%s"
  :profiles {:dev {:plugins [[lein-midje "3.2.1"]]}})
