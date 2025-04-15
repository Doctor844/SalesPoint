package service;

import model.AcquiringBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.AcquiringBankRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AcquiringBankService {

    private final AcquiringBankRepository acquiringBankRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AcquiringBankService(AcquiringBankRepository acquiringBankRepository, JdbcTemplate jdbcTemplate) {
        this.acquiringBankRepository = acquiringBankRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void save(AcquiringBank acquiringBank) {
        acquiringBankRepository.save(acquiringBank);
    }


    public Optional<AcquiringBank> findById(Long id) {
        return acquiringBankRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        acquiringBankRepository.deleteById(id);
    }


    public List<AcquiringBank> findAll() {
        return acquiringBankRepository.findAll();
    }

    @Transactional
    public void truncateTable() {
        String sql = """
                TRUNCATE TABLE acquiring_bank CASCADE""";
        jdbcTemplate.execute(sql);
    }

    @Transactional
    public void dropTable() {
        String sql = """
                DROP TABLE IF EXISTS acquiring_bank CASCADE""";
        jdbcTemplate.execute(sql);
    }

    @Transactional
    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS acquiring_bank
                (
                    id               BIGSERIAL PRIMARY KEY,
                    bic              VARCHAR(9),
                    abbreviated_name VARCHAR(255)
                );
                     """;
        jdbcTemplate.execute(sql);

    }
}
