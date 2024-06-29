package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.jdbc.DataSourceProvider;

import java.sql.*;
import java.util.UUID;
import javax.sql.DataSource;

import static guru.qa.niffler.data.Database.SPEND;

public class SpendRepositoryJdbc implements SpendRepository {

    private static final DataSource spendDataSource = DataSourceProvider.dataSource(SPEND);


    @Override
    public CategoryEntity createCategory(CategoryEntity category) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO \"category\" (category, username) VALUES (?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS
             )) {

            preparedStatement.setString(1, category.getCategory());
            preparedStatement.setString(2, category.getUsername());
            preparedStatement.executeUpdate();

            UUID generatedId;

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    generatedId = UUID.fromString(resultSet.getString("id"));
                } else {
                    throw new IllegalStateException("Can't access to id");
                }
            }

            category.setId(generatedId);
            return category;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void removeCategory(CategoryEntity category) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM \"category\" WHERE ID = ?"
             )) {
            preparedStatement.setObject(1, category.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CategoryEntity editCategory(CategoryEntity category) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE \"category\" SET category = ?, username = ? WHERE ID = ?"
             )) {
            preparedStatement.setString(1, category.getCategory());
            preparedStatement.setString(2, category.getUsername());
            preparedStatement.setObject(3, category.getId());
            preparedStatement.executeUpdate();
            return category;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SpendEntity createSpend(SpendEntity spend) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO \"spend\" (username, currency, spend_date, amount, description, category_id) VALUES (?, ?, ?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, spend.getUsername());
            preparedStatement.setString(2, String.valueOf(spend.getCurrency()));
            preparedStatement.setDate(3, new Date(spend.getSpendDate().getTime()));

            preparedStatement.setDouble(4, spend.getAmount());
            preparedStatement.setString(5, spend.getDescription());
            preparedStatement.setObject(6, getCategoryById(spend.getCategory()));

            preparedStatement.executeUpdate();

            UUID generatedId;
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    generatedId = UUID.fromString(resultSet.getString("id"));
                } else {
                    throw new IllegalStateException("Can`t access to id");
                }
            }
            spend.setId(generatedId);
            return spend;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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


    @Override
    public SpendEntity editSpend(SpendEntity spend) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE \"spend\" SET username = ?, currency = ?," +
                     " spend_date = ?, amount = ?, description = ?, category_id = ? WHERE id = ?")) {
            preparedStatement.setString(1, spend.getUsername());
            preparedStatement.setString(2, String.valueOf(spend.getCurrency()));

            Date spendDate = new Date(spend.getSpendDate().getTime());
            preparedStatement.setDate(3, spendDate);

            preparedStatement.setDouble(4, spend.getAmount());
            preparedStatement.setString(5, spend.getDescription());
            preparedStatement.setObject(6, getCategoryById(spend.getCategory()));
            preparedStatement.setObject(7, getSpendIdByCategoryId(getCategoryById(spend.getCategory())));
            preparedStatement.executeUpdate();
            return spend;
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


    public void removeSpend(SpendEntity spend) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM \"spend\" WHERE ID = ?")) {
            preparedStatement.setObject(1, spend.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
