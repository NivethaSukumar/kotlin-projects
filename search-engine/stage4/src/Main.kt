package search

import java.io.File

fun main(args: Array<String>) {

    if (args.size != 2 || args[0] != "--data") {
        println("Invalid input")
        return
    }
    val peopleData = File(args[1]).readLines()

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
                searchInList(peopleData, inputStr)
            }
            2 -> {
                println("=== List of people ===")
                printAllData(peopleData)
            }
            else -> println("Incorrect option! Try again.")
        }
    }

}

fun searchInList(list: List<String>, inputStr: String){
    for (i in list.indices){
        if(list[i].contains(inputStr, ignoreCase = true)){
            println(list[i])
        }
    }
}

fun printAllData(list:List<String>){
    for (entry in list){
        println(entry)
    }
}