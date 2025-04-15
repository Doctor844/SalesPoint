package model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "payment_system")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Schema(description = "Платёжная система")
public class PaymentSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "Уникальный идентификатор платёжной системы", example = "1")
    private Long id;

    @Column(name = "payment_system_name")
    @Schema(description = "Название платёжной системы", example = "Visa")
    private String paymentSystemName;
}
