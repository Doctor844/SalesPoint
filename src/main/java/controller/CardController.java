package controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import model.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.CardService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/cards")
@Tag(name = "card-controller", description = "Управление карточками")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @Operation(summary = "Получить все карточки")
    @GetMapping
    public List<Card> getAllCards() {
        return cardService.findAll();
    }

    @Operation(summary = "Получить карточку по ID")
    @GetMapping("/{id}")
    public ResponseEntity<Card> getCardById(@PathVariable("id") Long id) {
        Optional<Card> card = cardService.findById(id);
        return card.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Создать новую карточку")
    @PostMapping
    public ResponseEntity<Card> createCard(@RequestBody Card card) {
        Card createdCard = cardService.save(card);
        return ResponseEntity.status(201).body(createdCard);
    }

    @Operation(summary = "Обновить карточку по ID")
    @PutMapping("/{id}")
    public ResponseEntity<Card> updateCard(@PathVariable("id") Long id, @RequestBody Card card) {
        card.setId(id);
        Optional<Card> updatedCard = Optional.ofNullable(cardService.save(card));
        return updatedCard.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Удалить карточку по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable("id") Long id) {
        if (cardService.findById(id).isPresent()) {
            cardService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Очистить таблицу card (TRUNCATE)")
    @PostMapping("/table/truncate")
    public ResponseEntity<Void> truncateCardTable() {
        cardService.truncateTable();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Удалить таблицу card (DROP)")
    @DeleteMapping("/table/drop")
    public ResponseEntity<Void> dropCardTable() {
        cardService.dropTable();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Создать таблицу card (CREATE)")
    @PostMapping("/table/create")
    public ResponseEntity<Void> createCardTable() {
        cardService.createTable();
        return ResponseEntity.ok().build();
    }




}
