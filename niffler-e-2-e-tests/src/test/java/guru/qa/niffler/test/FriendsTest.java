package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.pages.AuthPage;
import guru.qa.niffler.pages.LoginPage;
import guru.qa.niffler.pages.MainPage;
import guru.qa.niffler.pages.PeoplePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static guru.qa.niffler.jupiter.annotation.User.Selector.*;


@WebTest
public class FriendsTest {
    LoginPage loginPage = new LoginPage();
    AuthPage authPage = new AuthPage();
    MainPage mainPage = new MainPage();
    PeoplePage peoplePage = new PeoplePage();

    static {
        Configuration.browserSize = "1920x1080";
        Configuration.browser = "chrome";
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--incognito");
//        chromeOptions.addArguments("force-device-scale-factor=0.6");
//        chromeOptions.addArguments("high-dpi-support=0.6");
        Configuration.browserCapabilities = chromeOptions;

    }

    @Test
    void checkFriendInvitationSent(@User(selector = INVITATION_SEND) UserJson testUser) {
        authPage.openPage().clickLoginBtn();
        loginPage.setUsername(testUser.username())
                .setPassword(testUser.testData().password())
                .clickSubmit();

        mainPage.clickPeopleBtn();
        peoplePage.clickAddFriendBtn("barsik");
        peoplePage.checkInvitationSent("barsik");
    }


    @Test
    void checkFriendInvitationReceived(@User(selector = INVITATION_RECEIVED) UserJson testUser) {
        authPage.openPage().clickLoginBtn();
        loginPage.setUsername(testUser.username())
                .setPassword(testUser.testData().password())
                .clickSubmit();

        mainPage.clickPeopleBtn();
        peoplePage.checkInvitationReceived("duck");
    }

    @Test
    void checkListOfFreiends(@User(selector = WITH_FRIENDS) UserJson testUser) {
        authPage.openPage().clickLoginBtn();
        loginPage.setUsername(testUser.username())
                .setPassword(testUser.testData().password())
                .clickSubmit();

        mainPage.clickPeopleBtn();
        peoplePage.checkFriendShip();
    }


    @Test
    void testCheckFriendsAndInvitationReceived(@User(selector = WITH_FRIENDS) UserJson firstTestUser,
                                               @User(selector = INVITATION_SEND) UserJson secondTestUser) {
        authPage.openPage().clickLoginBtn();
        loginPage.setUsername(firstTestUser.username())
                .setPassword(firstTestUser.testData().password())
                .clickSubmit();

        mainPage.clickPeopleBtn();
        peoplePage.clickAddFriendBtn(secondTestUser.username());
        peoplePage.checkInvitationSent(secondTestUser.username());
    }


}
