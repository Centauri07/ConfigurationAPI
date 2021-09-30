package me.centauri07.configuration.extensions

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

val json = Json { prettyPrint = true }

inline fun <reified T> File.readJson() = json.decodeFromString<T>(this.readText())
inline fun <reified T> File.writeJson(element: T) = this.writeText(json.encodeToString(element))