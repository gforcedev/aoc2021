(->> "assets/input8.txt"
     slurp
     (re-seq #"(\w+ \w+ \w+ \w+)\n")
     (mapv #(first (rest %)))
     (mapv #(re-seq #"\w+" %))
     flatten
     (filter #(contains? {2 1, 4 4, 3 7, 7 8} (count %)))
     count)

; Some thinking
(def digits-to-count {
                      0 6
                      1 2
                      2 5
                      3 5
                      4 4
                      5 5
                      6 6
                      7 3
                      8 7
                      9 6})

