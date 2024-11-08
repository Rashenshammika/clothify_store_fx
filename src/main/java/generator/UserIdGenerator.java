package generator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.stream.Stream;

public class UserIdGenerator implements IdentifierGenerator {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
        String prefix = "U";
        String query = "SELECT e.id FROM UserEntity e ORDER BY e.id DESC";
        Stream<String> ids = session.createQuery(query, String.class).stream();

        Integer max = ids.map(id -> id.replace(prefix, ""))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);

        return prefix + String.format("%03d", max + 1);
    }
}
