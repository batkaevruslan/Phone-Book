fun parseCardNumber(cardNumber: String): Long {
    if (cardNumber.count { c -> c == ' ' } != 3) {
        throw Exception("Wrong spaces count")
    }
    val cardNumberWithoutSpaces = cardNumber.replace(" ", "")
    return cardNumberWithoutSpaces.toLong()
}