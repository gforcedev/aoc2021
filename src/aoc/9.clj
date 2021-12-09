(def heightmap (as-> "assets/input9.txt" v
  (slurp v)
  (clojure.string/split v #"\n")
  (mapv (fn [val] (->> val
              (re-seq #"\d")
              (mapv #(Integer/parseInt %)))) v)))

(defn get-score [x y]
  (let [n (get-in heightmap [x y])]
    (if (>
         (min
           (get-in heightmap [(dec x) y] 10)
           (get-in heightmap [(inc x) y] 10)
           (get-in heightmap [x (inc y)] 10)
           (get-in heightmap [x (dec y)] 10))
         n)
      (inc n)
      0)))

(reduce + (for [x (range (count heightmap)) y (range (count (first heightmap)))]
  (get-score x y)))

; Part 2
(defn get-neighbours [[x y]]
  (remove (fn [[a b]] (or
                        (< a 0)
                        (< b 0)
                        (>= a (count heightmap))
                        (>= b (count (first heightmap)))))
          [[(dec x) y]
           [(inc x) y]
           [x (inc y)]
           [x (dec y)]]))

(defn get-basin [starting-point]
  (loop [found-points [] points-to-check [starting-point]]
    (if (= (count points-to-check) 0)
      found-points
      (let [curr-point (first points-to-check)]
        ; If we've already been here, or it's a 9, move on
        (if
          (or
            (some #(= curr-point %) found-points)
            (= (get-in heightmap curr-point) 9))
          (recur found-points (rest points-to-check))
          (recur
            (conj found-points curr-point)
            (apply conj (rest points-to-check) (get-neighbours curr-point))))))))

(def all-points
  (for [x (range (count heightmap)) y (range (count (first heightmap)))]
  [x y]))

(->> (loop [left all-points basins []]
  (println (count left))
  (println (first left))
  (if (= 0 (count left))
    basins
    (let [curr-basin (get-basin (first left))]
      (recur
        (remove
          (fn [point]
            (or
              (some #(= point %) curr-basin)
              (= point (first left))))
          left)
        (conj basins curr-basin)))))
     (mapv #(count %))
     sort
     (take-last 3)
     (reduce *)))

