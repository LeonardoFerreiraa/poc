package dsl

import org.openqa.selenium.By
import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebElement

fun SearchContext.findById(id: String, block: WebElement.() -> Unit = {}): WebElement =
    this.findElement<WebElement>(By.id(id))
        .also(block)

fun SearchContext.findByXPath(xpath: String, block: WebElement.() -> Unit = {}): WebElement =
    this.findElement<WebElement>(By.xpath(xpath))
        .also(block)

fun SearchContext.findByCssSelector(cssSelector: String, block: WebElement.() -> Unit = {}): WebElement =
    this.findElement<WebElement>(By.cssSelector(cssSelector))
        .also(block)

fun SearchContext.findByName(name: String, block: WebElement.() -> Unit = {}): WebElement =
    this.findElement<WebElement>(By.name(name))
        .also(block)

fun SearchContext.findByClassName(className: String, block: WebElement.() -> Unit = {}): WebElement =
    this.findElement<WebElement>(By.className(className))
        .also(block)

fun SearchContext.findByLinkText(linkText: String, block: WebElement.() -> Unit = {}): WebElement =
    this.findElement<WebElement>(By.linkText(linkText))
        .also(block)

fun SearchContext.findByTagName(tagName: String, block: WebElement.() -> Unit = {}): WebElement =
    this.findElement<WebElement>(By.tagName(tagName))
        .also(block)

fun SearchContext.findByPartialLinkText(partialLinkText: String, block: WebElement.() -> Unit = {}): WebElement =
    this.findElement<WebElement>(By.partialLinkText(partialLinkText))
        .also(block)
