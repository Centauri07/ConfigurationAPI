package me.centauri07.configuration

import kotlinx.serialization.Serializable
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class HelloTest {
    @Test
    fun test() {
        val configurationService = ConfigurationService(File("configuration.json"))
        val defaultValue = Model()
        val model = configurationService.read(defaultValue)

        model?.persons?.add(Person("Christian", 21))
        model?.let { configurationService.write(it) }
    }
}

@Serializable
data class Model(
    val persons: MutableList<Person> = mutableListOf()
)

@Serializable
data class Person(
    var name: String,
    var age: Int = 0
)