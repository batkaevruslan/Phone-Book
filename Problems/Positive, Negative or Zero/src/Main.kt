import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val d = scanner.nextInt()
    if (d == 0) {
        println("zero")
    } else if (d > 0) {
        println("positive")
    } else {
        print("negative")
    }
}