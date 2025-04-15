package service;

import model.PaymentSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.PaymentSystemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentSystemService {

    private final PaymentSystemRepository paymentSystemRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PaymentSystemService(PaymentSystemRepository paymentSystemRepository, JdbcTemplate jdbcTemplate) {
        this.paymentSystemRepository = paymentSystemRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public PaymentSystem save(PaymentSystem paymentSystem) {
        return paymentSystemRepository.save(paymentSystem);
    }


    public Optional<PaymentSystem> findById(Long id) {
        return paymentSystemRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        paymentSystemRepository.deleteById(id);
    }


    public List<PaymentSystem> findAll() {
       return paymentSystemRepository.findAll();
    }

    @Transactional
    public void truncateTable() {
        String sql = """
                TRUNCATE TABLE payment_system CASCADE
                """;
        jdbcTemplate.execute(sql);
    }

    @Transactional
    public void dropTable() {
        String sql = """
                DROP TABLE IF EXISTS payment_system CASCADE
                """;
        jdbcTemplate.execute(sql);
    }

    @Transactional
    public void createTable() {
        String sql = """
               CREATE TABLE IF NOT EXISTS payment_system
               (
                   id                  BIGSERIAL PRIMARY KEY,
                   payment_system_name VARCHAR(50)
               );             
                """;
        jdbcTemplate.execute(sql);
    }

}