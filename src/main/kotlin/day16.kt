import java.util.BitSet

fun day16a(): UInt {
    val data = readInput("day16.txt")
    val bitSet = BitSet(data.length * 4)
    val table = (('0'..'9') + ('A'..'F')).mapIndexed { i, ch -> ch to i }.toMap()
    data.forEachIndexed { i, ch ->
        val value = table[ch]!!
        repeat(4) {
            bitSet.set(i * 4 + it, (value and (1 shl (3 - it)) != 0))
        }
    }
//    data.forEachIndexed { i, ch ->
//        val bits = table[ch]?.toString(2)
//        val fromSet = (0..3).map { if (bitSet[i * 4 + it]) '1' else '0' }.joinToString("")
//        println("$ch $bits $fromSet")
//    }
    val packet = readPacket(BitReader(bitSet))
    println(packet)
    return sumVersions(packet)
}

val literalTypeId: UByte = 4u

fun readPacket(bitReader: BitReader): Packet {
    val version = bitReader.readInt(3u).toUByte()
    val typeId = bitReader.readInt(3u).toUByte()
    return if (typeId == literalTypeId) {
        var value = 0.toULong()
        do {
            val next = bitReader.readBit()
            val chunk = bitReader.readInt(4u)
            value = (value shl 4) + chunk
        } while (next)
        LiteralPacket(version, value).also { println(it) }
    } else {
        val lengthTypeId = bitReader.readBit()
        val subPackets = if (lengthTypeId) {
            val packetsCount = bitReader.readInt(11u)
            println("operator with packetsCount: $packetsCount")
            List(packetsCount.toInt()) { readPacket(bitReader) }
        } else {
            val packetsSize = bitReader.readInt(15u).toInt()
            println("operator with packetsSize: $packetsSize")
            val initialOffset = bitReader.offset
            generateSequence {
//                println("bitReader.offset: ${bitReader.offset}")
                if (bitReader.offset < initialOffset + packetsSize) {
                    readPacket(bitReader)
                } else null
            }.toList()
        }
        OperatorPacket(version, typeId, subPackets).also { println(it) }
    }
}

fun sumVersions(packet: Packet): UInt =
    packet.version + when (packet) {
        is LiteralPacket -> 0u
        is OperatorPacket -> packet.operands.sumOf(::sumVersions)
    }

class BitReader(val bitSet: BitSet) {
    var offset = 0

    fun readBit(): Boolean = bitSet[offset++]

    fun readInt(bitsCount: UInt): UInt {
        var res = 0.toUInt()
        (0u until bitsCount).forEach {
            val b = if (bitSet[offset + it.toInt()]) 1 else 0
            res = (res shl 1) + b.toUInt()
        }
        offset += bitsCount.toInt()
        return res
    }
}

sealed class Packet {
    abstract val version: UByte
}

data class LiteralPacket(override val version: UByte, val value: ULong) : Packet()

data class OperatorPacket(override val version: UByte, val operatorType: UByte, val operands: List<Packet>) : Packet()

fun main() {
    println(day16a())
}
