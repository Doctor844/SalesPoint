package configuration;


import model.Card;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.CardService;

import java.sql.Date;

public class Application {
    public static void main(String[] args) throws Exception {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        CardService cardService = context.getBean(CardService.class);

        // Создание новой карты
        Card newCard = new Card();
        newCard.setCardNumber("1234567890123456");
        newCard.setExpirationDate(Date.valueOf("2025-12-31").toLocalDate());
        newCard.setHolderName("IVAN IVANOV");
        newCard.setPaymentSystem(null);

        Card saved = cardService.save(newCard);
        System.out.println("✅ Сохранили карту с ID: " + saved.getId());

        System.out.println("----- Первый вызов (из БД) -----");
        cardService.findById(saved.getId()); // должен вывести "⚡ Данные из БД..."

        System.out.println("----- Второй вызов (из Redis) -----");
        cardService.findById(saved.getId()); // ничего не выведет в консоль — из кэша

        context.close();
    }
}