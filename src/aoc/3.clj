; Turn integers split by newlines into a vector of strings
(def puzzleInput (clojure.string/split (slurp "assets/input3.txt") #"\n"))

; Part 1 - yep, I lasted a whole 2 days of summarising the problem in a comment
(defn getBitCount [numList, index]
  (reduce
    (fn [counts input] (assoc counts input (inc (counts input))))
    [0 0] (map (fn [inputStr] (Character/digit (nth inputStr index) 10)) numList)))

(def bitCounts (map #(getBitCount puzzleInput %) (range (count (puzzleInput 0)))))

(reduce
  *
  (map
    #(Integer/parseInt (apply str (map (fn [thisCount] (% thisCount 0 1)) bitCounts)) 2)
    [min-key max-key]))

; Part 2 - an attempt to do it nicer than part 1
(defn get-life-support-rating [[min-or-max-key args]]
  (loop [remaining puzzleInput
       index 0]
  (if (= (count remaining) 1)
    (first remaining)
    (recur
      (let [important-bit
            ; Everything except from this line is actually nice
            (apply min-or-max-key (concat [(getBitCount remaining index)] args))]
        (filter
          ; Okay maybe this line too because of the conversions
          #(= (Integer/parseInt (str (nth % index))) important-bit)
          remaining))
      (inc index)))))

(reduce * (map #(Integer/parseInt (get-life-support-rating %) 2) [[min-key [1 0]] [max-key [0 1]]]))

