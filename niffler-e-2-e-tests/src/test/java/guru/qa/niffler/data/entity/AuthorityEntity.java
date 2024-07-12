package guru.qa.niffler.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class AuthorityEntity implements Serializable {
    private UUID id;
    private Authority authority;
    private UUID user_id;
}
