package model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Table(name = "card")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Schema(description = "Банковская карта")
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "Уникальный идентификатор карты", example = "101")
    private Long id;

    @Column(name = "card_number")
    @Schema(description = "Номер карты", example = "1234-5678-9876-5432")
    private String cardNumber;

    @Column(name = "expiration_date")
    @Schema(description = "Срок действия карты", example = "2026-08-31")
    private LocalDate expirationDate;

    @Column(name = "holder_name")
    @Schema(description = "Имя держателя карты", example = "Иван Иванов")
    private String holderName;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "payment_system_id")
    @Schema(description = "Платёжная система")
    private PaymentSystem paymentSystem;

}