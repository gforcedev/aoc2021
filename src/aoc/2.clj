(require '[clojure.string :as str])

; Turn integers split by newlines into a vector of numbers
(def puzzleInput (str/split (slurp "assets/input2.txt") #"\n"))

; Part 1 - product of length and depth after instruction list
(def commands {
                 "Forward" (fn [[length depth]] [(inc length) depth])
                 "Up"      (fn [[length depth]] [length (dec depth)])
                 "Down"    (fn [[length depth]] [length (inc depth)])})

(reduce  [0 0] puzzleInput)

