package dsl

import java.util.Properties

object ConfigurationProperty {
    private val properties: Properties = Properties()

    init {
        javaClass.classLoader.getResourceAsStream("application.properties").use {
            properties.load(it)
        }
    }

    operator fun get(key: Any): String? {
        return properties[key] as String?
    }

}
