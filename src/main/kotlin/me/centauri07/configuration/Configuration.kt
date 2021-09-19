package me.centauri07.configuration

interface Configuration<E> {
    fun create()
    fun load()
    fun save()
    fun createOrLoad()
}