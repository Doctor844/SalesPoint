package controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import model.AcquiringBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AcquiringBankService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/acquiring-banks")
@Tag(name = "acquiring-bank-controller", description = "Управление эквайринг-банками")
public class AcquiringBankController {

    private final AcquiringBankService acquiringBankService;

    @Autowired
    public AcquiringBankController(AcquiringBankService acquiringBankService) {
        this.acquiringBankService = acquiringBankService;
    }

    @Operation(summary = "Получить все эквайринг-банки")
    @GetMapping
    public List<AcquiringBank> getAllAcquiringBanks() {
        return acquiringBankService.findAll();
    }

    @Operation(summary = "Получить эквайринг-банк по ID")
    @GetMapping("/{id}")
    public ResponseEntity<AcquiringBank> getAcquiringBankById(@PathVariable("id") Long id) {
        Optional<AcquiringBank> acquiringBank = acquiringBankService.findById(id);
        return acquiringBank.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Создать новый эквайринг-банк")
    @PostMapping
    public ResponseEntity<AcquiringBank> createAcquiringBank(@RequestBody AcquiringBank acquiringBank) {
        acquiringBankService.save(acquiringBank);
        return ResponseEntity.status(201).body(acquiringBank);
    }

    @Operation(summary = "Обновить эквайринг-банк по ID")
    @PutMapping("/{id}")
    public ResponseEntity<AcquiringBank> updateAcquiringBank(@PathVariable("id") Long id, @RequestBody AcquiringBank acquiringBank) {
        acquiringBank.setId(id);
        acquiringBankService.save(acquiringBank);
        return ResponseEntity.ok(acquiringBank);
    }

    @Operation(summary = "Удалить эквайринг-банк по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcquiringBank(@PathVariable("id") Long id) {
        if (acquiringBankService.findById(id).isPresent()) {
            acquiringBankService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Очистить таблицу acquiring_bank (TRUNCATE)")
    @PostMapping("/table/truncate")
    public ResponseEntity<Void> truncateAcquiringBankTable() {
        acquiringBankService.truncateTable();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Удалить таблицу acquiring_bank (DROP)")
    @DeleteMapping("/table/drop")
    public ResponseEntity<Void> dropAcquiringBankTable() {
        acquiringBankService.dropTable();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Создать таблицу acquiring_bank (CREATE)")
    @PostMapping("/table/create")
    public ResponseEntity<Void> createAcquiringBankTable() {
        acquiringBankService.createTable();
        return ResponseEntity.ok().build();
    }
}
