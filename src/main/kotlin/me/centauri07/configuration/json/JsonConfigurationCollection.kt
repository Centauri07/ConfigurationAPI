package me.centauri07.configuration.json

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import me.centauri07.configuration.ConfigurationCollection
import java.io.File
import java.io.FileReader
import java.io.FileWriter

data class Model<T> (var model: MutableList<T>)

open class JsonConfigurationCollection<T>: ConfigurationCollection<T> {
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

    private val type = object : TypeToken<Model<T>>() {}.type

    private val file: File

    private lateinit var configuration: Model<T>

    override fun create() {
        configuration = Model(mutableListOf())
        save()
    }

    override fun load() {
        val reader = FileReader(file)
        configuration = gson.fromJson(reader, type)
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

    override fun find(): List<T> = configuration.model

    override fun find(key: String, value: Any): List<T> {
        val elements = mutableListOf<T>()

        configuration.model.forEach { type ->
            type!!::class.java.declaredFields.forEach { field ->
                field.isAccessible = true
                if (field.name == key && field.get(type) == value) {
                    elements.add(type)
                }
            }
        }

        return elements
    }

    override fun insert(obj: T): T {
        configuration.model.add(obj)
        save()
        return obj
    }

    override fun delete(obj: T): T {
        configuration.model.remove(obj)
        save()
        return obj
    }

    override fun replace(old: T, new: T): T {
        if (configuration.model.contains(old)) {
            configuration.model.add(configuration.model.indexOf(old), new)
            configuration.model.removeAt(configuration.model.indexOf(old) + 1)
        }
        save()
        return new
    }
}