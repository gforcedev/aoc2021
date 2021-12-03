; Turn integers split by newlines into a vector of numbers
(def puzzleInput  (clojure.string/split (slurp "assets/input3.txt") #"\n"))

; Part 1 - yep, I lasted a whole 2 days of summarising the problem in a comment
((fn [counts input] (assoc counts input (inc (counts input)))) [0 1] 1)

(defn getBitCount [col]
  (reduce
    (fn [counts input] (assoc counts input (inc (counts input))))
    [0 0] (map (fn [inputStr] (Character/digit (nth inputStr col) 10)) puzzleInput)))

(def bitCounts (map getBitCount (range (count (puzzleInput 0)))))

(*
 (Integer/parseInt (apply str (map (fn [thisCount] (max-key thisCount 0 1)) bitCounts)) 2)
 (Integer/parseInt (apply str (map (fn [thisCount] (min-key thisCount 0 1)) bitCounts)) 2))

