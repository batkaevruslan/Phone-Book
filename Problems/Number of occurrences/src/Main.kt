fun solution(strings: List<String>, str: String): Int {
    var count = 0
    for (string in strings) {
        if (string == str) {
            count++
        }
    }
    return count
}