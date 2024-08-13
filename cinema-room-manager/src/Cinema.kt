package cinema

fun main() {

    println("Enter the number of rows:")
    val noOfRows = readln().toInt()
    println("Enter the number of seats in each row:")
    val noOfSeats = readln().toInt()
    var row: Int?
    var seat: Int?
    val selectedSeats = mutableSetOf<Set<Pair<Int, Int>>>()
    var currentIncome: Int = 0

    while (true) {
        println("1. Show the seats\n" +
                "2. Buy a ticket\n" +
                "3. Statistics\n" +
                "0. Exit")
        val selectedOption = readln().toInt()
        when(selectedOption) {
            1 ->  printCinemaHall(noOfRows, noOfSeats, selectedSeats)
            2 -> {
                while (true) {
                    println("Enter a row number:")
                    row = readln().toInt()
                    println("Enter a seat number in that row:")
                    seat = readln().toInt()
                    if (row > noOfRows || seat > noOfSeats) {
                        println("Wrong input!")
                    } else if (!addSelectedSeat(selectedSeats, setOf(Pair(row, seat)))) {
                            println("That ticket has already been purchased!")
                    } else {
                        println("Ticket price: ")
                        currentIncome += calculateTicketPrice(noOfRows, noOfSeats, row)
                        println("$${calculateTicketPrice(noOfRows, noOfSeats, row)}")
                        break
                    }
                }
            }
            3 -> {
                val purchasedTickets = selectedSeats.size
                val percentage = calculatePercentage(noOfRows, noOfSeats, purchasedTickets)
                val formatPercentage = "%.2f".format(percentage)
                println("Number of purchased tickets: $purchasedTickets")
                println("Percentage: $formatPercentage%")
                println("Current income: $${currentIncome}")
                println("Total income: $${totalIncome(noOfRows, noOfSeats)}")
            }
            0 -> break
            else -> println("Incorrect option")
        }
    }
}

fun addSelectedSeat(set: MutableSet<Set<Pair<Int, Int>>>, setToAdd: Set<Pair<Int, Int>>):Boolean {
    if (set.contains(setToAdd)) {
        return false
    } else {
        set.add(setToAdd)
    }
    return true
}

fun calculateTicketPrice(totalRows: Int, totalSeats: Int, row: Int):Int {
    if(totalRows*totalSeats <= 60)
    {
        return 10
    }
    else {
        val firstRows = totalRows/2
        return if(row <= firstRows) {
            10
        } else {
            8
        }

    }
}
fun calculatePercentage(totalRows: Int, totalSeats: Int, purchasedTickets:Int) :Double {
    return if (purchasedTickets >= 1) {
        (purchasedTickets.toDouble()/(totalRows * totalSeats))*100.0
    } else {
        0.0
    }
}

fun totalIncome(totalRows: Int, totalSeats: Int): Int {
    if (totalRows * totalSeats <= 60) {
        return totalRows*totalSeats*10
    } else {
        val firstRows = totalRows / 2
        val firstRowsCost = firstRows*totalSeats*10
        val remainingRowsCost = (totalRows-firstRows)*totalSeats*8
        return firstRowsCost+remainingRowsCost
    }
}
fun printCinemaHall(rows: Int, seats: Int, chosenSeats: MutableSet<Set<Pair<Int, Int>>>) {
    println("Cinema:")
    print("  ")
    for (col in 1..seats) {
        print("$col ")
    }
    println()

    // Print rows with seats
    for (row in 1..rows) {
        print("$row ")
        for (col in 1..seats) {
            if(chosenSeats.contains(setOf(Pair(row, col)))) print("B ") else print("S ")
        }
        println()
    }
}