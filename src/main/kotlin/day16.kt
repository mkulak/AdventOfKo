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
    val packet = readPacket(BitReader(bitSet))
    return sumVersions(packet)
}

fun day16b(): ULong {
    val data = readInput("day16.txt")
    val bitSet = BitSet(data.length * 4)
    val table = (('0'..'9') + ('A'..'F')).mapIndexed { i, ch -> ch to i }.toMap()
    data.forEachIndexed { i, ch ->
        val value = table[ch]!!
        repeat(4) {
            bitSet.set(i * 4 + it, (value and (1 shl (3 - it)) != 0))
        }
    }
    val packet = readPacket(BitReader(bitSet))
    return evaluate(packet)
}

val sumTypeId: UByte = 0u
val mulTypeId: UByte = 1u
val minTypeId: UByte = 2u
val maxTypeId: UByte = 3u
val literalTypeId: UByte = 4u
val greaterTypeId: UByte = 5u
val lessTypeId: UByte = 6u
val equalTypeId: UByte = 7u

data class Packet(val version: UByte, val type: UByte, val value: ULong, val operands: List<Packet>)

fun readPacket(reader: BitReader): Packet {
    val version = reader.readInt(3u).toUByte()
    val typeId = reader.readInt(3u).toUByte()
    return if (typeId == literalTypeId) {
        var value = 0.toULong()
        do {
            val next = reader.readBit()
            val chunk = reader.readInt(4u)
            value = (value shl 4) + chunk
        } while (next)
        Packet(version, typeId, value, emptyList())
    } else {
        val lengthTypeId = reader.readBit()
        val subPackets = if (lengthTypeId) {
            val packetsCount = reader.readInt(11u)
            List(packetsCount.toInt()) { readPacket(reader) }
        } else {
            val packetsSize = reader.readInt(15u).toInt()
            val initial = reader.offset
            generateSequence { if (reader.offset < initial + packetsSize) readPacket(reader) else null }.toList()
        }
        Packet(version, typeId, 0.toULong(), subPackets)
    }
}

class BitReader(val bitSet: BitSet) {
    var offset = 0

    fun readBit(): Boolean = bitSet[offset++]

    fun readInt(bitsCount: UInt): UInt =
        (0u until bitsCount).fold(0u) { acc, _ -> (acc shl 1) + (if (readBit()) 1u else 0u) }
}

fun sumVersions(packet: Packet): UInt = packet.version + packet.operands.sumOf(::sumVersions)

fun evaluate(packet: Packet): ULong =
        when (packet.type) {
            sumTypeId -> packet.operands.sumOf(::evaluate)
            mulTypeId -> packet.operands.fold(1.toULong()) { acc, p -> acc * evaluate(p)}
            minTypeId -> packet.operands.map(::evaluate).minOrNull()!!
            maxTypeId -> packet.operands.map(::evaluate).maxOrNull()!!
            literalTypeId -> packet.value
            greaterTypeId -> if (evaluate(packet.operands[0]) > evaluate(packet.operands[1])) 1u else 0u
            lessTypeId -> if (evaluate(packet.operands[0]) < evaluate(packet.operands[1])) 1u else 0u
            equalTypeId -> if (evaluate(packet.operands[0]) == evaluate(packet.operands[1])) 1u else 0u
            else -> unreachable()
        }

fun main() {
    println(day16b())
}
