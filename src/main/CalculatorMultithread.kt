package main

import java.io.File
import java.math.BigInteger
import java.nio.channels.FileChannel
import java.io.RandomAccessFile

class CalculatorMultithread {
    fun findSum(): BigInteger {
        var sum = BigInteger.ZERO

        val threads = mutableListOf<SimpleThread>()
        for (i in 1..2) {
            val thread2 = SimpleThread()
            thread2.n = i
            threads.add(thread2)
        }

        for (thread in threads){
            thread.start()
        }
        for (thread in threads){
            thread.join()
        }
        for (thread in threads){
            sum += thread.getMySum()
        }

        return sum
    }

    fun readBuffer(file: RandomAccessFile, position: Long, size: Long): BigInteger{
        //Get direct byte buffer access using channel.map() operation
        val buffer = file.channel.map(FileChannel.MapMode.READ_ONLY, position, size)

        // the buffer now reads the file as if it were loaded in memory.
        println(buffer.capacity())  //Get the size based on content size of file

        var sum = BigInteger.ZERO
        var i = 0

        loop@while (i < buffer.limit()){
            val bytes = ArrayList<Byte>()

            for (j in 0..5){

                if (i >= buffer.limit()) {
                    break@loop
                }
                val nextByte = buffer.get()
                i += 1

                if (nextByte == 0x0a.toByte()) {
                    break
                }
                bytes.add(nextByte)
            }

            if (bytes.size > 0)
                sum += BigInteger(bytes.toByteArray())
        }

        return sum
    }
}

class SimpleThread: Thread() {
    var n = 1
    var sum = BigInteger.ZERO

    private val numberOfThreads = 2

    override fun run() {
        println("${Thread.currentThread()} has run.")

        val file = RandomAccessFile(File("test.txt"), "r")
        val size =  file.channel.size()/numberOfThreads

        sum = CalculatorMultithread().readBuffer(file,(n-1)*size, size)
        println("Sum in thread $sum")
        println("${Thread.currentThread().name} has finished.")
    }

    @Synchronized
    fun getMySum() : BigInteger{
        return this.sum
    }
}