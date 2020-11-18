import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    var ascending = true
    var n = scanner.nextInt()
    if (n > 0) {
        var previous = scanner.nextInt()
        while (--n > 0) {
            val next = scanner.nextInt()
            if (next < previous) {
                ascending = false
                break
            }
            previous = next
        }
    }
    if (ascending) {
        print("YES")
    } else {
        print("NO")
    }
}