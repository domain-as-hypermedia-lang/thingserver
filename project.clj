(defproject thingserver "0.1.0"
  :description "Serve things"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [ring/ring-core "1.6.3"]
                 [ring/ring-jetty-adapter "1.6.3"]
                 [environ "1.1.0"]]
  :aot :all
  :main thingserver.core)
