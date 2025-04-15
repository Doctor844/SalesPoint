package service;

import model.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.TerminalRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TerminalService {

    private final TerminalRepository terminalRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TerminalService(TerminalRepository terminalRepository, JdbcTemplate jdbcTemplate) {
        this.terminalRepository = terminalRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public Terminal save(Terminal terminal) {
        return terminalRepository.save(terminal);
    }


    public Optional<Terminal> findById(Long id) {
        return terminalRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        terminalRepository.deleteById(id);
    }


    public List<Terminal> findAll() {
       return terminalRepository.findAll();
    }

    @Transactional
    public void truncateTable() {
        String sql = """
                TRUNCATE TABLE terminal CASCADE
                """;
        jdbcTemplate.execute(sql);
    }

    @Transactional
    public void dropTable() {
        String sql = """
                DROP TABLE IF EXISTS terminal CASCADE
                """;
        jdbcTemplate.execute(sql);
    }

    @Transactional
    public void createTable() {
        String sql = """
             CREATE TABLE IF NOT EXISTS terminal
             (
                 id          BIGSERIAL PRIMARY KEY,
                 terminal_id VARCHAR(9),
                 mcc_id      INTEGER REFERENCES merchant_category_code (id),
                 pos_id      BIGINT REFERENCES sales_point (id)
             );
                """;
        jdbcTemplate.execute(sql);
    }

}