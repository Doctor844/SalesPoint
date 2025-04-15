package controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TransactionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
@Tag(name = "transaction-controller", description = "Управление транзакциями")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Получить все транзакции")
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.findAll();
    }

    @Operation(summary = "Получить транзакцию по ID")
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable("id") Long id) {
        Optional<Transaction> transaction = transactionService.findById(id);
        return transaction.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Создать новую транзакцию")
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction createdTransaction = transactionService.save(transaction);
        return ResponseEntity.status(201).body(createdTransaction);
    }

    @Operation(summary = "Обновить транзакцию")
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable("id") Long id, @RequestBody Transaction transaction) {
        transaction.setId(id); // Устанавливаем ID для обновления
        Optional<Transaction> updatedTransaction = Optional.ofNullable(transactionService.save(transaction));
        return updatedTransaction.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Удалить транзакцию по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable("id") Long id) {
        if (transactionService.findById(id).isPresent()) {
            transactionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Удалить таблицу транзакций (DROP)")
    @DeleteMapping("/table/drop")
    public ResponseEntity<Void> dropTransactionTable() {
        transactionService.dropTable();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Очистить таблицу транзакций (TRUNCATE)")
    @PostMapping("/table/truncate")
    public ResponseEntity<Void> truncateTransactionTable() {
        transactionService.truncateTable();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Создать таблицу транзакций (CREATE)")
    @PostMapping("/table/create")
    public ResponseEntity<Void> createTransactionTable() {
        transactionService.createTable();
        return ResponseEntity.ok().build();
    }
}
