package search

fun main() {
    println("Enter the number of people: ")
    val input = readln().toInt()
    println("Enter all people: ")
    val myList = MutableList(input) { "" }
    for (i in 0 until input){
        val data: String = readln()
        myList[i] = data
    }
    
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
                searchInList(myList, inputStr)
            }
            2 -> {
                println("=== List of people ===")
                printAllData(myList)
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