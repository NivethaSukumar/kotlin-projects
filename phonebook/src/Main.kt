package phonebook

import java.io.File
import kotlin.math.sqrt

fun main() {
    val directory = File("./src/resources/directory.txt").readLines()
    val namesToFind = File("./src/resources/find.txt").readLines()

    println("Start searching (linear search)...")
    val startTime = System.nanoTime()
    // Do a linear search
    var foundCount = 0
    for (name in namesToFind) {
        if (linearSearch(directory, name)) {
            foundCount++
        }
    }
    val endTime = System.nanoTime()

    val searchTime1 = endTime - startTime

    val timeDetails = calculateDuration(searchTime1)
    println("Found $foundCount / ${namesToFind.size} entries. Time taken: ${timeDetails.min} min. ${timeDetails.sec} sec. ${timeDetails.ms} ms. ")

    sortAndJumpSearch(directory, namesToFind)
    quickSortAndBinarySearch(directory, namesToFind)
    hashTableAndSearch(directory, namesToFind)
}
fun linearSearch(directory: List<String>, name:String): Boolean {
    for (entry in directory) {
        if (entry.contains(name)) {
            return true
        }
    }
    return false
}

fun bubbleSort(directory: MutableList<String>) {
    val n = directory.size
    for (i in 0 until n-1) {
        for (j in 0 until n-i-1) {
            val name1 = directory[j].split(" ")[1]
            val name2 = directory[j+1].split(" ")[1]
            if (name1 > name2) {
                val temp = directory[j]
                directory[j] = directory[j+1]
                directory[j+1] = temp
            }
        }
    }
}

data class TimeDetails(val min:Long, val sec: Long, val ms: Long)

fun calculateDuration (timeDiff: Long): TimeDetails{
    val duration = timeDiff / 1_000_000
    val minutes = duration / 60000
    val seconds = (duration % 60000) / 1000
    val milliseconds = duration % 1000
    return TimeDetails(minutes, seconds, milliseconds)
}

fun jumpSearch (sortedDirectory: List<String>, name: String) : Boolean {
    val jumpVal = sqrt(sortedDirectory.size.toDouble()).toInt()
    val n = sortedDirectory.size
    var targetIndex = -1
    var foundTarget = false
    for (i in 0 until n-1 step jumpVal) {
        val entryName = sortedDirectory[i].split(" ")[1]
        if (name < entryName) {
            targetIndex = i
        } else if (name == entryName) {
            foundTarget = true
        }
    }

    // perform backward search in that block
    if (targetIndex >=0) {
        for (i in targetIndex downTo  targetIndex-jumpVal ) {
            if (sortedDirectory[i].contains(name)) {
                foundTarget = true
            }
        }
    }
    // perform linear search with the remaining values
    if (!foundTarget){
        for (i in jumpVal until n) {
            if (sortedDirectory[i].contains(name)) {
                foundTarget = true
            }
        }
    }
    return foundTarget

}
fun internalSort(directory: List<String>): List<String> {
    // to speed things up use this sort
    return directory.sortedBy { name(it) }
}

fun sortAndJumpSearch (directory: List<String>, namesToFind: List<String>) {
    println("Start searching (bubble sort + jump search)...")
    var startTime = System.nanoTime()
    val sortedDirectory : List<String> = internalSort(directory)
    // Bubble sort is slow as hell
    // bubbleSort(sortedDirectory)

    var endTime = System.nanoTime()
    val internalSortTime = endTime - startTime
    val internalSortDetails = calculateDuration(internalSortTime)

    // Perform search
    // var movedToLinearSearch = false
    startTime = System.nanoTime()
    var foundCount = 0
    for (name in namesToFind) {
//        if (internalSortTime / searchTime1 > 10) {
//            movedToLinearSearch = true
//            linearSearch(directory, name)
//        } else
//        {
//            print("\n")
        if (jumpSearch(sortedDirectory, name)) {
            foundCount++
        }
    }
    endTime = System.nanoTime()
    val searchTime = endTime - startTime
    val searchTimeDetails = calculateDuration(searchTime)


    val totalTime = internalSortTime + searchTime
    val totalTimeDetails = calculateDuration(totalTime)

    println("Found $foundCount / ${namesToFind.size} entries. Time taken: ${totalTimeDetails.min} min. ${totalTimeDetails.sec} sec. ${totalTimeDetails.ms} ms. ")
    println("Sorting time: ${internalSortDetails.min} min. ${internalSortDetails.sec} sec. ${internalSortDetails.ms} ms.")
    // if (movedToLinearSearch) print(" - STOPPED, moved to linear search\n")
    println("Searching time: ${searchTimeDetails.min} min. ${searchTimeDetails.sec} sec. ${searchTimeDetails.ms} ms.")
}

