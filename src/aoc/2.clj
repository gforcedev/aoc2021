; Read the input data into a vector of maps of the form {:command "command-string" :arg <number>}
(defn interpretCommand [cmdString]
  {:command (re-find #"\S+" cmdString)
   :arg (read-string (re-find #"\d+" cmdString))})

(def puzzleInput (into [] (map interpretCommand (clojure.string/split (slurp "assets/input2.txt") #"\n"))))

; Part 1 - product of length and depth after instruction list
(def commands {
                 "forward" (fn [[length depth] arg] [(+ length arg) depth])
                 "up"      (fn [[length depth] arg] [length (- depth arg)])
                 "down"    (fn [[length depth] arg] [length (+ depth arg)])})

(reduce * (reduce (fn [pos command] ((commands (command :command)) pos (command :arg))) [0 0] puzzleInput))

; Part 2 - same problem only now aim is involved
(def commands2 {
                 "forward" (fn [[length depth aim] arg] [(+ length arg) (+ depth (* aim arg)) aim])
                 "up"      (fn [[length depth aim] arg] [length depth (- aim arg)])
                 "down"    (fn [[length depth aim] arg] [length depth (+ aim arg)])})

(let [finalPos
      (reduce (fn [pos command] ((commands2 (command :command)) pos (command :arg))) [0 0 0] puzzleInput)]
  (* (finalPos 0) (finalPos 1)))

