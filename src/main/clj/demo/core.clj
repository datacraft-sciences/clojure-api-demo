(ns demo.core
  (:use [org.httpkit.server :only [run-server]]
        [mikera.cljutils error])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [compojure.core :as cc :refer [GET POST DELETE PUT ANY defroutes]]
            [ring.util.response :as resp]
            [ring.middleware.params]
            [clojure.java.jdbc :as j]
            [cheshire.core :as json]
            [clojure.string]
            [liberator.core :as lib]
            [liberator.representation]
            [clojure.java.io :as io])
  (:require [org.httpkit.client :as http])
  (:gen-class))

;; ==================================================================
;; Utility functions

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string data)})

(defn string-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "text/plain"}
   :body data})

(def test-api
  (lib/resource :available-media-types ["text/html"]
                :handle-ok "<html>Hello, Internet.</html>"))

;; ==================================================================
;; API Specification

(defroutes app-routes
  (GET "/" [] (resp/redirect "/index.html"))

  (ANY "/test-api" [] test-api)
  
  (cc/context "/echo-params" []
    (GET "/" {params :params} (do
                                ;; (prn params)
                                (json-response 
                                 params))))

  (route/resources "/") ;; defaults to reading from /public path on classpath
  (route/not-found "Page not found"))

;; ==================================================================
;; Server configuration and launch


;; use compojure.handler to wrap routes in appropriate middleware for an API interface
(def app
  (-> #'app-routes 
      (handler/api)))

(defn -main [& args] ;; entry point, lein run will pick up and start from here
  (let [port 8080]
    (println (str "Starting Clojure API Server on port: " port))
    (run-server app {:port port})))

;; ==================================================================
;; Testing code (for execution at REPL)

(defn test-code []
;; load testing with 1000 requests
  (time (doseq [pr (doall (pmap (fn [n] (http/get (str "http://localhost:8080/test-api" n))) (range 1000)))] @pr))
  
  )
