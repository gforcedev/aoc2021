(defn parse-input [input]
  (as-> input v
       (clojure.string/split v #",")
       (mapv #(Integer/parseInt %) v)))

(parse-input "1,2,3,4,5")

