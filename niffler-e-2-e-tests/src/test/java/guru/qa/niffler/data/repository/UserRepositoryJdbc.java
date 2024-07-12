package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.UserAuthEntity;
import guru.qa.niffler.data.entity.UserEntity;
import guru.qa.niffler.data.jdbc.DataSourceProvider;

import javax.sql.DataSource;

import static guru.qa.niffler.data.Database.*;

public class UserRepositoryJdbc implements UserRepository {

    private static final DataSource authDataSource = DataSourceProvider.dataSource(AUTH);
    private static final DataSource udDataSource = DataSourceProvider.dataSource(USERDATA);

    @Override
    public UserAuthEntity createUserInAuth(UserAuthEntity user) {
        return null;
    }

    @Override
    public UserEntity createUserInUserData(UserEntity user) {
        return null;
    }
}
