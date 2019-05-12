(ns constraints.core)

(defprotocol V2
  (add [this p])
  (sub [this p])
  (mul [this m])
  (div [this d])
  (mag [this])
  (norm [this])
  )

(deftype Point [x y] 
  V2
  (add [p1 p2] (Point. (+ (.x p1) (.x p2)) 
                       (+ (.y p1) (.y p2))))
  (sub [p1 p2] (Point. (- (.x p1) (.x p2)) 
                       (- (.y p1) (.y p2))))
  
  (mul [p1 m] (Point. (* (.x p1) m) 
                      (* (.y p1) m)))
  (div [p1 d] (Point. (/ (.x p1) d) 
                      (/ (.y p1) d)))

  (mag [p] (Math/sqrt (+ (* (.x p) (.x p))
                         (* (.y p) (.y p)))))

  (norm [p] (div p (mag p)))
  )


(defn set-dist [p1 p2 d condition correction-factor]
  (let [delta (sub p2 p1)
        dist (mag delta)
        dir (norm delta)]
    (if (condition dist d)
      (sub p1 (mul (mul dir (- d dist))
                   correction-factor))
      p1)))

(defn mk-circle [x y r] {:c (Point. x y) :r r :links [] :id 0})
(defn add-circle [c cs] 
  (let [id (+ 1 (max (map :id cs)))]
    (conj (assoc c :id id) cs)))

(defn exclude
  "Return c1 updated such that the overlap with c2 eliminted"
  ([c1 c2] (exclude c1 c2 1.0))
  ([c1 c2 fac]
   (assoc c1
          :c (set-dist (:c c1) (:c c2)
                       (+ (:r c1) (:r c2)) ; desired-dist
                       <
                       fac))))

(defn fix-dist 
  "Return updated c1 such that the distance to center of c2 is fixed"
  ([c1 c2] (fix-dist c1 c2 1.0))
  ([c1 c2 fac]
   (assoc c1
          :c (set-dist (:c c1) (:c c2)
                       (+ (:r c1) (:r c2)) ; desired-dist
                       (constantly true)
                       1.0))))

(defn keep-within [c1 c2] "Return updated c1 such that it stays within c2"
  (assoc c1
         :c (set-dist (:c c1) (:c c2)
                      (- (:r c2) (:r c1)) ; desired-dist
                      >
                      1.0)))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
