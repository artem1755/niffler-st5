package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.Database;
import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.jdbc.DataSourceProvider;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.UUID;

import static guru.qa.niffler.data.Database.SPEND;

public class SpendRepositorySpringJdbc implements SpendRepository {

    private static final DataSource spendDataSource = DataSourceProvider.dataSource(SPEND);

    private static final JdbcTemplate jdbcTemplate = new JdbcTemplate(
            DataSourceProvider.dataSource(Database.SPEND)
    );

    @Override
    public CategoryEntity createCategory(CategoryEntity category) {

        KeyHolder kh = new GeneratedKeyHolder();

        jdbcTemplate.update(con ->
                {
                    PreparedStatement ps = con.prepareStatement("INSERT INTO category (category, username) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
                    ps.setString(1, category.getCategory());
                    ps.setString(2, category.getUsername());
                    return ps;
                }, kh

        );
        category.setId(UUID.fromString((String) kh.getKeys().get("id")));
        return category;

    }


    @Override
    public void removeCategory(CategoryEntity category) {
        jdbcTemplate.update(
                "DELETE FROM \"category\" WHERE ID = ?",
                category.getId()
        );

    }

    @Override
    public CategoryEntity editCategory(CategoryEntity category) {

        jdbcTemplate.update(
                "UPDATE \"category\" SET category = ?, username = ? WHERE ID = ?",
                category.getCategory(),
                category.getUsername(),
                category.getId()
        );
        return category;
    }

    @Override
    public SpendEntity createSpend(SpendEntity spend) {
        KeyHolder kh = new GeneratedKeyHolder();

        jdbcTemplate.update(con ->
                {
                    PreparedStatement ps = con.prepareStatement("INSERT INTO \"spend\" (username, currency, spend_date, amount, description, category_id) VALUES (?, ?, ?, ?, ?, ?)",
                            PreparedStatement.RETURN_GENERATED_KEYS);
                    ps.setString(1, spend.getUsername());
                    ps.setString(2, String.valueOf(spend.getCurrency()));
                    ps.setDate(3, new Date(spend.getSpendDate().getTime()));

                    ps.setDouble(4, spend.getAmount());
                    ps.setString(5, spend.getDescription());
                    ps.setObject(6, spend.getCategory());
                    return ps;
                }, kh

        );
        spend.setId(UUID.fromString((String) kh.getKeys().get("id")));
        return spend;

    }

    @Override
    public SpendEntity editSpend(SpendEntity spend) {
        jdbcTemplate.update(
                "UPDATE \"spend\" SET username = ?, currency = ?, spend_date = ?, amount = ?, description = ?, category_id = ? WHERE id = ?",
                spend.getUsername(),
                spend.getCurrency(),
                new Date(spend.getSpendDate().getTime()),
                spend.getAmount(),
                spend.getDescription(),
                spend.getCategory(),
                getCategoryById(spend.getCategory())
        );
        return spend;
    }

    @Override
    public void removeSpend(SpendEntity spend) {

        jdbcTemplate.update(
                "DELETE FROM \"spend\" WHERE ID = ?",
                spend.getId()
        );
    }


    private UUID getCategoryById(String categoryName) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT id FROM category WHERE category = ?")) {
            preparedStatement.setString(1, categoryName);
            preparedStatement.execute();

            try (ResultSet resultSet = preparedStatement.getResultSet()) {
                if (resultSet.next()) {
                    return UUID.fromString(resultSet.getString("id"));
                } else {
                    throw new IllegalStateException("Can't access id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int getSpendIdByCategoryId(UUID catId) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM spend WHERE category_id = ?")) {
            preparedStatement.setObject(1, catId);
            preparedStatement.execute();

            try (ResultSet resultSet = preparedStatement.getResultSet()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    throw new IllegalStateException();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
