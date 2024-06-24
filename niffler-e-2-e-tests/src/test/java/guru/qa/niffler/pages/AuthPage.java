package guru.qa.niffler.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class AuthPage {
    private final SelenideElement loginBtn = $("a[href*='redirect']");
    private final SelenideElement registerBtn = $("a[href*='register']");

    public AuthPage openPage() {
        Selenide.open("http://127.0.0.1:3000/");
        return this;
    }

    public void clickLoginBtn() {
        loginBtn.click();
    }

    public void clickRegisterBtn() {
        registerBtn.click();
    }
}
