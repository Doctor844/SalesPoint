package service;

import model.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.ResponseCodeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ResponseCodeService {

    private final ResponseCodeRepository responseCodeRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ResponseCodeService(ResponseCodeRepository responseCodeRepository, JdbcTemplate jdbcTemplate) {
        this.responseCodeRepository = responseCodeRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public ResponseCode save(ResponseCode responseCode) {
        return responseCodeRepository.save(responseCode);
    }


    public Optional<ResponseCode> findById(Long id) {
        return responseCodeRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        responseCodeRepository.deleteById(id);
    }


    public List<ResponseCode> findAll() {
       return responseCodeRepository.findAll();
    }

    @Transactional
    public void truncateTable() {
        String sql = """
                TRUNCATE TABLE response_code CASCADE
                """;
        jdbcTemplate.execute(sql);
    }

    @Transactional
    public void dropTable() {
        String sql = """
                DROP TABLE IF EXISTS response_code CASCADE
                 """;
        jdbcTemplate.execute(sql);
    }

    @Transactional
    public void createTable() {
        String sql = """
               CREATE TABLE IF NOT EXISTS response_code
               (
                   id                BIGSERIAL PRIMARY KEY,
                   error_code        VARCHAR(2),
                   error_description VARCHAR(255),
                   error_level       VARCHAR(255)
               );
                 """;
        jdbcTemplate.execute(sql);

    }

}