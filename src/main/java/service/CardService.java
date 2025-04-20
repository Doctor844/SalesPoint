package service;

import model.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @CacheEvict(value = "cards", allEntries = true) // очищаем кэш при сохранении
    public Card save(Card card) {
        return cardRepository.save(card);
    }

    @Cacheable(value = "cards", key = "#root.args[0]", unless = "#root.args[0] == null") // кэшируем по id
    public Optional<Card> findById(Long id) {
        System.out.println(" Данные из БД, id = " + id);
        return cardRepository.findById(id);
    }

    @Transactional
    @CacheEvict(value = "cards", key = "#id") // очищаем кэш при удалении
    public void deleteById(Long id) {
        cardRepository.deleteById(id);
    }

    @Cacheable(value = "cardsAll") // кэшируем весь список (осторожно с обновлением)
    public List<Card> findAll() {
        System.out.println("Получаем список из БД");
        return cardRepository.findAll();
    }

    @Transactional
    @CacheEvict(value = { "cards", "cardsAll" }, allEntries = true)
    public void truncateTable() {
        String sql = "TRUNCATE TABLE card CASCADE";
        jdbcTemplate.execute(sql);
    }

    @Transactional
    @CacheEvict(value = { "cards", "cardsAll" }, allEntries = true)
    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS card CASCADE";
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
