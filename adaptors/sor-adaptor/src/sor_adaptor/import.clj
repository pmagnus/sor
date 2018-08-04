(ns sor-adaptor.import
  (:require [clojure-csv.core :refer [parse-csv]]
            [clojure.data :refer [diff]]
            [taoensso.nippy :as nippy]))

#_(defonce csv (slurp "http://filer.nsi.dk/sor/data/sor/sorcsv/v_1_2_1_19/sor.csv" :encoding "ISO-8859-1"))
#_(spit "data/cor.csv" csv)

#_(defonce sor-csv (slurp "data/sor.csv"))
#_(def parsed (parse-csv sor-csv :delimiter \;))

(defn csv-map
  "ZipMaps header as keys and values from lines."
  [head, lines]
  (map #(zipmap (map keyword head) %1) lines))

#_(time (def sor-map (group-by :SOR-kode (csv-map (first parsed) (rest parsed)))))

(defn reload-sor-csv-file [base]
  (println "Reload sor csv file ...")
  (let [csv (slurp "http://filer.nsi.dk/sor/data/sor/sorcsv/v_1_2_1_19/sor.csv"
                   :encoding "ISO-8859-1")
        _ (println "  Parse CSV file ...")
        parsed (parse-csv csv :delimiter \;)
        _ (println "  Group CSV file ...")
        sor-map (group-by :SOR-kode
                          (csv-map (first parsed) (rest parsed)))]
    (println "  Diff reload sor csv file...")
    (diff sor-map base)))


#_(nippy/freeze-to-file "data/0.sor" sor-map)

(defonce sor-0 (nippy/thaw-from-file "data/0.sor"))

(comment
  (count sor-0)
  (time (def reload-csv (reload-sor-csv-file sor-0)))
  (let [d reload-csv]
    [ (count (first d)) (count (second d)) (count (nth d 2))]))
