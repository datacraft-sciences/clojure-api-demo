(defproject clojure-api-demo "0.0.1-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/tools.reader "0.8.3"]
                 ;; CLJ
                 [ring/ring-core "1.2.1"]
                 [org.clojure/data.xml "0.0.7"]
                 [org.clojure/java.jdbc "0.3.3"]
                 [clj-time "0.6.0"]
                 [compojure "1.1.6"]
                 [liberator "0.10.0"]
                 [cheshire "5.3.1"]
                 [net.mikera/clojure-utils "0.6.0"]
                 [javax.servlet/servlet-api "2.5"]
                 [com.h2database/h2 "1.3.175"]
                 [http-kit "2.1.17"]]

  :plugins [[lein-cljsbuild "1.0.0"]
            [lein-ring "0.8.7"]]

  :ring {:handler demo.core/app
         :init    demo.core/init}

  :source-paths ["src/main/clj"]
  :test-paths ["src/test/clj"]
  
  :resource-paths ["resources"]
  
  :main demo.core)