package me.centauri07.configuration

interface ConfigurationFile<E> {
    fun create()
    fun load()
    fun save()
    fun createOrLoad()
}