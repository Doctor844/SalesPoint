package service;

import model.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.CardRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CardService(CardRepository cardRepository, JdbcTemplate jdbcTemplate) {
        this.cardRepository = cardRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public Card save(Card card) {
        return cardRepository.save(card);
    }


    public Optional<Card> findById(Long id) {
        return cardRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        cardRepository.deleteById(id);
    }


    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @Transactional
    public void truncateTable() {
        String sql = """
                TRUNCATE TABLE card CASCADE""";
        jdbcTemplate.execute(sql);
    }

    @Transactional
    public void dropTable() {
        String sql = """
                DROP TABLE IF EXISTS card CASCADE""";
        jdbcTemplate.execute(sql);
    }

    @Transactional
    public void createTable() {
        String sql = """
               CREATE TABLE IF NOT EXISTS card
               (
                   id                         BIGSERIAL PRIMARY KEY,
                   card_number                VARCHAR(50),
                   expiration_date            DATE,
                   holder_name                VARCHAR(50),
                   payment_system_id          BIGINT REFERENCES payment_system (id)
               );
                """;
        jdbcTemplate.execute(sql);
    }

}