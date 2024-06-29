package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.annotation.meta.WebTestHttp;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.pages.AuthPage;
import guru.qa.niffler.pages.LoginPage;
import guru.qa.niffler.pages.MainPage;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.ByteArrayInputStream;
import java.util.Objects;


@WebTestHttp
public class SpendingTestHttp {
    MainPage mainPage = new MainPage();
    LoginPage loginPage = new LoginPage();
    AuthPage authPage = new AuthPage();

    static {
        Configuration.browserSize = "1920x1080";
        Configuration.browser = "chrome";
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--incognito");
//        chromeOptions.addArguments("force-device-scale-factor=0.6");
//        chromeOptions.addArguments("high-dpi-support=0.6");
        Configuration.browserCapabilities = chromeOptions;

    }

    @BeforeEach
    void doLogin() {
        // createSpend
        authPage.openPage().clickLoginBtn();
        loginPage.setUsername("dima")
                .setPassword("12345")
                .clickSubmit();
    }


    @AfterEach
    void doScreenshot() {
        Allure.addAttachment(
                "Screen on test end",
                new ByteArrayInputStream(
                        Objects.requireNonNull(
                                Selenide.screenshot(OutputType.BYTES)
                        )
                )
        );
    }

    @GenerateCategory(
            category = "Обучение",
            username = "dima"
    )
    @GenerateSpend(
            category = "Обучение",
            description = "QA.GURU Advanced 5",
            amount = 65000.00,
            currency = CurrencyValues.RUB,
            username = "dima"
    )
    @Test
    void spendingShouldBeDeletedAfterTableAction(SpendJson spendJson) {
        SelenideElement searchingRow = mainPage.searchSpendingRowByText(spendJson.description());

        mainPage.chooseFirstSpending(searchingRow)
                .clickDeleteSpendingBtn()
                .checkSpendingTable(0);
    }

}
