package dsl

import org.openqa.selenium.WebDriver
import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.primaryConstructor


object PageFactory {

    fun <T : Any> createPage(page: KClass<T>, driver: WebDriver): T {
        val primaryConstructor = page.primaryConstructor ?: throw IllegalArgumentException()
        driver.get(retrievePageUrl(page))
        return primaryConstructor.callBy(
            retrieveParametersValues(primaryConstructor, driver)
        )
    }

    private fun <T : Any> retrieveParametersValues(
        constructor: KFunction<T>,
        driver: WebDriver
    ): Map<KParameter, Any?> {
        return constructor.parameters.map { parameter ->
            when {
                parameter.hasAnnotation<Element>() -> retrieveParameterValue(parameter, driver)
                parameter.hasAnnotation<Driver>() -> parameter to driver
                else -> throw IllegalArgumentException()
            }
        }.toMap()
    }

    private fun retrieveParameterValue(parameter: KParameter, driver: WebDriver): Pair<KParameter, Any?> {
        val element = parameter.findAnnotation<Element>() ?: throw IllegalArgumentException()
        return when {
            element.id.isNotBlank() -> parameter to driver.findById(element.id)
            element.xpath.isNotBlank() -> parameter to driver.findByXPath(element.xpath)
            element.cssSelector.isNotBlank() -> parameter to driver.findByCssSelector(element.cssSelector)
            element.name.isNotBlank() -> parameter to driver.findByName(element.name)
            element.className.isNotBlank() -> parameter to driver.findByClassName(element.className)
            element.linkText.isNotBlank() -> parameter to driver.findByLinkText(element.linkText)
            element.tagName.isNotBlank() -> parameter to driver.findByTagName(element.tagName)
            element.partialLinkText.isNotBlank() -> parameter to driver.findByPartialLinkText(element.partialLinkText)
            else -> throw IllegalArgumentException()
        }
    }

    private fun <T : Any> retrievePageUrl(page: KClass<T>): String {
        val pageAnnotation: Page = page.findAnnotation() ?: throw IllegalArgumentException()
        return when {
            pageAnnotation.url.isNotBlank() -> pageAnnotation.url
            pageAnnotation.path.isNotBlank() -> "${ConfigurationProperty["baseURL"]}/${pageAnnotation.path}"
            else -> throw IllegalArgumentException()
        }
    }

    private inline fun <reified T : Annotation> KAnnotatedElement.hasAnnotation(): Boolean =
        findAnnotation<T>() != null

}
