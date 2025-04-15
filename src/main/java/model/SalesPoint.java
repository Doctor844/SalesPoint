package model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "sales_point")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Schema(description = "Торговая точка")
public class SalesPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "Уникальный идентификатор торговой точки", example = "1")
    private Long id;

    @Column(name = "pos_name")
    @Schema(description = "Название торговой точки", example = "Точка 1")
    private String posName;

    @Column(name = "pos_address")
    @Schema(description = "Адрес торговой точки", example = "Москва, ул. Пушкина, д. 10")
    private String posAddress;

    @Column(name = "pos_inn")
    @Schema(description = "ИНН торговой точки", example = "7701234567")
    private String posInn;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "acquiring_bank_id")
    @Schema(description = "Аквирирующий банк, связанный с торговой точкой")
    private AcquiringBank acquiringBank;
}
