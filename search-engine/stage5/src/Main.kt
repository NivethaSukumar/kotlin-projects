package search

import java.io.File

fun main(args: Array<String>) {

    if (args.size != 2 || args[0] != "--data") {
        println("Invalid input")
        return
    }
    val peopleData = File(args[1]).readLines()
    val invertedIndexData = mutableMapOf<String, MutableList<Int>>()
    createIndexedData(peopleData, invertedIndexData)
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
                println("Enter a name or email to search all suitable people.")
                val inputStr = readln()
                searchInList(peopleData, invertedIndexData, inputStr)
            }
            2 -> {
                println("=== List of people ===")
                printAllData(peopleData)
            }
            else -> println("Incorrect option! Try again.")
        }
    }

}

fun searchInList(peopleData: List<String>, invertedIndex: MutableMap<String, MutableList<Int>>, inputStr: String){
    if (invertedIndex.containsKey(inputStr))
    {
        for(element in invertedIndex[inputStr]!!)
        {
            println(peopleData[element])
        }
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
            addWordToInvertedIndex(invertedIndex, v, i)
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