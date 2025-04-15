package controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import model.PaymentSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.PaymentSystemService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payment-systems")
@Tag(name = "payment-system-controller", description = "Управление платёжными системами")
public class PaymentSystemController {

    private final PaymentSystemService paymentSystemService;

    @Autowired
    public PaymentSystemController(PaymentSystemService paymentSystemService) {
        this.paymentSystemService = paymentSystemService;
    }

    @Operation(summary = "Получить все платёжные системы")
    @GetMapping
    public List<PaymentSystem> getAllPaymentSystems() {
        return paymentSystemService.findAll();
    }

    @Operation(summary = "Получить платёжную систему по ID")
    @GetMapping("/{id}")
    public ResponseEntity<PaymentSystem> getPaymentSystemById(@PathVariable("id") Long id) {
        Optional<PaymentSystem> paymentSystem = paymentSystemService.findById(id);
        return paymentSystem.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Создать новую платёжную систему")
    @PostMapping
    public ResponseEntity<PaymentSystem> createPaymentSystem(@RequestBody PaymentSystem paymentSystem) {
        PaymentSystem createdPaymentSystem = paymentSystemService.save(paymentSystem);
        return ResponseEntity.status(201).body(createdPaymentSystem);
    }

    @Operation(summary = "Обновить платёжную систему")
    @PutMapping("/{id}")
    public ResponseEntity<PaymentSystem> updatePaymentSystem(@PathVariable("id") Long id, @RequestBody PaymentSystem paymentSystem) {
        paymentSystem.setId(id); // Устанавливаем ID для обновления
        Optional<PaymentSystem> updatedPaymentSystem = Optional.ofNullable(paymentSystemService.save(paymentSystem));
        return updatedPaymentSystem.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Удалить платёжную систему по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentSystem(@PathVariable("id") Long id) {
        if (paymentSystemService.findById(id).isPresent()) {
            paymentSystemService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Удалить таблицу payment_systems (DROP)")
    @DeleteMapping("/table/drop")
    public ResponseEntity<Void> dropPaymentSystemTable() {
        paymentSystemService.dropTable();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Очистить таблицу payment_systems (TRUNCATE)")
    @PostMapping("/table/truncate")
    public ResponseEntity<Void> truncatePaymentSystemTable() {
        paymentSystemService.truncateTable();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Создать таблицу payment_systems (CREATE)")
    @PostMapping("/table/create")
    public ResponseEntity<Void> createPaymentSystemTable() {
        paymentSystemService.createTable();
        return ResponseEntity.ok().build();
    }
}
