import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val a = scanner.nextInt()
    val b = scanner.nextInt()

    if (b == 0) {
        println("Division by zero, please fix the second argument!")
    } else {
        println(a / b)
    }
}