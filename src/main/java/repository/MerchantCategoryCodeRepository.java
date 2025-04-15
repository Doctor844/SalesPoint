package repository;

import model.MerchantCategoryCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantCategoryCodeRepository extends JpaRepository<MerchantCategoryCode, Long> {
}
