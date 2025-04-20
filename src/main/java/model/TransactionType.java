package model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Table(name = "transaction_type")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Schema(description = "Тип транзакции")
public class TransactionType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "Уникальный идентификатор типа транзакции", example = "1")
    private Long id;

    @Column(name = "transaction_type_name")
    @Schema(description = "Название типа транзакции", example = "Оплата")
    private String transactionTypeName;

    @Column(name = "operator")
    @Schema(description = "Оператор транзакции", example = "Visa")
    private String operator;
}
