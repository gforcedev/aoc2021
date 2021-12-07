(defn parse-input [input]
  (->> (re-seq #"\d" input)
       (mapv #(Integer/parseInt %))))
