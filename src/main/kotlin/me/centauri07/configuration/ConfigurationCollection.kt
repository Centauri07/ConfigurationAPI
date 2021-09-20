package me.centauri07.configuration

interface ConfigurationCollection<E>: ConfigurationFile<E> {
    fun find(): Collection<Configuration<E>>
    fun find(key: String, value: Any): Collection<Configuration<E>>
    fun insert(obj: E): E
    fun delete(obj: E): E
    fun replace(old: E, new: E): E
}