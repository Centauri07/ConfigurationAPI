package me.centauri07.configuration

import me.centauri07.configuration.json.JsonConfigurationCollection
import kotlin.test.Test

class HelloTest {
    @Test
    fun test() {
        val configuration = JsonConfigurationCollection<Person>("persons", true)
        val person = Person()
        person.name = "John"
        person.age = 14

        configuration.insert(person)

        configuration.find("age", 14).forEach {
            println(it::class.java.name)
        }
    }
}

class Person {
    lateinit var name: String
    var age: Int = 0
}