 (defproject sor-adaptor "0.1.0-SNAPSHOT"
   :description "FIXME: write description"
   :dependencies [[org.clojure/clojure "1.8.0"]
                  [clojure-csv/clojure-csv "2.0.1"]
                  [com.taoensso/nippy "2.14.0"]
                  [metosin/compojure-api "1.1.11"]]
   :ring {:handler sor-adaptor.handler/app}
   :uberjar-name "server.jar"
   :profiles {:dev {:dependencies [[javax.servlet/javax.servlet-api "3.1.0"]]
                    :plugins [[lein-ring "0.12.0"]]}})
