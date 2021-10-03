package me.centauri07.configuration

interface ConfigurationCollection<E>: ConfigurationFile<E> {
    fun find(): List<E>
    fun find(key: String, value: Any): List<E>
    fun insert(obj: E): E
    fun delete(obj: E): E
    fun replace(old: E, new: E): E
}