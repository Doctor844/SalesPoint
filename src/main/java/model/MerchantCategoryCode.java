package model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "merchant_category_code")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Schema(description = "Код категории торговца (MCC)")
public class MerchantCategoryCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "Уникальный идентификатор кода категории торговца", example = "101")
    private Long id;

    @Column(name = "mcc")
    @Schema(description = "Код категории торговца (MCC)", example = "5411")
    private String mcc;

    @Column(name = "mcc_name")
    @Schema(description = "Название категории торговца", example = "Рестораны")
    private String mccName;
}
