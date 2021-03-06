; First off load the numbers and bingo board
(def input-strings (clojure.string/split (slurp "assets/input4.txt") #"\n"))
(def nums (map read-string (clojure.string/split (first input-strings) #",")))

(defn split-row [row] (filter #(not= "" %) (clojure.string/split row #" ")))
(def board-string-lists (partition 5 6 (nthrest input-strings 2)))

(defn make-board [board-row-strings]
  (reduce
    (fn [curr-board row-string]
      (conj curr-board (map #(identity {:called false :num (read-string %)}) (split-row row-string))))
    [] board-row-strings))

; Boards is a seq of 2d vectors, each element of the vector
; being a map of the form {:called <bool> :num <num>}
(def boards (map make-board board-string-lists))

; Part 1 - winning score of the board which will win first

; Set :called to true for each element of the board where :num is just-called
(defn update-board [board just-called]
  (clojure.walk/prewalk
    #(if (map? %)
       (if (= (:num %) just-called) (assoc % :called true) %)
       %) board))

(defn check-win [board]
  (some identity
        (map
          (fn [b]
            (= (apply max (map #(count (filter :called %)) b))
               (count b)))
          [board (apply mapv vector board)])))

(defn score-board [board just-called]
  (*
   just-called
   (reduce + (map #(:num %) (filter #(= (:called %) false) (flatten board))))))

(defn check-score [board just-called]
  (if (check-win board)
    (score-board board just-called)
    0))

(loop [remaining nums curr-boards boards]
  (let [
        new-boards (map #(update-board % (first remaining)) curr-boards)
        winning-score (apply max (map #(check-score % (first remaining)) new-boards))]
    (if (> winning-score 0)
      winning-score
      (recur (rest remaining) new-boards))))

; Part 2 - winning score of the board which will win last
(loop [remaining nums curr-boards boards]
  (let [new-boards (map #(update-board % (first remaining)) curr-boards)]
    (if (= (count new-boards) 1)
      (check-score (first new-boards) (first remaining))
      (recur (rest remaining) (filter #(= 0 (check-score % 1)) new-boards)))))

