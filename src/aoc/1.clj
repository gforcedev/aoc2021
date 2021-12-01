(require '[clojure.string :as str])

; Turn integers split by newlines into a vector of numbers
(def puzzleInput (map read-string (str/split (slurp "assets/input1.txt") #"\n")))

; Part 1 - amount of steps which increase
(defn getIncreases [data]
  ((reduce
    (fn [[count last] curr]
      (if (> curr last)
        [(inc count) curr]
        [count curr]))
    [-1 0] data) 0))

(getIncreases puzzleInput)

; Part 2 - amount of steps which the sum of three increases
(getIncreases
 (map
  (fn [triple] (reduce + triple))
  (partition 3 1 puzzleInput)))

