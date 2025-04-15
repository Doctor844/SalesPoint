package model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "terminal")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Schema(description = "Терминал")
public class Terminal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "Уникальный идентификатор терминала", example = "1")
    private Long id;

    @Column(name = "terminal_id")
    @Schema(description = "Идентификатор терминала", example = "T12345")
    private String terminalId;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "mcc_id")
    @Schema(description = "Категория торговца (MCC), связанная с терминалом")
    private MerchantCategoryCode mcc;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pos_id")
    @Schema(description = "Торговая точка, с которой связан терминал")
    private SalesPoint pos;
}
