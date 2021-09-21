package me.centauri07.configuration

open class Configuration<E>(val src: E) {
    fun get(key: String): Any? {
        for (field in src!!::class.java.declaredFields) {
            field.isAccessible = true

            if (field.name == key) {
                return field.get(src)
            }
        }

        return null
    }

    fun getString(key: String): Any = get(key) as String
    fun getInt(key: String): Any = get(key) as Int
    fun getLong(key: String): Any = get(key) as Long
    fun getDouble(key: String): Any =  get(key) as Double
    fun getBoolean(key: String): Any = get(key) as Boolean

}