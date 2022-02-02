(ns clojure-noob.spidey
  (:require [clojure.string :as string]
            [clojure.pprint :as pp]))

(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])


(defn matching-part
  [part]
  {:name (string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts
         final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
               (into final-body-parts
                     (set [part (matching-part part)])))))))

;;; Same same but with reduce:

(defn better-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-part part)])))
          []
          asym-body-parts))

(pp/pprint (symmetrize-body-parts asym-hobbit-body-parts))

;;;; Exercises ;;;;
;;;; Exercise-1 ;;;;

(str "dog went" " over " 3 " bridges with " {:key "val"})
(vector "dog went" " over " 3 " bridges with " {:key "val"})
(list "dog went" " over " 3 " bridges with " {:key "val"})
(hash-map "dog went" " over " 3 " bridges with ")
(hash-set "dog went" " over " 3 " bridges with " {:key "val"})

;;;; Exercise-2 ;;;;
;; Write a function that takes a number and adds 100 to it.

(defn add-100 [n]
  (+ n 100))

(add-100 3)

;;;; Exercise-3 ;;;;
;; Write a function, dec-maker, that works exactly like the function inc-maker except with subtraction:

(defn inc-maker
  "Create a custom incrementor"
  [inc-by]
  #(+ % inc-by))

(def inc3 (inc-maker 3))

(inc3 7)

(defn dec-maker [dec-by]
  #(- % dec-by))

(def dec9 (dec-maker 9))

(dec9 10)

;;;; Exercise-4 ;;;;
;;Write a function, mapset, that works like map except the return value is a set:

(defn mapset [function array]
  (set (map function array)))

(mapset inc [1 1 2 2])

;;;; Exercise-5 ;;;;
;; Create a function that’s similar to symmetrize-body-parts except that it has to work with weird space aliens with radial symmetry. 
;; Instead of two eyes, arms, legs, and so on, they have five.

(def mad-bug-parts [{:name "head" :size 3}
                    {:name "left-eye" :size 1}
                    {:name "left-ear" :size 1}
                    {:name "mouth" :size 1}
                    {:name "nose" :size 1}
                    {:name "neck" :size 2}
                    {:name "left-shoulder" :size 3}
                    {:name "left-upper-arm" :size 3}
                    {:name "chest" :size 10}
                    {:name "back" :size 10}
                    {:name "left-forearm" :size 3}
                    {:name "abdomen" :size 6}
                    {:name "left-kidney" :size 1}
                    {:name "left-hand" :size 2}
                    {:name "left-knee" :size 2}
                    {:name "left-thigh" :size 4}
                    {:name "left-lower-leg" :size 3}
                    {:name "left-achilles" :size 1}
                    {:name "left-foot" :size 2}])

(defn fivefold-it? [part]
  (clojure.string/includes? (:name part) "left-"))

(defn create-fivefold [part]
  (let [thing-to-multiply (apply str (rest (string/split (:name part) #"-" 2)))]
    (repeat 5 {:name thing-to-multiply
               :size (:size part)})))

(defn multiply-bug-parts
  "Expects a seq of maps that have a :name and :size"
  [body-parts]
  (reduce (fn [final-body-parts part]
            (if (fivefold-it? part)
              (concat final-body-parts (create-fivefold part))
              (conj final-body-parts part)))
          []
          body-parts))

(multiply-bug-parts mad-bug-parts)

;; Or simplr with just parts and given list of the multiply-y ones

(def mad-bug-parts-simply [{:name "head" :size 3}
                           {:name "eye" :size 1}
                           {:name "ear" :size 1}
                           {:name "mouth" :size 1}
                           {:name "nose" :size 1}
                           {:name "neck" :size 2}
                           {:name "shoulder" :size 3}
                           {:name "upper-arm" :size 3}
                           {:name "chest" :size 10}
                           {:name "back" :size 10}
                           {:name "forearm" :size 3}
                           {:name "abdomen" :size 6}
                           {:name "kidney" :size 1}
                           {:name "hand" :size 2}
                           {:name "knee" :size 2}
                           {:name "thigh" :size 4}
                           {:name "lower-leg" :size 3}
                           {:name "achilles" :size 1}
                           {:name "foot" :size 2}])

(defn double-it? [part]
  (let [double-parts ["eye" "neck" "knee" "foot"]]
    (.contains double-parts (:name part))))

(defn double-parts [parts]
  (mapcat #(if (double-it? %) [% %] [%]) parts))

(double-parts mad-bug-parts-simply)

;;;; Exercise-6 ;;;;
;; Create a function that generalizes symmetrize-body-parts and the function you created in Exercise 5. 
;; The new function should take a collection of body parts and the number of matching body parts to add

(defn multiply-it? [part]
  (clojure.string/includes? (:name part) "left-"))

(defn multiply-by [part x]
  (let [thing-to-multiply (apply str (rest (string/split (:name part) #"-" 2)))]
    (repeat x {:name thing-to-multiply
               :size (:size part)})))

(defn multiply-bug-parts-by-x
  "Expects a seq of maps that have a :name and :size"
  [body-parts x]
  (reduce (fn [final-body-parts part]
            (if (multiply-it? part)
              (concat final-body-parts (multiply-by part x))
              (conj final-body-parts part)))
          []
          body-parts))

(multiply-bug-parts-by-x mad-bug-parts 3)
