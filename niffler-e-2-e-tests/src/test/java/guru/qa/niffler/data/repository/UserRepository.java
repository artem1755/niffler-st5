package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.entity.UserAuthEntity;
import guru.qa.niffler.data.entity.UserEntity;

public interface UserRepository {


    static UserRepository getInstance() {
        if ("sjdbc".equals(System.getProperty("repo"))) {
            return new UserRepositorySpringJdbc();
        }
        if ("hibernate".equals(System.getProperty("repo"))) {
            return new UserRepositoryHibernate();
        }
        return new UserRepositoryJdbc();
    }

    UserAuthEntity createUserInAuth(UserAuthEntity user);

    UserEntity createUserInUserData(UserEntity user);


}
