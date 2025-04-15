package controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import model.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ResponseCodeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/response-codes")
@Tag(name = "response-code-controller", description = "Управление кодами ответов")
public class ResponseCodeController {

    private final ResponseCodeService responseCodeService;

    @Autowired
    public ResponseCodeController(ResponseCodeService responseCodeService) {
        this.responseCodeService = responseCodeService;
    }

    @Operation(summary = "Получить все коды ответов")
    @GetMapping
    public List<ResponseCode> getAllResponseCodes() {
        return responseCodeService.findAll();
    }

    @Operation(summary = "Получить код ответа по ID")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseCode> getResponseCodeById(@PathVariable("id") Long id) {
        Optional<ResponseCode> responseCode = responseCodeService.findById(id);
        return responseCode.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Создать новый код ответа")
    @PostMapping
    public ResponseEntity<ResponseCode> createResponseCode(@RequestBody ResponseCode responseCode) {
        ResponseCode createdResponseCode = responseCodeService.save(responseCode);
        return ResponseEntity.status(201).body(createdResponseCode);
    }

    @Operation(summary = "Обновить код ответа")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseCode> updateResponseCode(@PathVariable("id") Long id, @RequestBody ResponseCode responseCode) {
        responseCode.setId(id); // Устанавливаем ID для обновления
        Optional<ResponseCode> updatedResponseCode = Optional.ofNullable(responseCodeService.save(responseCode));
        return updatedResponseCode.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Удалить код ответа по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResponseCode(@PathVariable("id") Long id) {
        if (responseCodeService.findById(id).isPresent()) {
            responseCodeService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Удалить таблицу response_codes (DROP)")
    @DeleteMapping("/table/drop")
    public ResponseEntity<Void> dropResponseCodeTable() {
        responseCodeService.dropTable();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Очистить таблицу response_codes (TRUNCATE)")
    @PostMapping("/table/truncate")
    public ResponseEntity<Void> truncateResponseCodeTable() {
        responseCodeService.truncateTable();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Создать таблицу response_codes (CREATE)")
    @PostMapping("/table/create")
    public ResponseEntity<Void> createResponseCodeTable() {
        responseCodeService.createTable();
        return ResponseEntity.ok().build();
    }
}
