package phonebook

import java.io.File
import java.lang.Integer.max
import kotlin.math.floor
import kotlin.math.sqrt

fun main() {
    val allPhoneRecords:List<String> = getAllPhoneRecords()
    val phoneRecordsToFind:List<String> = getRecordsToSearch()

    val linearSearchTime = performLinearSearch(allPhoneRecords, phoneRecordsToFind)
    println()
    performBubbleSort(allPhoneRecords, phoneRecordsToFind, linearSearchTime)
}

private fun performLinearSearch(allPhoneRecords: List<String>, phoneRecordsToFind: List<String>): Long {
    println("Start searching (linear search)...")
    val startTime = System.currentTimeMillis()
    var totalFoundRecords: Int = doLinearSearch(allPhoneRecords, phoneRecordsToFind)
    val endTime = System.currentTimeMillis()
    println("Found $totalFoundRecords / ${phoneRecordsToFind.size} entries. Time taken: ${formatTime(endTime - startTime)}")
    return endTime - startTime
}

private fun doLinearSearch(allPhoneRecords: List<String>, namesToFind: List<String>): Int {
    var totalFoundRecords:Int = 0
    for (name in namesToFind) {
        for (nameWithPhone in allPhoneRecords) {
            if (nameWithPhone.contains(name)) {
                totalFoundRecords++
                break
            }
        }
    }
    return totalFoundRecords
}

fun performBubbleSort(allPhoneRecords: List<String>, phoneRecordsToFind: List<String>, linearSearchTime: Long): Unit {
    println("Start searching (bubble sort + jump search)...")
    val startTime = System.currentTimeMillis()
    val sortedRecords = allPhoneRecords.toMutableList()
    var sortCompleted = true
    for (i in sortedRecords.indices) {
        if (shouldStopSorting(linearSearchTime, startTime)) {
            sortCompleted = false
            break
        }
        for (y in i until sortedRecords.size) {
            if (sortedRecords[i] > sortedRecords[y]) {
                val temp = sortedRecords[i]
                sortedRecords[i] = sortedRecords[y]
                sortedRecords[y] = temp
            }
        }
    }
    val sortEndTime = System.currentTimeMillis()
    var totalFoundRecords = 0
    if (sortCompleted) {
        val jumpSize = floor(sqrt(sortedRecords.size.toDouble())).toInt()
        for (nameToFind in phoneRecordsToFind) {
            var previousBlockStartIndex = 0
            var nextBlockStartIndex = 0
            var isNameFound = false
            for (i in 0 until sortedRecords.size step jumpSize) {
                previousBlockStartIndex = nextBlockStartIndex
                nextBlockStartIndex = max(i, sortedRecords.size - 1)
                if (!isNameFound && sortedRecords[nextBlockStartIndex] >= nameToFind) {
                    for (y in previousBlockStartIndex..nextBlockStartIndex) {
                        if (sortedRecords[y] == nameToFind){
                            isNameFound = true
                            totalFoundRecords++
                            break
                        }
                    }
                }
            }
        }
        val searchEndTime = System.currentTimeMillis()
        println("Found $totalFoundRecords / ${phoneRecordsToFind.size} entries. Time taken: ${formatTime(searchEndTime - startTime)}")
        println("Sorting time: ${formatTime(sortEndTime - startTime)}")
        println("Searching time: ${formatTime(searchEndTime - sortEndTime)}")
    } else {
        var totalFoundRecords = doLinearSearch(allPhoneRecords, phoneRecordsToFind)
        val searchEndTime = System.currentTimeMillis()
        println("Found $totalFoundRecords / ${phoneRecordsToFind.size} entries. Time taken: ${formatTime(searchEndTime - startTime)}")
        println("Sorting time: ${formatTime(sortEndTime - startTime)} - STOPPED, moved to linear search")
        println("Searching time: ${formatTime(searchEndTime - sortEndTime)}")
    }
}

private fun formatTime(timeTaken: Long) = String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", timeTaken)

private fun shouldStopSorting(linearSearchTime: Long, startTime: Long): Boolean {
    val sortTimeTaken = System.currentTimeMillis() - startTime
    return linearSearchTime * 10 < sortTimeTaken
}

private fun getRecordsToSearch() = File("D:\\find.txt").readLines()

private fun getAllPhoneRecords() = File("D:\\directory.txt").readLines()

