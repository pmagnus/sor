(ns sor-adaptor.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [sor-adaptor.import :refer [get-sor get-random-sor]]
            [schema.core :as s]))

(def app
  (api
    {:swagger
     {:ui "/"
      :spec "/swagger.json"
      :data {:info {:title "Sor-adaptor"
                    :description "Sor adaptor Api"}
             :tags [{:name "api", :description "sor apis"}]}}}

    (context "/api" []
      :tags ["api"]

      (GET "/random-sor" []
        :summary "Return random sor info"
        (ok (get-random-sor)))

      (GET "/sor" []
        :query-params [sor :- String]
        :summary "Return sor info"
        (ok (get-sor sor))))))
