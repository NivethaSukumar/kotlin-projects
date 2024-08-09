package search

import java.awt.image.BufferStrategy
import java.io.File

fun main(args: Array<String>) {

    if (args.size != 2 || args[0] != "--data") {
        println("Invalid input")
        return
    }
    val peopleData = File(args[1]).readLines()
    val invertedIndexData = mutableMapOf<String, MutableList<Int>>()
    createIndexedData(peopleData, invertedIndexData)
//    println(invertedIndexData)
    while (true) {
        println(
            """=== Menu ===
1. Find a person
2. Print all people
0. Exit"""
        )
        val option = readln().toInt()

        when(option) {
            0 -> {
                println("Bye!")
                break
            }
            1 -> {
                println("Select a matching strategy: ALL, ANY, NONE")
                val strategy = readln()
                println("Enter a name or email to search all suitable people.")
                val inputStr = readln()
                searchInList(peopleData, invertedIndexData, inputStr, strategy)
            }
            2 -> {
                println("=== List of people ===")
                printAllData(peopleData)
            }
            else -> println("Incorrect option! Try again.")
        }
    }

}

fun searchInList(peopleData: List<String>, invertedIndex: MutableMap<String, MutableList<Int>>, inputStr: String, strategy: String){
    val words = inputStr.lowercase().split(" ")
    println("------------------------------------------>$words")
    when(strategy){
        "ALL" -> {
            val searchListofList = mutableListOf<MutableList<Int>>()

           for (word in words) {
               if (invertedIndex.containsKey(word)) {
                   searchListofList.add(invertedIndex[word]!!)
               }
           }
            val searchList = searchListofList.flatten()
            if(searchList.size >= 1)
            {
                val searchIndex = searchList.groupingBy { it }.eachCount().maxByOrNull { it.value }!!.key
                println(searchIndex)
                println(peopleData[searchIndex])
            }
            else {
                println("No matching people found.")
            }
           

        }
        "ANY" -> {
            for (word in words) {
                if (invertedIndex.containsKey(word))
                {
                    for (element in invertedIndex[word]!!) {
                        println(peopleData[element])
                    }
                }
            }
        }
        "NONE" -> {
            val exclusionList = mutableListOf<MutableList<Int>>()
            for(word in words) {
                if(invertedIndex.containsKey(word))
                {
                    invertedIndex[word]?.let { exclusionList.add(it) }
                }
            }
            val excludedIndices = exclusionList.flatten().toSet()
//            println(excludedIndices)
            for (i in peopleData.indices) {
                if(i !in excludedIndices) {
                    println(peopleData[i])
                }
            }
        }
        else -> println("Unknown strategy")
    }
}


fun printAllData(list:List<String>){
    for (entry in list){
        println(entry)
    }
}
fun createIndexedData(list:List<String>, invertedIndex: MutableMap<String, MutableList<Int>>) {
    for (i in list.indices) {
        val values = list[i].split(" ")
        for (v in values) {
            addWordToInvertedIndex(invertedIndex, v.lowercase(), i)
        }
    }
}
fun addWordToInvertedIndex(invertedIndex: MutableMap<String, MutableList<Int>>, word: String, index:Int) {
    if (invertedIndex.containsKey(word)) {
        invertedIndex[word]!!.add(index)
    } else {
        invertedIndex[word] = mutableListOf(index)
    }
}