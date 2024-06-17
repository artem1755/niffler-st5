package guru.qa.niffler.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.apache.kafka.common.protocol.types.Field;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {
    private final SelenideElement deleteSpendingBtn = $(".spendings__bulk-actions button");
    private final ElementsCollection tableWithSpending = $(".spendings-table tbody")
            .$$("tr");
    
    public SelenideElement searchSpendingRowByText(String text) {
        return tableWithSpending.find(text(text))
                .scrollIntoView(true);


    }

    public MainPage chooseFirstSpending(SelenideElement spendingRow) {
        spendingRow.$$("td").first().scrollTo().click();
        return this;
    }

    public MainPage clickDeleteSpendingBtn() {
        deleteSpendingBtn.click();
        return this;
    }

    public void checkSpendingTable(int size) {
        tableWithSpending.shouldHave(size(size));
    }

}
