package dsl

import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import java.io.File
import kotlin.reflect.KClass

inline fun <T : Any> WebDriver.onThePage(
    page: KClass<T>,
    maximizeWindow: Boolean = true,
    block: T.() -> Unit
): T {
    if (maximizeWindow) {
        this.manage().window().maximize()
    }

    return PageFactory.createPage(
        page = page,
        driver = this
    ).also(block)
}

fun WebDriver.screenShotAs(name: String) {
    this as TakesScreenshot
    this.getScreenshotAs(OutputType.FILE)
        .copyTo(
            overwrite = true,
            target = File("${ConfigurationProperty["screenShotFolder"]}/$name")
        )
}
