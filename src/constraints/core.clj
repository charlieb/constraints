(ns constraints.core)

(defprotocol V2
  (add [this p])
  (sub [this p])
  (mul [this m])
  (div [this d])
  (mag [this])
  (norm [this])
  )

(defprotocol Constraints
  (dist< [this p distance] [this p distance correction-factor]) ;"Ensure distance is less than the value specified"
  (dist> [this p distance] [this p distance correction-factor]) ;"Ensure distance is greater than the value specified"
  (dist= [this p distance] [this p distance correction-factor]) ;"Ensure distance is less than the value specified"
  )

(defn- set-dist [p1 p2 d condition correction-factor]
  (let [delta (sub p2 p1)
        dist (mag delta)
        dir (norm delta)]
    (if (condition dist d)
      (sub p1 (mul (mul dir (- d dist))
                   correction-factor))
      p1)))

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

  Constraints
  (dist< [p1 p2 dst] (dist< p1 p2 dst 1.0))
  (dist< [p1 p2 dst factor] (set-dist p1 p2 dst > factor))

  (dist> [p1 p2 dst] (dist> p1 p2 dst 1.0))
  (dist> [p1 p2 dst factor] (set-dist p1 p2 dst < factor))

  (dist=[p1 p2 dst] (dist< p1 p2 dst 1.0))
  (dist= [p1 p2 dst factor] (set-dist p1 p2 dst (constantly true) factor)))



(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
