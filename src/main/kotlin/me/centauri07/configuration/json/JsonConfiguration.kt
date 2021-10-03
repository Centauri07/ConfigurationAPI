package me.centauri07.configuration.json

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import me.centauri07.configuration.ConfigurationFile
import java.io.File
import java.io.FileReader
import java.io.FileWriter

open class JsonConfiguration<E>: ConfigurationFile<E> {
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

    var configuration: JsonObject = JsonObject()
        private set

    override fun create() {
        configuration = JsonObject()
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
}