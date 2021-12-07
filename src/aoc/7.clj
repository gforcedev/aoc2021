(defn parse-input [input]
  (->> (re-seq #"\d+" input)
       (mapv #(Integer/parseInt %))))

(parse-input "assets/7.txt")

(defn solve [crab-positions]
  (reduce
    (fn [curr-min n]
      (let [this-min (->> crab-positions
                          (mapv #(Math/abs (- n %)))
                          (reduce +))]
        (if (< curr-min this-min) curr-min this-min))) 99999999 (range (apply min crab-positions) (apply max crab-positions))))

(->> "assets/input7.txt"
     slurp
     parse-input
     solve)

; Part 2 - triangles
(defn triangle [n]
  (/ (+ (* n n) n) 2))

(defn solve-2 [crab-positions]
  (reduce
    (fn [curr-min n]
      (let [this-min (->> crab-positions
                          (mapv #(Math/abs (- n %)))
                          (mapv triangle)
                          (reduce +))]
        (if (< curr-min this-min) curr-min this-min))) 99999999 (range (apply min crab-positions) (apply max crab-positions))))

(->> "assets/input7.txt"
     slurp
     parse-input
     solve-2)

