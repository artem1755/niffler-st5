package guru.qa.niffler.data.entity;

import guru.qa.niffler.model.CategoryJson;
import lombok.Data;

import java.util.UUID;


@Data
public class CategoryEntity {
    private UUID id;
    private String category;
    private String username;

    public static CategoryEntity fromJson(CategoryJson categoryJson) {
        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setId(categoryJson.id());
        categoryEntity.setCategory(categoryJson.category());
        categoryEntity.setUsername(categoryJson.username());

        return categoryEntity;
    }
}
