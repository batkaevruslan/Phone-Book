fun main(args: Array<String>) {
    if (args.size != 3) {
        println("Invalid number of program arguments")
    } else {
        for (i in args.indices) {
            printArgument(args[i], i)
        }
    }
}

fun printArgument(arg: String, index: Int) {
    println("Argument ${index + 1}: $arg. It has ${arg.length} characters")
}