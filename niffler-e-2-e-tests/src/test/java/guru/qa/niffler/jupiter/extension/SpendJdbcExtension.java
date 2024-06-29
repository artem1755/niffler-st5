package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.repository.SpendRepository;
import guru.qa.niffler.data.repository.SpendRepositoryJdbc;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Date;

public class SpendJdbcExtension extends AbstractSpendExtension {

    private final SpendRepository spendRepository = new SpendRepositoryJdbc();

    @Override
    public SpendJson createSpend(ExtensionContext extensionContext, GenerateSpend spend) {
        SpendEntity spendEntity = new SpendEntity();
        spendEntity.setSpendDate(new Date());
        spendEntity.setCategory(spend.category());
        spendEntity.setCurrency(spend.currency());
        spendEntity.setAmount(spend.amount());
        spendEntity.setDescription(spend.description());
        spendEntity.setUsername(spend.username());

        spendEntity = spendRepository.createSpend(spendEntity);

        return SpendJson.fromEntity(spendEntity);
    }

    @Override
    public void removeSpend(SpendJson spend) {
        SpendJson spendJson =
                new SpendJson(spend.id(),
                        spend.spendDate(),
                        spend.category(),
                        spend.currency(),
                        spend.amount(),
                        spend.description(),
                        spend.username());

        spendRepository.removeSpend(SpendEntity.fromJson(spendJson));
    }
}
