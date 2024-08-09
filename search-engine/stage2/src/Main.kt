package search

fun main() {
    println("Enter the number of people: ")
    val input = readln().toInt()
    println("Enter all people: ")
    val myList = MutableList(input) { "" }
    for (i in 0..input-1){
        val data: String = readln()
        myList[i] = data
    }
    println("Enter the number of search queries: ")
    val searchQueries = readln().toInt()

    for (i in 0..searchQueries-1) {
        println("Enter data to search people: ")
        val inputStr = readln()
        println("People found:")
        var found = false
        for (i in 0..myList.size-1){
            if(myList[i].contains(inputStr, ignoreCase = true)){
                found = true
                println(myList[i])
            }
        }
        if(!found) println("No matching people found.")
    }
}