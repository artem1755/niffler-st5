package guru.qa.niffler.data;


import lombok.Getter;

@Getter
public enum Database {
    AUTH("jdbc:postgresql://localhost:5432/niffler-auth"),
    CURRENCY("jdbc:postgresql://localhost:5432/niffler-currency"),
    SPEND("jdbc:postgresql://localhost:5432/niffler-spend"),
    USERDATA("jdbc:postgresql://localhost:5432/niffler-userdata");

    Database(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    private final String jdbcUrl;
}
