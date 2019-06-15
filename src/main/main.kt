package main

import java.io.File
import kotlin.system.measureTimeMillis

fun main(){
    println("Running...")

    // Create a file with random numbers
    // NumberGenerator().writeNumber(true)

    val file = File("result.txt")

    var elapsedTime = measureTimeMillis {
        val sum = Calculator().findSum()
        file.writeText("Sum: $sum\n")
    }
    file.appendText("Elapsed: $elapsedTime\n\n")


    elapsedTime = measureTimeMillis {
        val sum = CalculatorMultithread().findSum()
        file.appendText("Sum using threads: $sum\n")
    }
    file.appendText("Elapsed: $elapsedTime")
}
