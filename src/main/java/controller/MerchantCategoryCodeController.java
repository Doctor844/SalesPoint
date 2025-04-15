package controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import model.MerchantCategoryCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.MerchantCategoryCodeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/merchant-category-codes")
@Tag(name = "merchant-category-code-controller", description = "Управление кодами категорий торговцев")
public class MerchantCategoryCodeController {

    private final MerchantCategoryCodeService merchantCategoryCodeService;

    @Autowired
    public MerchantCategoryCodeController(MerchantCategoryCodeService merchantCategoryCodeService) {
        this.merchantCategoryCodeService = merchantCategoryCodeService;
    }

    @Operation(summary = "Получить все MerchantCategoryCode")
    @GetMapping
    public List<MerchantCategoryCode> getAllMerchantCategoryCodes() {
        return merchantCategoryCodeService.findAll();
    }

    @Operation(summary = "Получить MerchantCategoryCode по ID")
    @GetMapping("/{id}")
    public ResponseEntity<MerchantCategoryCode> getMerchantCategoryCodeById(@PathVariable("id") Long id) {
        Optional<MerchantCategoryCode> merchantCategoryCode = merchantCategoryCodeService.findById(id);
        return merchantCategoryCode.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Создать новый MerchantCategoryCode")
    @PostMapping
    public ResponseEntity<MerchantCategoryCode> createMerchantCategoryCode(@RequestBody MerchantCategoryCode merchantCategoryCode) {
        MerchantCategoryCode createdMerchantCategoryCode = merchantCategoryCodeService.save(merchantCategoryCode);
        return ResponseEntity.status(201).body(createdMerchantCategoryCode);
    }

    @Operation(summary = "Обновить MerchantCategoryCode")
    @PutMapping("/{id}")
    public ResponseEntity<MerchantCategoryCode> updateMerchantCategoryCode(@PathVariable("id") Long id, @RequestBody MerchantCategoryCode merchantCategoryCode) {
        merchantCategoryCode.setId(id);
        Optional<MerchantCategoryCode> updatedMerchantCategoryCode = Optional.ofNullable(merchantCategoryCodeService.save(merchantCategoryCode));
        return updatedMerchantCategoryCode.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Удалить MerchantCategoryCode по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchantCategoryCode(@PathVariable("id") Long id) {
        if (merchantCategoryCodeService.findById(id).isPresent()) {
            merchantCategoryCodeService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Удалить таблицу merchant_category_codes (DROP)")
    @DeleteMapping("/table/drop")
    public ResponseEntity<Void> dropMerchantCategoryCodeTable() {
        merchantCategoryCodeService.dropTable();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Очистить таблицу merchant_category_codes (TRUNCATE)")
    @PostMapping("/table/truncate")
    public ResponseEntity<Void> truncateMerchantCategoryCodeTable() {
        merchantCategoryCodeService.truncateTable();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Создать таблицу merchant_category_codes (CREATE)")
    @PostMapping("/table/create")
    public ResponseEntity<Void> createMerchantCategoryCodeTable() {
        merchantCategoryCodeService.createTable();
        return ResponseEntity.ok().build();
    }
}
