package controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import model.SalesPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.SalesPointService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sales-points")
@Tag(name = "sales-point-controller", description = "Управление точками продаж")
public class SalesPointController {

    private final SalesPointService salesPointService;

    @Autowired
    public SalesPointController(SalesPointService salesPointService) {
        this.salesPointService = salesPointService;
    }

    @Operation(summary = "Получить все точки продаж")
    @GetMapping
    public List<SalesPoint> getAllSalesPoints() {
        return salesPointService.findAll();
    }

    @Operation(summary = "Получить точку продаж по ID")
    @GetMapping("/{id}")
    public ResponseEntity<SalesPoint> getSalesPointById(@PathVariable("id") Long id) {
        Optional<SalesPoint> salesPoint = salesPointService.findById(id);
        return salesPoint.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Создать новую точку продаж")
    @PostMapping
    public ResponseEntity<SalesPoint> createSalesPoint(@RequestBody SalesPoint salesPoint) {
        SalesPoint createdSalesPoint = salesPointService.save(salesPoint);
        return ResponseEntity.status(201).body(createdSalesPoint);
    }

    @Operation(summary = "Обновить точку продаж")
    @PutMapping("/{id}")
    public ResponseEntity<SalesPoint> updateSalesPoint(@PathVariable("id") Long id, @RequestBody SalesPoint salesPoint) {
        salesPoint.setId(id); // Устанавливаем ID для обновления
        Optional<SalesPoint> updatedSalesPoint = Optional.ofNullable(salesPointService.save(salesPoint));
        return updatedSalesPoint.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Удалить точку продаж по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalesPoint(@PathVariable("id") Long id) {
        if (salesPointService.findById(id).isPresent()) {
            salesPointService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Удалить таблицу sales_points (DROP)")
    @DeleteMapping("/table/drop")
    public ResponseEntity<Void> dropSalesPointTable() {
        salesPointService.dropTable();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Очистить таблицу sales_points (TRUNCATE)")
    @PostMapping("/table/truncate")
    public ResponseEntity<Void> truncateSalesPointTable() {
        salesPointService.truncateTable();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Создать таблицу sales_points (CREATE)")
    @PostMapping("/table/create")
    public ResponseEntity<Void> createSalesPointTable() {
        salesPointService.createTable();
        return ResponseEntity.ok().build();
    }
}
