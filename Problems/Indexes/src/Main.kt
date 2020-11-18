fun solution(products: List<String>, product: String) {
    for ((index, productInList) in products.withIndex()) {
        if (productInList == product) {
            print("$index ")
        }
    }
}