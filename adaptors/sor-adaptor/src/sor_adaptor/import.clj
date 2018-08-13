(ns sor-adaptor.import
  (:require [clojure-csv.core :refer [parse-csv]]
            [clojure.data :refer [diff]]
            [environ.core :refer [env]]
            [clojure.java.io :as io]
            [taoensso.nippy :as nippy]))

(defonce sor-1 (nippy/thaw-from-file (io/resource "1.sor")))

(def sor-csv-file (if-let [e (env :sor-csv)]
                    e
                    "http://filer.nsi.dk/sor/data/sor/sorcsv/v_1_2_1_19/sor.csv"))

(defn csv-map
  "ZipMaps header as keys and values from lines."
  [head, lines]
  (map #(zipmap (map keyword head) %1) lines))

(defn group-by-sor [sor-seq]
  (reduce (fn [a r]
            (assoc a (:SOR-kode r) r))
          {}
          sor-seq))

(defn- fetch-sor-csv-file
  []
  (slurp sor-csv-file :encoding "ISO-8859-1"))

(defn reload-sor-csv-file [base csv]
  (println "Reload sor csv file ...")
  (let [_ (println "  Parse CSV file ...")
        parsed (parse-csv csv :delimiter \;)
        _ (println "  Group CSV file ...")
        sor-map (group-by-sor (csv-map (first parsed) (rest parsed)))]
    (println "  Diff reload sor csv file...")
    (diff sor-map base)))

(comment
  (count sor-1)
  (first sor-1)

  (time (def raw-file (fetch-sor-csv-file)))
  (time (def parsed (parse-csv raw-file :delimiter \;)))
  (time (def zm (csv-map (first parsed) (rest parsed))))
  (keys (first zm))
  (time (def gb (group-by-sor zm)))
  (time (nippy/freeze-to-file "data/1.sor" gb))
  (first gb)

  (time (def reload-csv (reload-sor-csv-file sor-1 (fetch-sor-csv-file))))
  (let [d reload-csv]
    [(count (first d)) (count (second d)) (count (nth d 2))]))





