package model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Table(name = "transaction")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Schema(description = "Транзакция")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "Уникальный идентификатор транзакции", example = "1")
    private Long id;

    @Column(name = "transaction_date")
    @Schema(description = "Дата транзакции", example = "2025-04-11")
    private LocalDate transactionDate;

    @Column(name = "sum")
    @Schema(description = "Сумма транзакции", example = "1500.75")
    private BigDecimal sum;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "transaction_type_id")
    @Schema(description = "Тип транзакции")
    private TransactionType transactionType;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "card_id")
    @Schema(description = "Карта, использованная в транзакции")
    private Card card;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "terminal_id")
    @Schema(description = "Терминал, с которым была совершена транзакция")
    private Terminal terminal;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "response_code_id")
    @Schema(description = "Код ответа на транзакцию")
    private ResponseCode responseCode;

    @Column(name = "authorization_code")
    @Schema(description = "Код авторизации транзакции", example = "AUTH123456")
    private String authorizationCode;


}
