(defn parse-input [input]
  (as-> input v
    (slurp v)
    (clojure.string/replace v #"\n" "")
    (clojure.string/split v #",")
    (mapv #(Integer/parseInt %) v)))

(defn do-day [fish-list]
  (->> fish-list
       (map dec)
       (replace {-1 [6 8]})
       flatten
       vec))

; Part 1
(->> "assets/input6.txt"
     parse-input
     ((apply comp (repeat 80 do-day)))
     count)

; Part 2 experimentation
(defn parse-input-2 [input]
  (as-> input v
    (slurp v)
    (clojure.string/replace v #"\n" "")
    (clojure.string/split v #",")
    (mapv #(Integer/parseInt %) v)
    (mapv vector v)))

(defn do-day-2 [fish-list-list]
  (mapv #(do-day %) fish-list-list))

(->> "assets/input6.txt"
     parse-input-2
     ((apply comp (repeat 64 do-day-2))))

(sort-by key (frequencies (map #(->> "1"
     parse-input
     ((apply comp (repeat % do-day)))
     count) (vec (range 64)))))

; Yeah I don't think I'm managing part 2

; A different solution found from https://github.com/zelark/AoC-2021/blob/main/src/zelark/aoc_2021/day_06.clj
; Which I saw one tiny bit of and decided I wanted to implement
(def better-input (->> "assets/input6.txt"
     parse-input
     (frequencies)
     (sort-by key)))

(def starting-point
  (reduce (fn [curr [amount timer]]
            (assoc curr amount timer)) (vec (range 9)) better-input))

(defn do-day-better [vals]
    (assoc (apply vector (flatten [(rest vals) (first vals)])) 6 (first vals)))

(do-day-better starting-point)
