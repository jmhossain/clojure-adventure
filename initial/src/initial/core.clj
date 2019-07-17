(ns initial.core)

;; The [& xx] means to take a variable number of arguments and place them
;; into a list.

(defn plus
 "Adds up numbers." 
  [& xx]  (apply + xx))


(defn socialist-plus
  "Adds up numbers [x_1, x_2, ... x_n].  If n>2, it taxes the result by subtracting
  (n-2).  If n<2 it adds one as a subsidy."
  [& xx] 
  ;; Your code here
(cond (< (count xx) 2) (+ (apply + xx) 1)
	true (- (apply + xx) (- (count xx) 2))
)
)


; (socialist-plus 10 20 30) => 59
; (socialist-plus 10 20 20 10) => 58

(defn capitalist-plus
  "Adds up numbers [x_1, x_2, ... x_n].  If n>2, it subsidizes the result by adding
  (n-2).  If n<2 it subtracts one as a tax."
  [& xx] 
  ;; Your code here

(cond (< (count xx) 2) (- (apply + xx) 1)
	true (+ (apply + xx) (- (count xx) 2))
)
)

; (capitalist-plus 10 20 30) => 61
; (capitalist-plus 10 20 20 10) => 62

(defn communist-plus
  "Adds up numbers.  To allow for equality the sum is always 10."
  ([& xx] 
  ;; Your code here 
10
   ))

(defn political-extreemist-plus
  "Adds up numbers like a political extreemist, i.e., by multiplying them.
  You get to pick which political extreemists this refers to."
  [& xx]
  ;; Your code here
(cond (empty? xx) 1
	true (apply * xx)
)
  )