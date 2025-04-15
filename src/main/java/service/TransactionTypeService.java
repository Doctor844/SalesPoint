package service;

import model.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.TransactionTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionTypeService {

    private final TransactionTypeRepository transactionTypeRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TransactionTypeService(TransactionTypeRepository transactionTypeRepository, JdbcTemplate jdbcTemplate) {
        this.transactionTypeRepository = transactionTypeRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public TransactionType save(TransactionType transactionType) {
        return transactionTypeRepository.save(transactionType);
    }


    public Optional<TransactionType> findById(Long id) {
        return transactionTypeRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        transactionTypeRepository.deleteById(id);
    }


    public List<TransactionType> findAll() {
        return transactionTypeRepository.findAll();
    }

    @Transactional
    public void truncateTable() {
        String sql = """
                TRUNCATE TABLE transaction_type CASCADE
                """;
        jdbcTemplate.execute(sql);
    }

    @Transactional
    public void dropTable() {
        String sql = """
                DROP TABLE IF EXISTS transaction_type CASCADE
                """;
        jdbcTemplate.execute(sql);
    }

    @Transactional
    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS transaction_type
                (
                    id                    BIGSERIAL PRIMARY KEY,
                    transaction_type_name VARCHAR(255),
                    operator              VARCHAR(1)
                );
                 """;
        jdbcTemplate.execute(sql);
    }

}