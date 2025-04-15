package service;

import model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.TransactionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, JdbcTemplate jdbcTemplate) {
        this.transactionRepository = transactionRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public Transaction save(Transaction terminal) {
        return transactionRepository.save(terminal);
    }


    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }


    public List<Transaction> findAll() {
       return transactionRepository.findAll();
    }

    @Transactional
    public void truncateTable() {
        String sql = """
                TRUNCATE TABLE transaction CASCADE
                """;
        jdbcTemplate.execute(sql);
    }

    @Transactional
    public void dropTable() {
        String sql = """
                DROP TABLE IF EXISTS transaction CASCADE
                """;
        jdbcTemplate.execute(sql);
    }

    @Transactional
    public void createTable() {
        String sql = """
                 CREATE TABLE IF NOT EXISTS transaction
                 (
                     id                         BIGSERIAL PRIMARY KEY,
                     transaction_date           DATE,
                     sum                        DECIMAL,
                     transaction_type_id        BIGINT REFERENCES transaction_type (id),
                     card_id                    BIGINT REFERENCES card (id),
                     terminal_id                BIGINT REFERENCES terminal (id),
                     response_code_id           BIGINT REFERENCES response_code (id),
                     authorization_code         VARCHAR(6)
                 );
                """;
        jdbcTemplate.execute(sql);
    }

}