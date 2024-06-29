package guru.qa.niffler.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import lombok.Data;

import java.util.Date;
import java.util.UUID;


@Data
public class SpendEntity {

    private UUID id;
    private Date spendDate;
    private String category;
    private CurrencyValues currency;
    private Double amount;
    private String description;
    private String username;


    public static SpendEntity fromJson(SpendJson spendJson) {
        SpendEntity spendEntity = new SpendEntity();

        spendEntity.setId(spendJson.id());
        spendEntity.setSpendDate(spendJson.spendDate());
        spendEntity.setCategory(spendJson.category());
        spendEntity.setCurrency(spendJson.currency());
        spendEntity.setAmount(spendJson.amount());
        spendEntity.setDescription(spendJson.description());
        spendEntity.setUsername(spendJson.username());


        return spendEntity;
    }

}
