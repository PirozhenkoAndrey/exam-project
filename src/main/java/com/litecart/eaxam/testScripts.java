package com.litecart.eaxam;

import com.codeborne.selenide.Condition;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertTrue;

/**
 * Created by piroz on 12.06.2017.
 */
public class testScripts {
    public WebDriver driver;
    public WebDriverWait wait;



    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void testCheckMainLogo () {
        chromeBrowser();
        openMainPage();
        assertTrue($("#header > a > img").isImage());//проверяем что картинка лого корректно загрузилась
    }

    public void openMainPage() {
        open("http://litecart-2.0.1/");//открываем main page
    }

    public void chromeBrowser() {
        System.setProperty("selenide.browser", "Chrome");//используем chrome browser
    }

    @Test
    public void testSearch () {
        chromeBrowser();
        openMainPage();
        $(By.cssSelector("[type*='search']"))
                .setValue("green")
                .pressEnter();
        $(By.cssSelector("#box-product div:nth-child(2) h1)"));
        $(By.cssSelector("[title*='Green Duck']"));
    }

    @Test
    public void testNewCustomerCreation () {
        newCustomer();
        testEmailExist();
        logoutCustomerProfile();
        loginCustomerProfile();
        logoutCustomerProfile();
    }

    public void loginCustomerProfile() {
        $(By.cssSelector("#box-account-login [data-type='email']")).sendKeys(customerMail());
        $(By.cssSelector("#box-account-login [data-type='password']")).sendKeys(customerPassword());
        $(By.cssSelector("#box-account-login [value='Sign In']")).click();
    }

    public String customerMail() {
        return "some1@mail.com";
    }

    public String customerPassword() {
        return "password";
    }

    public void logoutCustomerProfile() {
        $(By.cssSelector("a[href*=logout]")).click();
    }

    public void newCustomer() {
        chromeBrowser();
        openMainPage();
        $(By.cssSelector("#box-account-login a")).click();
        $(By.cssSelector("[name=tax_id]")).sendKeys("1234567890");
        $(By.cssSelector("[name=company]")).sendKeys("Main Academy");
        $(By.cssSelector("[name=firstname]")).sendKeys("Ganadiy");
        $(By.cssSelector("[name=lastname]")).sendKeys("Miroshnichenko");
        $(By.cssSelector("[name=address1]")).sendKeys("UA");
        $(By.cssSelector("[name=address2]")).sendKeys("UA");
        $(By.cssSelector("[name=postcode]")).sendKeys("04080");
        $(By.cssSelector("[name=city]")).sendKeys("Kiev");
        $(By.cssSelector("[name*=country_code]")).click();
        $(By.cssSelector("[value*=UA]")).click();
        $(By.cssSelector("#box-create-account [data-type='email']")).sendKeys(customerMail());
        $(By.cssSelector("#box-create-account [data-type='phone']")).sendKeys("+380000000000");
        $(By.cssSelector("#box-create-account [data-type='password']")).sendKeys(customerPassword());
        $(By.cssSelector("#box-create-account [name='confirmed_password']")).sendKeys(customerPassword());
        $(By.cssSelector("#box-create-account [type='checkbox']")).click();
        $(By.cssSelector("#box-create-account button")).click();
    }

    @Test
    public void testNewCustomerEmailAddressExists () {
        newCustomer();
        testEmailExist();
        //newCustomerEmailAutoChange();
    }

    public void testEmailExist() {
        $(By.cssSelector("#notices")).shouldHave(Condition.visible);
    }

    public void newCustomerEmailAutoChange() {
        $(By.cssSelector("[name*=country_code]")).click();
        $(By.cssSelector("[value*=UA]")).click();
        $(By.cssSelector("#box-create-account [data-type='email']")).val(Math.random()+"@email.com");
        $(By.cssSelector("#box-create-account [data-type='password']")).sendKeys(customerPassword());
        $(By.cssSelector("#box-create-account [name='confirmed_password']")).sendKeys(customerPassword());
        $(By.cssSelector("#box-create-account button")).click();
    }


    @After
    public void closeBrowser () {
        driver.quit();
    }

}
