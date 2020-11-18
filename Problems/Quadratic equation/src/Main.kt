import java.util.Scanner

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val a = scanner.nextDouble()
    val b = scanner.nextDouble()
    val c = scanner.nextDouble()
    val x1 = (-b - Math.sqrt(b * b - 4 * a * c)) / 2 / a
    val x2 = (-b + Math.sqrt(b * b - 4 * a * c)) / 2 / a
    if (x1 < x2) {
        print("$x1 $x2")
    } else {
        print("$x2 $x1")
    }
}