fun name(line: String): String {
    return line.substringAfter(" ")
}

fun quickSortAndBinarySearch(directory: List<String>, namesToFind: List<String>) {
    println("Start searching (quick sort + binary search)...")
    //TODO: use quick sort algorithm
    var startTime = System.nanoTime()
    val sortedDirectory = directory.toMutableList()
    quickSort(sortedDirectory, 0, directory.size-1)
    var endTime = System.nanoTime()
    val internalSortTime = endTime - startTime
    val internalSortDetails = calculateDuration(internalSortTime)

    startTime = System.nanoTime()
    var foundCount = 0
    for (name in namesToFind) {
        if (binarySearch(sortedDirectory, name)) {
            foundCount++
        }
    }
    endTime = System.nanoTime()
    val searchTime = endTime - startTime
    val searchTimeDetails = calculateDuration(searchTime)


    val totalTime = internalSortTime + searchTime
    val totalTimeDetails = calculateDuration(totalTime)

    println("Found $foundCount / ${namesToFind.size} entries. Time taken: ${totalTimeDetails.min} min. ${totalTimeDetails.sec} sec. ${totalTimeDetails.ms} ms. ")
    println("Sorting time: ${internalSortDetails.min} min. ${internalSortDetails.sec} sec. ${internalSortDetails.ms} ms.")
    println("Searching time: ${searchTimeDetails.min} min. ${searchTimeDetails.sec} sec. ${searchTimeDetails.ms} ms.")
}

fun binarySearch(list: List <String>, name: String): Boolean {
    var low = 0
    var high = list.size -1
    while (low <= high) {
        var mid = (low + high) / 2
        if (name(list[mid]) == name) {
            return true
        } else if (name < name(list[mid])) {
            high = mid - 1
        } else {
            low = mid + 1
        }
    }
    return false
}

fun partition (arr: MutableList<String>, low: Int, high: Int): Int  {
    val pivot = arr[high]
    var i = low - 1
    for (j in low until high) {
        if (name(arr[j]) < name(pivot)) {
            i++
            val temp = arr[i]
            arr[i] = arr[j]
            arr[j] = temp
        }
    }
    val temp = arr[i + 1]
    arr[i + 1] = arr[high]
    arr[high] = temp
    return i + 1
}
fun quickSort(directory: MutableList<String>, low: Int, high: Int){
    if (low < high){
        val pivot = partition(directory, low, high)
        quickSort(directory, low, pivot-1)
        quickSort(directory, pivot+1, high)
    }
}

fun hashTableAndSearch(directory: List<String>, namesToFind: List<String>) {
    println("Start searching (hash table)...")
    var startTime = System.nanoTime()
    val sortedDirectoryMap = createHashMap(directory)

    var endTime = System.nanoTime()
    val internalSortTime = endTime - startTime
    val internalSortDetails = calculateDuration(internalSortTime)

    startTime = System.nanoTime()
    var foundCount = 0
    for (name in namesToFind) {
        if (searchInHashMap(sortedDirectoryMap, name)) {
            foundCount++
        }
    }
    endTime = System.nanoTime()
    val searchTime = endTime - startTime
    val searchTimeDetails = calculateDuration(searchTime)


    val totalTime = internalSortTime + searchTime
    val totalTimeDetails = calculateDuration(totalTime)

    println("Found $foundCount / ${namesToFind.size} entries. Time taken: ${totalTimeDetails.min} min. ${totalTimeDetails.sec} sec. ${totalTimeDetails.ms} ms. ")
    println("Creating time: ${internalSortDetails.min} min. ${internalSortDetails.sec} sec. ${internalSortDetails.ms} ms.")
    println("Searching time: ${searchTimeDetails.min} min. ${searchTimeDetails.sec} sec. ${searchTimeDetails.ms} ms.")
}
fun createHashMap(directory: List<String>): HashMap <String, String> {
    val map = HashMap<String, String>()
    for (entry in directory) {
        map[name(entry)] = entry.split( " ")[0]

    }
    return map
}
fun searchInHashMap(map: HashMap<String, String>, name: String): Boolean {
    return map.containsKey(name)
}