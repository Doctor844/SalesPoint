package service;

import model.MerchantCategoryCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.MerchantCategoryCodeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MerchantCategoryCodeService {

    private final MerchantCategoryCodeRepository merchantCategoryCodeRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MerchantCategoryCodeService(MerchantCategoryCodeRepository merchantCategoryCodeRepository, JdbcTemplate jdbcTemplate) {
        this.merchantCategoryCodeRepository = merchantCategoryCodeRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public MerchantCategoryCode save(MerchantCategoryCode merchantCategoryCode) {
        return merchantCategoryCodeRepository.save(merchantCategoryCode);
    }


    public Optional<MerchantCategoryCode> findById(Long id) {
        return merchantCategoryCodeRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        merchantCategoryCodeRepository.deleteById(id);
    }


    public List<MerchantCategoryCode> findAll() {
        return merchantCategoryCodeRepository.findAll();
    }

    @Transactional
    public void truncateTable() {
        String sql = """
                TRUNCATE TABLE merchant_category_code CASCADE
                """;
        jdbcTemplate.execute(sql);
    }

    @Transactional
    public void dropTable() {
        String sql = """
                DROP TABLE IF EXISTS merchant_category_code CASCADE
                """;
        jdbcTemplate.execute(sql);
    }

    @Transactional
    public void createTable() {
        String sql = """
               CREATE TABLE IF NOT EXISTS merchant_category_code
               (
                   id       BIGSERIAL PRIMARY KEY,
                   mcc      varchar(4),
                   mcc_name varchar(255)
               );
                """;
        jdbcTemplate.execute(sql);
    }

}