(ns constraints.core)

(defn add [p1 p2] {:x (+ (.x p1) (.x p2)) :y (+ (.y p1) (.y p2))})
(defn sub [p1 p2] {:x. (- (.x p1) (.x p2)) :y (- (.y p1) (.y p2))})
(defn mul [p1 m] {:x. (* (.x p1) m) :y (* (.y p1) m)})
(defn div [p1 d] {:x. (/ (.x p1) d) :y (/ (.y p1) d)})
(defn mag [p] (Math/sqrt (+ (* (.x p) (.x p)) (* (.y p) (.y p)))))
(defn norm [p] (div p (mag p)))

(defn- set-dist [p1 p2 d condition correction-factor]
  (let [delta (sub p2 p1)
        dist (mag delta)
        dir (norm delta)]
    (if (condition dist d)
      (sub p1 (mul (mul dir (- d dist))
                   correction-factor))
      p1)))

(defn dist<
  ([p1 p2 dst] (dist< p1 p2 dst 1.0))
  ([p1 p2 dst factor] (set-dist p1 p2 dst > factor)))

(defn dist> 
  ([p1 p2 dst] (dist> p1 p2 dst 1.0))
  ([p1 p2 dst factor] (set-dist p1 p2 dst < factor)))

(defn dist= 
  ([p1 p2 dst] (dist< p1 p2 dst 1.0))
  ([p1 p2 dst factor] (set-dist p1 p2 dst (constantly true) factor)))

(defn step
  "Step the verlet simulation for this point"
  [p]
  {:pos 
   (-> (:pos p)
       (mul 2.)
       (sub (:prev-pos p)))
   :prev-pos (:pos p)})


(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
