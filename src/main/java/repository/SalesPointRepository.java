package repository;

import model.SalesPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesPointRepository extends JpaRepository<SalesPoint, Long> {
}
