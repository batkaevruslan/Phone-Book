import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val angle = scanner.nextDouble()
    println(Math.sin(angle) - Math.cos(angle))
}