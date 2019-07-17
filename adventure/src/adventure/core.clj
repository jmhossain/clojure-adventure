(ns adventure.core
  (:require [clojure.core.match :refer [match]]
            [clojure.string :as str])
  (:gen-class))

(def the-map
  {      :foyer {:desc "The walls are freshly painted but do not have any pictures.
  You get the feeling it was just created for a game or something. "
                 :title "in the foyer"
                 :dir {:south :grue-pen
                       :north :store-room}
                 :contents #{:raw-egg}}
      :grue-pen {:desc "It is very dark.  You are about to be eaten by a grue. "
                 :title "in the grue pen"
                 :dir {:north :foyer}
                 :contents #{}}
    :store-room {:desc "All sorts of things are scattered around the room. "
                 :title "in the store room"
                 :dir {:east :stairs
                       :west :corridor
                       :south :foyer}
                 :contents #{:light-source, :sword, :orange, :magnify-glass}}
        :stairs {:desc "Rays of sunlight pouring in. You can see a window upstairs "
                 :title "walking up the stairs"
                 :dir {:up :treasure-room
                       :down :store-room}
                 :contents #{}}
      :corridor {:desc "A rather dim passageway "
                 :title "at a corridor"
                 :dir {:north :hidden-room
                       :east :store-room
                       :south :grue-pen
                       :west :forked-path}
                 :contents #{:eerie-doll}}
   :hidden-room {:desc "A small, dimly lit room with a small box hidden at a corner on the floor "
                 :title "in the hidden room"
                 :dir {:south :corridor}
                 :contents #{:key}}
   :forked-path {:desc "2 paths lead to 2 vastly different outcomes "
                 :title "choosing your destiny"
                 :dir {:north :outside
                       :south :pit-of-death
                       :east :corridor}
                 :contents #{:eyeball}}
       :outside {:desc "You have escaped the abyss and can see exactly how you had fallen in. No need to be scared of the grue anymore. Hooray!! "
                 :title "a lucky save"
                 :dir {}
                 :contents #{:loneliness}}
  :pit-of-death {:desc "You are falling into a pit of fire and there seems to be no end in sight "
                 :title "an unlucky choice"
                 :dir {}
                 :contents #{:death}}
 :treasure-room {:desc "A big chest lies in the middle of an otherwise empty room. You will need to use the command unlock to see if you can unlock it "
                 :title "in the treasure room"
                 :dir {:west :stairs}
                 :contents #{:treasure-chest}}
   })

(def adventurer
  {:location :foyer
   :inventory #{}
   :tick 0
   :HP 10
   :seen #{}})

(defn status [player]
  (let [location (player :location)]
    (print (str "You are " (-> the-map location :title) ". "))
    (when-not ((player :seen) location)
      (print (-> the-map location :desc)))
    (update-in player [:seen] #(conj % location))))

(defn to-keywords [commands]
  (mapv keyword (str/split commands #"[.,?! ]+")))

(defn go [dir player]
  (let [location (player :location)
        dest (->> the-map location :dir dir)]
    (if (nil? dest)
      (do (println "You can't go that way.")
          player)
      (assoc-in player [:location] dest))))

(defn find-key [player]
  (let [location (player :location)]
    (cond (contains? (player :inventory) :key) (do (println "Key is already in inventory") player)
          (contains? (-> the-map location :contents) :key) (update-in player [:inventory] #(conj % :key))
          (= location :grue-pen) (do (println "Better not or grue will eat you") player)
          :else (do (println "Key is not in current location") player))))

(defn unlock [player]
  (let [location (player :location)]
    (cond (not (contains? (-> the-map location :contents) :treasure-chest)) (do (println "There is nothing to unlock here") player)
          (contains? (player :inventory) :key) (do (println "You have opened the treasure chest and now all riches belong to you") player)
          :else (do (println "You do not have the key to the treasure chest") player))))

(defn pick-up [player]
  (let [location (player :location)
        contents (-> the-map location :contents)]
    (cond (contains? (player :inventory) contents) (do (println "Contents is already in inventory") player)
          :else (update-in player [:inventory] #(conj % contents)))))

(defn eat [player]
  (cond (contains? (player :inventory) :orange) (do (println "Ate an orange") player)
        (contains? (player :inventory) :eyeball) (do (println "Ate an eyeball. The cruch of the first bite felt stangely satisfying") player)
        (contains? (player :inventory) :raw-egg) (do (println "Broke into a raw egg. The watery egg yolk quenched your thirst") player)
        :else (do (println "There is nothing edible in the inventory") player)))

(defn damage [player]
  (update-in player [:HP] dec))

(defn tock [player]
  (update-in player [:tick] inc))

(defn respond [player command]
  (match command
         [:peek] (update-in player [:seen] #(disj % (-> player :location)))
         [:search] (find-key player)
         [:unlock] (unlock player)
         [:pick-up] (pick-up player)
         [:eat] (eat player)
         [:south] (go :south player)
         [:north] (go :north player)
         [:east] (go :east player)
         [:west] (go :west player)
         [:up] (go :up player)
         [:down] (go :down player)

         _ (do (println "I don't understand you.")
               player)

         ))

(defn -main
  [& args]
  (loop [local-map the-map
         local-player adventurer]
    (let [pl (status local-player)
          _  (println "What do you want to do?")
          command (read-line)]
    (recur local-map (respond pl (to-keywords command))))))
