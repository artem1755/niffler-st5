package guru.qa.niffler.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class PeoplePage {
    private final SelenideElement peopleTable = $(".abstract-table tbody");
    private final ElementsCollection rows = $$(".abstract-table tbody tr");

    public void checkInvitationSent(String name) {
        searchRequiredRow(name).$$("td").last().$("div").shouldHave(text("Pending invitation"));
    }

    public void checkInvitationReceived(String name) {
        searchRequiredRow(name).$$("td").last().$(".abstract-table__buttons div").shouldHave(attribute("data-tooltip-id", "submit-invitation"));
    }

    public SelenideElement searchRequiredRow(String user) {
        return rows.find(text(user));
    }

    public void clickAddFriendBtn(String name) {
        searchRequiredRow(name).$$("td").last().$(".button-icon_type_add").click();
    }

    public void checkFriendShip() {
        rows.find(text("You are friends")).shouldBe(exist);
    }
}
