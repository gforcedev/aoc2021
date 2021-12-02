; Turn integers split by newlines into a vector of numbers
(def puzzleInput (clojure.string/split (slurp "assets/input2.txt") #"\n"))

; Part 1 - product of length and depth after instruction list
(def commands {
                 "forward" (fn [[length depth]] [(inc length) depth])
                 "up"      (fn [[length depth]] [length (dec depth)])
                 "down"    (fn [[length depth]] [length (inc depth)])})

(defn doCommand [[length depth] cmdString]
  (let [
        command (re-find #"\S+" cmdString )
        distance (read-string (re-find #"\d+" cmdString ))]
    ; This is stupid - just (+ <relevant coord> arg) for defining the commands lol
    (loop [count 0 coords [length depth]]
      (if (>= count distance)
        coords
        (recur (inc count) ((commands command) coords))))))

(puzzleInput 0)

(reduce * (reduce doCommand [0 0] puzzleInput))

; Part 2 - same problem only now aim is involved
(def commands2 {
                 "forward" (fn [[length depth aim] arg] [(+ length arg) (+ depth (* aim arg)) aim])
                 "up"      (fn [[length depth aim] arg] [length depth (- aim arg)])
                 "down"    (fn [[length depth aim] arg] [length depth (+ aim arg)])})

(defn doCommand2 [pos cmdString]
  (let [
        command (re-find #"\S+" cmdString )
        arg (read-string (re-find #"\d+" cmdString ))]
    ((commands2 command) pos arg)))

(let [finalPos (reduce doCommand2 [0 0 0] puzzleInput)]
  (* (finalPos 0) (finalPos 1)))
