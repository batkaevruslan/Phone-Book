package phonebook

import java.io.File
import java.lang.Integer.max
import kotlin.math.floor
import kotlin.math.sqrt

fun main() {
    val allPhoneRecords:List<PhoneRecord> = getAllPhoneRecords()
    val phoneNamesToFind:List<String> = getRecordsToSearch()
    val linearSearchTime = performLinearSearch(allPhoneRecords, phoneNamesToFind)
    println()
    performJumpSearch(allPhoneRecords, phoneNamesToFind, linearSearchTime)
    println()
    performBinarySearch(allPhoneRecords, phoneNamesToFind)
    println()
    performHashBasedSearch(allPhoneRecords, phoneNamesToFind)
}

fun performHashBasedSearch(allPhoneRecords: List<PhoneRecord>, phoneNamesToFind: List<String>) {
    println("Start searching (hash table)...")
    val startTime = System.currentTimeMillis()
    val phoneRecords = HashTable((allPhoneRecords.size * 1.25).toInt())
    //val phoneRecords = HashMap<String, String>()
    for (record in allPhoneRecords) {
        phoneRecords.put(record.person, record.phone)
    }
    val hashTableCreationEndTime = System.currentTimeMillis()
    var totalFoundRecords = 0
    for (name in phoneNamesToFind) {
        if (phoneRecords.get(name) != null) {
            totalFoundRecords++
        }
    }
    val searchEndTime = System.currentTimeMillis()
    println("Found $totalFoundRecords / ${phoneNamesToFind.size} entries. Time taken: ${formatTime(searchEndTime - startTime)}")
    println("Creating time: ${formatTime(hashTableCreationEndTime - startTime)}")
    println("Searching time: ${formatTime(searchEndTime - hashTableCreationEndTime)}")
}

fun performBinarySearch(allPhoneRecords: List<PhoneRecord>, phoneNamesToFind: List<String>) {
    println("Start searching (quick sort + binary search)...")
    val startTime = System.currentTimeMillis()
    val sortedRecords = allPhoneRecords.toTypedArray()
    doQuickSort(sortedRecords, 0, sortedRecords.lastIndex)
    val sortEndTime = System.currentTimeMillis()

    var totalFoundRecords = 0
    for (name in phoneNamesToFind) {
        if (doBinarySearch(sortedRecords, name, 0, sortedRecords.lastIndex) != -1) {
            totalFoundRecords++
        }
    }
    val searchEndTime = System.currentTimeMillis()
    println("Found $totalFoundRecords / ${phoneNamesToFind.size} entries. Time taken: ${formatTime(searchEndTime - startTime)}")
    println("Sorting time: ${formatTime(sortEndTime - startTime)}")
    println("Searching time: ${formatTime(searchEndTime - sortEndTime)}")
}

fun doBinarySearch(allPhoneRecords: Array<PhoneRecord>, name: String, startIndex: Int, endIndex: Int): Int {
    if (endIndex == startIndex) {
        return if (allPhoneRecords[endIndex].person == name) {
            endIndex
        } else {
            -1
        }
    }
    val middleIndex = (startIndex + endIndex) / 2
    val middleElement = allPhoneRecords[middleIndex]
    return if (middleElement.person >= name) {
        doBinarySearch(allPhoneRecords, name, startIndex, middleIndex)
    } else {
        doBinarySearch(allPhoneRecords, name, middleIndex + 1, endIndex)
    }
}

fun doQuickSort(records: Array<PhoneRecord>, left: Int, right: Int) {
    if (left < right) {
        val pivotIndex = doPartition(records, left, right)
        doQuickSort(records, left, pivotIndex - 1)
        doQuickSort(records, pivotIndex + 1, right)
    }
}

fun doPartition(records: Array<PhoneRecord>, left: Int, right: Int): Int {
    val pivot = records[right]
    var partitionIndex = left
    for (i in left until right) {
        if (records[i].person < pivot.person) {
            swap(records, i, partitionIndex)
            partitionIndex++
        }
    }

    swap(records, partitionIndex, right)

    return partitionIndex
}

private fun swap(records: Array<PhoneRecord>, i: Int, y: Int) {
    val temp = records[i]
    records[i] = records[y]
    records[y] = temp
}

private fun performLinearSearch(allPhoneRecords: List<PhoneRecord>, phoneNamesToFind: List<String>): Long {
    println("Start searching (linear search)...")
    val startTime = System.currentTimeMillis()
    val totalFoundRecords: Int = doLinearSearch(allPhoneRecords, phoneNamesToFind)
    val endTime = System.currentTimeMillis()
    println("Found $totalFoundRecords / ${phoneNamesToFind.size} entries. Time taken: ${formatTime(endTime - startTime)}")
    return endTime - startTime
}

private fun doLinearSearch(allPhoneRecords: List<PhoneRecord>, namesToFind: List<String>): Int {
    var totalFoundRecords = 0
    for (name in namesToFind) {
        for (nameWithPhone in allPhoneRecords) {
            if (nameWithPhone.person == name) {
                totalFoundRecords++
                break
            }
        }
    }
    return totalFoundRecords
}

fun performJumpSearch(allPhoneRecords: List<PhoneRecord>, phoneNamesToFind: List<String>, linearSearchTime: Long) {
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
            if (sortedRecords[i].person > sortedRecords[y].person) {
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
        for (nameToFind in phoneNamesToFind) {
            var previousBlockStartIndex: Int
            var nextBlockStartIndex = 0
            var isNameFound = false
            for (i in 0 until sortedRecords.size step jumpSize) {
                previousBlockStartIndex = nextBlockStartIndex
                nextBlockStartIndex = max(i, sortedRecords.size - 1)
                if (!isNameFound && sortedRecords[nextBlockStartIndex].person >= nameToFind) {
                    for (y in previousBlockStartIndex..nextBlockStartIndex) {
                        if (sortedRecords[y].person == nameToFind){
                            isNameFound = true
                            totalFoundRecords++
                            break
                        }
                    }
                }
            }
        }
        val searchEndTime = System.currentTimeMillis()
        println("Found $totalFoundRecords / ${phoneNamesToFind.size} entries. Time taken: ${formatTime(searchEndTime - startTime)}")
        println("Sorting time: ${formatTime(sortEndTime - startTime)}")
        println("Searching time: ${formatTime(searchEndTime - sortEndTime)}")
    } else {
        totalFoundRecords = doLinearSearch(allPhoneRecords, phoneNamesToFind)
        val searchEndTime = System.currentTimeMillis()
        println("Found $totalFoundRecords / ${phoneNamesToFind.size} entries. Time taken: ${formatTime(searchEndTime - startTime)}")
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

private fun getAllPhoneRecords(): List<PhoneRecord> {
    val allPhoneRecords = mutableListOf<PhoneRecord>()
    File("D:\\directory.txt").forEachLine {
        val firstSpaceIndex = it.indexOf(" ")
        val record = PhoneRecord()
        record.phone = it.substring(0, firstSpaceIndex)
        record.person = it.substring(firstSpaceIndex + 1)
        allPhoneRecords.add(record)
    }
    return allPhoneRecords
}

class PhoneRecord {
    var phone: String = ""
    var person: String = ""
    override fun toString(): String {
        return "$phone $person"
    }
}