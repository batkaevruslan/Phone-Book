fun solution(first: List<Int>, second: List<Int>): MutableList<Int> {
    val mutable = first.toMutableList()
    mutable.addAll(second)
    return mutable
}