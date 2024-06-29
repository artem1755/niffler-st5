package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.repository.SpendRepository;
import guru.qa.niffler.data.repository.SpendRepositoryJdbc;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.model.CategoryJson;

public class CategoryJdbcExtension extends AbstractCategoryExtension {

    private final SpendRepository spendRepository = new SpendRepositoryJdbc();

    @Override
    public CategoryJson createCategory(GenerateCategory category) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategory(category.category());
        categoryEntity.setUsername(category.username());

        categoryEntity = spendRepository.createCategory(categoryEntity);

        return CategoryJson.fromEntity(categoryEntity);
    }

    @Override
    public void removeCategory(CategoryJson category) {
        CategoryJson categoryJson = new CategoryJson(category.id(), category.category(), category.username());
        spendRepository.removeCategory(CategoryEntity.fromJson(categoryJson));
    }
}
