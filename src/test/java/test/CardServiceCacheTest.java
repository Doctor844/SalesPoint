package test;


import configuration.RedisConfig;
import org.junit.jupiter.api.*;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import redis.embedded.RedisServer;
import repository.CardRepository;
import service.CardService;

import static org.mockito.Mockito.*;

public class CardServiceCacheTest {

    private static RedisServer redisServer;

    private AnnotationConfigApplicationContext context;
    private CardService cardService;
    private CardRepository cardRepository;

    @BeforeAll
    public static void startRedis() {
        redisServer = new RedisServer(6379);
        redisServer.start();
    }

    @AfterAll
    public static void stopRedis() {
        redisServer.stop();
    }

    @BeforeEach
    public void setup() {
        context = new AnnotationConfigApplicationContext(TestConfig.class);
        cardService = context.getBean(CardService.class);
        cardRepository = context.getBean(CardRepository.class);
    }

    @AfterEach
    public void tearDown() {
        context.close();
    }

    @Test
    public void testFindByIdIsCached() {
      /*  Long id = 1L;
        Card card = Card.builder()
                .id(id)
                .cardNumber("1234567898765432")
                .expirationDate(LocalDate.of(2026, 8, 31))
                .holderName("Иван Иванов")
                .paymentSystem(null)
                .build();

        when(cardRepository.findById(id.longValue())).thenReturn(Optional.of(card));

        // Первый вызов – должен пойти в репозиторий
        Optional<Card> first = cardService.findById(id);
        assertEquals("1234567898765432", first.get().getCardNumber());

        // Второй вызов – должен взять из кеша
        Optional<Card> second = cardService.findById(id);
        assertEquals("1234567898765432", second.get().getCardNumber());

        // Проверяем, что репозиторий вызвался только один раз
        verify(cardRepository, times(1)).findById(id);*/
    }

    @Configuration
    @EnableCaching
    @Import(RedisConfig.class)
    static class TestConfig {

        @Bean
        public CardRepository cardRepository() {
            return mock(CardRepository.class);
        }

        @Bean
        public CardService cardService(CardRepository cardRepository) {
            return new CardService(cardRepository, mock(JdbcTemplate.class));
        }
    }
}
