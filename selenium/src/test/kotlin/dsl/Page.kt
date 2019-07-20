package dsl

@Target(AnnotationTarget.CLASS)
annotation class Page(
    val url: String = "",
    val path: String = ""
)
