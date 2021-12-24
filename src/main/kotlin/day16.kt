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
    val packet = BitReader(bitSet).readPacket()
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
    val packet = BitReader(bitSet).readPacket()
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

fun BitReader.readPacket(): Packet {
    val version = readUInt(3u).toUByte()
    val typeId = readUInt(3u).toUByte()
    val value = if (typeId == literalTypeId) readValue(0u) else 0u
    val operands = when {
        typeId == literalTypeId -> emptyList()
        readBit() -> List(readUInt(11u).toInt()) { readPacket() }
        else -> {
            val packetsSize = readUInt(15u).toInt()
            val initial = offset
            generateSequence { if (offset < initial + packetsSize) readPacket() else null }.toList()
        }
    }
    return Packet(version, typeId, value, operands)
}

tailrec fun BitReader.readValue(value: ULong = 0u): ULong =
    if (readBit()) readValue(value + readUInt(4u)) else value + readUInt(4u)

class BitReader(val bitSet: BitSet) {
    var offset = 0

    fun readBit(): Boolean = bitSet[offset++]

    fun readUInt(bitsCount: UInt): UInt =
        (0u until bitsCount).fold(0u) { acc, _ -> (acc shl 1) + (if (readBit()) 1u else 0u) }
}

fun sumVersions(packet: Packet): UInt = packet.version + packet.operands.sumOf(::sumVersions)

fun evaluate(packet: Packet): ULong =
    when (packet.type) {
        sumTypeId -> packet.operands.sumOf(::evaluate)
        mulTypeId -> packet.operands.fold(1.toULong()) { acc, p -> acc * evaluate(p) }
        minTypeId -> packet.operands.map(::evaluate).minOrNull()!!
        maxTypeId -> packet.operands.map(::evaluate).maxOrNull()!!
        literalTypeId -> packet.value
        greaterTypeId -> if (evaluate(packet.operands[0]) > evaluate(packet.operands[1])) 1u else 0u
        lessTypeId -> if (evaluate(packet.operands[0]) < evaluate(packet.operands[1])) 1u else 0u
        equalTypeId -> if (evaluate(packet.operands[0]) == evaluate(packet.operands[1])) 1u else 0u
        else -> unreachable()
    }

fun main() {
    println(day16b()) //2056021084691
}
