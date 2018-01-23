(ns thingserver.core
  (:use [ring.adapter.jetty]
        [ring.middleware.resource]
        [ring.middleware.content-type]
        [ring.middleware.not-modified]
        [ring.middleware.file])
  (:require [environ.core :refer [env]])
 (:gen-class))

(defn handler [request]
  {:status 404 :headers {"Content-Type" "text/html"} :body "Not Found"})

(def app
  (-> (wrap-file handler (env :dahl-asset-dir))
      (wrap-content-type)
      (wrap-not-modified)))

(defn -main [] (run-jetty app {:port 3000}))
