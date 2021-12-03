; Turn integers split by newlines into a vector of numbers
(def puzzleInput (clojure.string/split (slurp "assets/input3.txt") #"\n"))

; Part 1 - yep, I lasted a whole 2 days of summarising the problem in a comment
(defn getBitCount [numList, index]
  (reduce
    (fn [counts input] (assoc counts input (inc (counts input))))
    [0 0] (map (fn [inputStr] (Character/digit (nth inputStr index) 10)) numList)))

(def bitCounts (map #(getBitCount puzzleInput %) (range (count (puzzleInput 0)))))

(*
 (Integer/parseInt (apply str (map (fn [thisCount] (max-key thisCount 0 1)) bitCounts)) 2)
 (Integer/parseInt (apply str (map (fn [thisCount] (min-key thisCount 0 1)) bitCounts)) 2))

; Part 2 - an attempt to do it nicer than part 1
; Fully working apart from when using min-key we need to use (0 1) and when using max-key we need to use (1 0)
; Currently both use 0 1
(def testData ["00100" "11110" "10110" "10111" "10101" "01111" "00111" "11100" "10000" "11001" "00010" "01010"])

(defn get-life-support-rating [min-or-max-key]
  (loop [remaining puzzleInput
       index 0]
  (if (= (count remaining) 1)
    (first remaining)
    (recur
      (let [important-bit (min-or-max-key (getBitCount remaining index) 0 1)]
        (filter #(= (Integer/parseInt (str (nth % index))) important-bit) remaining))
      (inc index)))))

(reduce * (map #(Integer/parseInt (get-life-support-rating %) 2) [min-key max-key]))

