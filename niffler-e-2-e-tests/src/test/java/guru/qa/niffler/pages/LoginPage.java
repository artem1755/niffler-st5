package guru.qa.niffler.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {


    private final SelenideElement userName = $("input[name='username']");
    private final SelenideElement password = $("input[name='password']");
    private final SelenideElement submitButton = $("button[type='submit']");

    public LoginPage setUsername(String name) {
        userName.setValue(name);
        return this;
    }

    public LoginPage setPassword(String pwd) {
        password.setValue(pwd);
        return this;
    }

    public LoginPage clickSubmit() {
        submitButton.click();
        return this;
    }

}
