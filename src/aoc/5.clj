(defn parse-line [line]
  (vec (map
    (fn [coord-string] (vec (map #(Integer/parseInt %) (clojure.string/split coord-string #","))))
    (clojure.string/split line #" -> "))))

(def vent-lines ((comp #(map parse-line %) #(clojure.string/split % #"\n") slurp) "assets/input5.txt"))

(def orthogonal-vent-lines
  (filter
    #(or
       (= (get-in % [0 0]) (get-in % [1 0]))
       (= (get-in % [1 0]) (get-in % [1 1])))
    vent-lines))

(defn inc-2nd [arr] (assoc arr 1 (inc (get arr 1))))

(defn repeat-shortest [ranges]
  (let [min-count (apply min (map count ranges))]
    (map
      (fn [r]
        (if (= (count r) min-count)
          (repeat (first r))
          r))
      ranges)))

(defn coord-to-ranges [vent-def]
  (apply (fn [a b] (map #(into [] %&) a b))
    ((comp repeat-shortest #(into [] %))(map
      (fn [coord]
        (apply range ((comp inc-2nd #(into [] %) sort) [(get-in vent-def [0 coord]) (get-in vent-def [1 coord])])))
      [0 1]))))

(defn inc-in [grid coords]
  (assoc-in grid coords (inc (get-in grid coords))))

(def vent-points (apply concat (map coord-to-ranges orthogonal-vent-lines)))

(def smaller-vent-points [[0 0] [0 1] [0 0]])

(reduce
  (fn [[seen total] point]
    (if (= (get seen point) 1)
      [(assoc seen point 2) (inc total)]
      (if (= (get seen point) 2)
        [seen total]
        [(assoc seen point 1) total])))
  [{} 0] (take 1000 vent-points))

