package me.centauri07.configuration.json

import com.google.gson.GsonBuilder
import me.centauri07.configuration.Configuration
import me.centauri07.configuration.ConfigurationCollection
import java.io.File
import java.io.FileReader
import java.io.FileWriter

open class JsonConfigurationCollection<E>: ConfigurationCollection<E> {
    constructor(parent: File, name: String, loadOnInit: Boolean) {
        file = File(parent, "$name.json")
        if (file.parentFile != null && !file.parentFile.exists()) file.parentFile.mkdirs()
        if (loadOnInit) this.createOrLoad()
    }

    constructor(name: String, loadOnInit: Boolean) {
        file = File("$name.json")
        if (file.parentFile != null && !file.parentFile.exists()) file.parentFile.mkdirs()
        if (loadOnInit) this.createOrLoad()
    }

    private val gson = GsonBuilder().setPrettyPrinting().serializeNulls().create()

    private val file: File
    private var configuration = mutableListOf<E>()

    override fun create() {
        configuration = mutableListOf()
        save()
    }

    override fun load() {
        val reader = FileReader(file)
        configuration = gson.fromJson(reader, configuration::class.java)
        reader.close()
    }

    override fun save() {
        val writer = FileWriter(file)
        gson.toJson(configuration, writer)
        writer.close()
    }

    override fun createOrLoad() {
        if (!file.exists()) {
            file.createNewFile()
            create()
        }
        load()
    }

    override fun find(): Collection<Configuration<E>> {
        return configuration.map { Configuration(it) }
    }

    override fun find(key: String, value: Any): Collection<Configuration<E>> {
        val elements = mutableListOf<Configuration<E>>()

        configuration.forEach { element ->
            element!!::class.java.declaredFields.forEach { field ->
                field.isAccessible = true
                if (field.name == key && field.get(element) == value) {
                    elements.add(Configuration(element))
                }
            }
        }

        return elements
    }

    override fun insert(obj: E): E {
        configuration.add(obj)
        save()
        return obj
    }

    override fun delete(obj: E): E {
        configuration.remove(obj)
        save()
        return obj
    }

    override fun replace(old: E, new: E): E {
        if (configuration.contains(old)) {
            configuration.add(configuration.indexOf(old), new)
            configuration.removeAt(configuration.indexOf(old) + 1)
        }
        return new
    }
}