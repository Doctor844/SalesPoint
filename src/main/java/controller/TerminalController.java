package controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import model.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TerminalService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/terminals")
@Tag(name = "terminal-controller", description = "Управление терминалами")
public class TerminalController {

    private final TerminalService terminalService;

    @Autowired
    public TerminalController(TerminalService terminalService) {
        this.terminalService = terminalService;
    }

    @Operation(summary = "Получить все терминалы")
    @GetMapping
    public List<Terminal> getAllTerminals() {
        return terminalService.findAll();
    }

    @Operation(summary = "Получить терминал по ID")
    @GetMapping("/{id}")
    public ResponseEntity<Terminal> getTerminalById(@PathVariable("id") Long id) {
        Optional<Terminal> terminal = terminalService.findById(id);
        return terminal.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Создать новый терминал")
    @PostMapping
    public ResponseEntity<Terminal> createTerminal(@RequestBody Terminal terminal) {
        Terminal createdTerminal = terminalService.save(terminal);
        return ResponseEntity.status(201).body(createdTerminal);
    }

    @Operation(summary = "Обновить терминал")
    @PutMapping("/{id}")
    public ResponseEntity<Terminal> updateTerminal(@PathVariable("id") Long id, @RequestBody Terminal terminal) {
        terminal.setId(id); // Устанавливаем ID для обновления
        Optional<Terminal> updatedTerminal = Optional.ofNullable(terminalService.save(terminal));
        return updatedTerminal.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Удалить терминал по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTerminal(@PathVariable("id") Long id) {
        if (terminalService.findById(id).isPresent()) {
            terminalService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Удалить таблицу терминалов (DROP)")
    @DeleteMapping("/table/drop")
    public ResponseEntity<Void> dropTerminalTable() {
        terminalService.dropTable();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Очистить таблицу терминалов (TRUNCATE)")
    @PostMapping("/table/truncate")
    public ResponseEntity<Void> truncateTerminalTable() {
        terminalService.truncateTable();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Создать таблицу терминалов (CREATE)")
    @PostMapping("/table/create")
    public ResponseEntity<Void> createTerminalTable() {
        terminalService.createTable();
        return ResponseEntity.ok().build();
    }
}
