package service;

import model.SalesPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.SalesPointRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SalesPointService {

    private final SalesPointRepository salesPointRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SalesPointService(SalesPointRepository salesPointRepository, JdbcTemplate jdbcTemplate) {
        this.salesPointRepository = salesPointRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public SalesPoint save(SalesPoint salesPoint) {
        return salesPointRepository.save(salesPoint);
    }


    public Optional<SalesPoint> findById(Long id) {
        return salesPointRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        salesPointRepository.deleteById(id);
    }


    public List<SalesPoint> findAll() {
        return salesPointRepository.findAll();
    }

    @Transactional
    public void truncateTable() {
        String sql = """
                TRUNCATE TABLE sales_point CASCADE
                """;
        jdbcTemplate.execute(sql);
    }

    @Transactional
    public void dropTable() {
        String sql = """
                DROP TABLE IF EXISTS sales_point CASCADE
                """;
        jdbcTemplate.execute(sql);
    }

    @Transactional
    public void createTable() {
        String sql = """
               CREATE TABLE IF NOT EXISTS sales_point
               (
                   id                BIGSERIAL PRIMARY KEY,
                   pos_name          VARCHAR(255),
                   pos_address       VARCHAR(255),
                   pos_inn           VARCHAR(12),
                   acquiring_bank_id BIGINT REFERENCES acquiring_bank (id)
               );
                """;
        jdbcTemplate.execute(sql);
    }

}