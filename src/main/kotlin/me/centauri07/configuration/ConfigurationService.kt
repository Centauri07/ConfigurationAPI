package me.centauri07.configuration

import me.centauri07.configuration.extensions.readJson
import me.centauri07.configuration.extensions.writeJson
import java.io.File

class ConfigurationService(val configurationFile: File) {
    inline fun <reified T> read(defaultValue: T? = null): T? = if (configurationFile.exists()) configurationFile.readJson<T>() else defaultValue
    inline fun <reified T> write(element: T) = configurationFile.writeJson(element)
}