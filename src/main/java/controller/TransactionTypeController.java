package controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import model.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TransactionTypeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transaction-types")
@Tag(name = "transaction-type-controller", description = "Управление типами транзакций")
public class TransactionTypeController {

    private final TransactionTypeService transactionTypeService;

    @Autowired
    public TransactionTypeController(TransactionTypeService transactionTypeService) {
        this.transactionTypeService = transactionTypeService;
    }

    @Operation(summary = "Получить все типы транзакций")
    @GetMapping
    public List<TransactionType> getAllTransactionTypes() {
        return transactionTypeService.findAll();
    }

    @Operation(summary = "Получить тип транзакции по ID")
    @GetMapping("/{id}")
    public ResponseEntity<TransactionType> getTransactionTypeById(@PathVariable("id") Long id) {
        Optional<TransactionType> transactionType = transactionTypeService.findById(id);
        return transactionType.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Создать новый тип транзакции")
    @PostMapping
    public ResponseEntity<TransactionType> createTransactionType(@RequestBody TransactionType transactionType) {
        TransactionType createdTransactionType = transactionTypeService.save(transactionType);
        return ResponseEntity.status(201).body(createdTransactionType);
    }

    @Operation(summary = "Обновить тип транзакции")
    @PutMapping("/{id}")
    public ResponseEntity<TransactionType> updateTransactionType(@PathVariable("id") Long id, @RequestBody TransactionType transactionType) {
        transactionType.setId(id); // Устанавливаем ID для обновления
        Optional<TransactionType> updatedTransactionType = Optional.ofNullable(transactionTypeService.save(transactionType));
        return updatedTransactionType.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Удалить тип транзакции по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransactionType(@PathVariable("id") Long id) {
        if (transactionTypeService.findById(id).isPresent()) {
            transactionTypeService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Удалить таблицу типов транзакций (DROP)")
    @DeleteMapping("/table/drop")
    public ResponseEntity<Void> dropTransactionTypeTable() {
        transactionTypeService.dropTable();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Очистить таблицу типов транзакций (TRUNCATE)")
    @PostMapping("/table/truncate")
    public ResponseEntity<Void> truncateTransactionTypeTable() {
        transactionTypeService.truncateTable();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Создать таблицу типов транзакций (CREATE)")
    @PostMapping("/table/create")
    public ResponseEntity<Void> createTransactionTypeTable() {
        transactionTypeService.createTable();
        return ResponseEntity.ok().build();
    }
}
