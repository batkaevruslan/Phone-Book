package phonebook

import kotlin.math.abs

class HashTable(private val size: Int) {
    private val items: Array<TableEntry?> = Array(size, init = { _ -> null})

    private fun getHashCode(key: String): Int {
        var hashValue = 0;
        for (index in key.indices) {
            hashValue = (hashValue * 31 + key[index].toInt())
        }
        return hashValue
    }

    private fun findElementIndex(key: String): Int {
        val hashCode = getHashCode(key) and 0x7FFFFFFF
        var hash = hashCode % size

        var i = 1
        while (items[hash] != null && items[hash]!!.key() != key) {
            hash = (hash + 1) % size

            if (hash == hashCode % size) {
                return -1
            }
        }
        return hash
    }

    fun put(key:String, value: String) {
        val index = findElementIndex(key)
        items[index] = TableEntry(key, value)
    }

    fun get(key:String): String? {
        val index = findElementIndex(key)
        if (index == -1) {
            return null
        }
        return items[index]!!.value()
    }

    class TableEntry(private val key: String, private val value: String) {
        fun key(): String {
            return key
        }

        fun value(): String {
            return value
        }
    }
}