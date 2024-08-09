package search

fun main() {
    val input: List<String> = readln().split(" ")
    val searchWord = readln()
    val n = input.size-1
    for (i in 0..n){
        if (searchWord == input[i]){
            println(i+1)
            return
        }
    }
    println("Not found")
}
