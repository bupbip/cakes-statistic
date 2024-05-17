package ru.kustikov.cakes.consumables;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/consumable")
@RequiredArgsConstructor
public class ConsumableController {
    private final ConsumableService consumableService;

    @GetMapping("/get-consumable")
    public ResponseEntity<List<Consumable>> get(@RequestParam String username) {
        return consumableService.get(username);
    }

    @PostMapping("/update-consumable")
    public ResponseEntity<Consumable> update(@RequestBody Consumable consumable) {
        return consumableService.update(consumable);
    }

    @DeleteMapping("/delete-consumable")
    public ResponseEntity<String> delete(@RequestParam Long consumableId) {
        return consumableService.delete(consumableId);
    }
}
