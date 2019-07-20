package selenium.page

import dsl.Driver
import dsl.Page
import dsl.findByName
import dsl.screenShotAs
import org.junit.jupiter.api.Assertions
import org.openqa.selenium.WebDriver

@Page(url = "https://www.google.com/")
class GoogleHomePage(

    @Driver
    private val driver: WebDriver

) {

    init {
        Assertions.assertEquals("Google", driver.title)
    }

    fun search(text: String) {
        driver.findByName("q") {
            sendKeys(text)
            submit()
        }

        Assertions.assertEquals("$text - Pesquisa Google", driver.title)
        driver.screenShotAs("search - $text")
    }

}
