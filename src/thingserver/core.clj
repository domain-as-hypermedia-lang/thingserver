(ns thingserver.core
  (:require [clojure.string :as s])
  (:use [clojure.java.io :only [file]]
        [ring.middleware.content-type :only [wrap-content-type]]
        [ring.middleware.not-modified :only [wrap-not-modified]]
        [ring.adapter.jetty :only [run-jetty]]
        [ring.middleware.file :only [wrap-file]]
        [markdown.core :only [md-to-html-string]]
        [environ.core :only [env]])
  (:gen-class))

(defn handler [{:keys [uri] :as request}]
  (let [f1 (file (str (env :dahl-asset-dir) (s/replace uri #".doc" ".html")))
        f2 (file (str (env :dahl-asset-dir) (s/replace uri #".doc" ".md")))
        body (cond
               (and (s/ends-with? uri ".doc") (.exists f1)) (slurp f1)
               (and (s/ends-with? uri ".doc") (.exists f2)) (md-to-html-string (slurp f2))
               :else                                        "Not Found")]
    {:status 200 :headers {"Content-Type" "text/html"} :body body}))

(def app
  (-> (wrap-file handler (env :dahl-asset-dir))
      (wrap-content-type)
      (wrap-not-modified)))

(defn -main [] (run-jetty app {:port 3000}))
