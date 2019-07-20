package selenium

import dsl.onThePage
import io.github.bonigarcia.seljup.SeleniumExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.openqa.selenium.chrome.ChromeDriver
import selenium.page.GoogleHomePage

@ExtendWith(SeleniumExtension::class)
class SmokeTest {

    @Test
    fun `access google home page`(driver: ChromeDriver) {
        driver.onThePage(GoogleHomePage::class) {
            search("teste")
        }

        driver.onThePage(GoogleHomePage::class) {
            search("etset")
        }
    }

}
