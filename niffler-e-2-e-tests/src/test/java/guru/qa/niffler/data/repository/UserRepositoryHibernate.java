package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.UserAuthEntity;
import guru.qa.niffler.data.entity.UserEntity;

public class UserRepositoryHibernate implements UserRepository {
    @Override
    public UserAuthEntity createUserInAuth(UserAuthEntity user) {
        return null;
    }

    @Override
    public UserEntity createUserInUserData(UserEntity user) {
        return null;
    }
}
