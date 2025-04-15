package repository;

import model.PaymentSystem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentSystemRepository extends JpaRepository<PaymentSystem, Long> {
}
