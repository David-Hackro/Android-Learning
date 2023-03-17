package com.david.hackro.androidlearning

import androidx.lifecycle.ViewModel


/**
@see
https://kt.academy/article/cc-hot-cold
https://kt.academy/article/cc-hot-cold
 */
class ColdAndHotDataViewModel : ViewModel() {


    fun coldAndHotData() {
        hotData()
        coldData()

        hotAndColdDataBehavior()
    }

    /**
     * Are eager
     * independently consumption
     * Store elements
     * Multi Collectors
     * Emit data even when there is not collector
     * Collections (List, Set)
     * Channel
     */
    private fun hotData() {
        val l = buildList {
            /**Build start immediately */
            repeat(3) {
                add("item$it")
                println("Hot Data: $it")
            }
        }

        l.map {
            /**Operator start immediately */
            println("Hot Data: Processing")
            "Processed $it"
        }
    }

    /**
     * Are Lazy
     * On-Demand
     * Dont Store elements
     * FLow, RXJava Streams
     */
    private fun coldData() {
        val s = sequence {
            repeat(3) {
                yield("Item $it")
                println("Cold Data: $it")
            }
        }

        s.map {
            println("Cold Data: Processing")
            "Processed $it"
        }
    }


    private fun hotAndColdDataBehavior() {
        val list = listOf(1, 2, 3, 4, 5)
        /**
            Hot Data Stream apply the map operation over the list
            Next to, apply the find operation over the result (list) from above operation until find the element upper to 10
            Hot Stream  = map[1], map[2], map[3], map[4], map[5],    filter[1], filter[4], filter[9], filter[16]    ->  [16]
         */
        //Hot Data Stream
        list.map { //operator
            println("map operation / list #$it")
            it * it
        }.find {
            println("filter operation / list #$it")
            it >= 10
        }.let {
            println("Value upper 10 in the list #$it")
        }
        /**Results*/
        //map[1], map[2], map[3], map[4], map[5] -> filter[1], filter[4], filter[9], filter[16] -> [16]


        /**
            Cold Data Stream apply the map operation over the list
            Next to, apply the find operation over the result (current item) from above operation until find the element upper to 10
            Cold Stream = map[1], filter[2],    map[2], filter[4],   map[3], filter[9],     map[4], filter[16]      -> [16]
         */

        //Cold Data Stream
        val sequence = sequenceOf(1, 2, 3, 4, 5, 6)

        sequence.map {
            println("map operation / sequence #$it")
            it * it
        }.find {
            println("filter operation / sequence #$it")
            it >= 10
        }.let {
            println("Value upper 10 in the sequence #$it")
        }
        /**Results*/
        //map[1], filter[2], map[2], filter[4], map[3], filter[9], map[4], filter[16] -> [16]
    }


}