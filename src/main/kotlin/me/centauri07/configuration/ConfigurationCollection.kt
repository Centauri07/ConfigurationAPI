package me.centauri07.configuration

interface ConfigurationCollection<E>: Configuration<E> {
    fun find(key: String, value: Any): Collection<E>
    fun insert(obj: E): E
    fun delete(obj: E): E
    fun replace(old: E, new: E): E
}