(def input
  (as-> "assets/input10.txt" v
       (slurp v)
       (clojure.string/split v #"\n")
       (map #(vec %) v)))

(def pairs {
            \( \)
            \[ \]
            \{ \}
            \< \>})

(def scores {
             \) 3
             \] 57
             \} 1197
             \> 25137
             })

(defn parse-chunk [chunk]
  (loop [stack '() c chunk]
    (if (= 0 (count c))
      0
      (let [expected-pair (pairs (first c))]
        (if (= (first stack) (first c))
          (recur (rest stack) (rest c))
          (if (contains? pairs (first c))
            (recur (cons expected-pair stack) (rest c))
            (scores (first c))))))
    ))

(reduce + (map parse-chunk input))

; Part 2

(def incomplete
  (filter #(= (parse-chunk %) 0) input))

(defn parse-incomplete [chunk]
  (loop [stack '() c chunk]
    (if (= 0 (count c))
      stack
      (let [expected-pair (pairs (first c))]
        (if (= (first stack) (first c))
          (recur (rest stack) (rest c))
          (if (contains? pairs (first c))
            (recur (cons expected-pair stack) (rest c))
            (scores (first c))))))
    ))

(def incompletion-scores {
                          \) 1
                          \] 2
                          \} 3
                          \> 4})

(def part-2-scores (->> incomplete
     (map parse-incomplete)
     (map #(map incompletion-scores %))
     (map #(reduce * %)) ; This line needs fixing to score correctly
     sort
     vec))

(identity part-2-scores)

; Too low apparently
(nth part-2-scores (quot (count part-2-scores) 2))
