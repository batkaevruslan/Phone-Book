fun solution(strings: MutableList<String>, str: String): MutableList<String> {
    strings.replaceAll { element ->
        return@replaceAll if (element == str) {
            "Banana"
        } else {
            element
        }

    }
    return strings
}