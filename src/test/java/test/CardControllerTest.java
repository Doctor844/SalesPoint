package test;


import controller.CardController;
import model.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import service.CardService;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardControllerTest {

    private CardService cardService;
    private CardController cardController;

    @BeforeEach
    void setUp() {
        cardService = mock(CardService.class);
        cardController = new CardController(cardService);
    }

    @Test
    void getAllCards_shouldReturnList() {
        when(cardService.findAll()).thenReturn(Arrays.asList(new Card(), new Card()));

        var cards = cardController.getAllCards();

        assertEquals(2, cards.size());
        verify(cardService).findAll();
    }

    @Test
    void getCardById_shouldReturnCard_whenFound() {
        Card card = new Card();
        card.setId(1L);

        when(cardService.findById(1L)).thenReturn(Optional.of(card));

        ResponseEntity<Card> response = cardController.getCardById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void getCardById_shouldReturn404_whenNotFound() {
        when(cardService.findById(2L)).thenReturn(Optional.empty());

        ResponseEntity<Card> response = cardController.getCardById(2L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void createCard_shouldReturnCreatedCard() {
        Card card = new Card();
        card.setCardNumber("9999");

        when(cardService.save(card)).thenReturn(card);

        ResponseEntity<Card> response = cardController.createCard(card);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("9999", response.getBody().getCardNumber());
    }

    @Test
    void updateCard_shouldReturnUpdatedCard() {
        Card card = new Card();
        card.setCardNumber("updated");

        when(cardService.save(any())).thenReturn(card);

        ResponseEntity<Card> response = cardController.updateCard(3L, card);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("updated", response.getBody().getCardNumber());
    }

    @Test
    void deleteCard_shouldReturn204_whenFound() {
        when(cardService.findById(4L)).thenReturn(Optional.of(new Card()));

        ResponseEntity<Void> response = cardController.deleteCard(4L);

        assertEquals(204, response.getStatusCodeValue());
        verify(cardService).deleteById(4L);
    }

    @Test
    void deleteCard_shouldReturn404_whenNotFound() {
        when(cardService.findById(4L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = cardController.deleteCard(4L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void truncateCardTable_shouldReturn200() {
        ResponseEntity<Void> response = cardController.truncateCardTable();
        assertEquals(200, response.getStatusCodeValue());
        verify(cardService).truncateTable();
    }

    @Test
    void dropCardTable_shouldReturn200() {
        ResponseEntity<Void> response = cardController.dropCardTable();
        assertEquals(200, response.getStatusCodeValue());
        verify(cardService).dropTable();
    }

    @Test
    void createCardTable_shouldReturn200() {
        ResponseEntity<Void> response = cardController.createCardTable();
        assertEquals(200, response.getStatusCodeValue());
        verify(cardService).createTable();
    }
}

