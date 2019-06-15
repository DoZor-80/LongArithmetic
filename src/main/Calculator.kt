package main

import java.io.File
import java.math.BigInteger

class Calculator {
    fun findSum() : BigInteger{
        val bufferedInputStream = File("test.txt").inputStream().buffered()
        val ints = ArrayList<Int>()
        var bytes = byteArrayOf()
        var sum = BigInteger.ZERO
        var k = 0

        cycle@while (true) {
            for (i in 0..5){
                val tmp = bufferedInputStream.read()
                if (tmp < 0) break@cycle
                if (tmp == 0x0a)
                    break
                ints.add(tmp)
            }
            if (ints.size > 0) {
                bytes = ints.foldIndexed(ByteArray(ints.size)) { i, a, v -> a.apply { set(i, v.toByte()) } }
                sum += BigInteger(bytes)
                k += 1
            }
            ints.clear()
        }
        bufferedInputStream.close()

        println("Count: $k")
        return sum
    }
}
