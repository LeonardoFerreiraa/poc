package dsl

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Element(
    val id: String = "",
    val xpath: String = "",
    val cssSelector: String = "",
    val name: String = "",
    val className: String = "",
    val linkText: String = "",
    val tagName: String = "",
    val partialLinkText: String = ""
)
