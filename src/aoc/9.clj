(def heightmap (as-> "assets/input9.txt" v
  (slurp v)
  (clojure.string/split v #"\n")
  (mapv (fn [val] (->> val
              (re-seq #"\d")
              (mapv #(Integer/parseInt %)))) v)))

(defn get-score [curr map]
  (if ()
    
    0))
