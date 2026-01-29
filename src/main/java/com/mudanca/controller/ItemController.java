package com.mudanca.controller;

import com.mudanca.model.ItemMudanca;
import com.mudanca.repository.ItemRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// --- CONTROLADOR API (REST) ---
@RestController
@RequestMapping("/api/itens")
@CrossOrigin(origins = "*") // Permite chamadas do seu site HTML
public class ItemController {

    private final ItemRepository repository;

    public ItemController(ItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ItemMudanca> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public ItemMudanca create(@RequestBody ItemMudanca item) {
        return repository.save(item);
    }

    @PostMapping("/batch")
    public void create(@RequestBody List<ItemMudanca> items) {
        items.forEach(repository::save);
    }

    @PutMapping("/{id}")
    public ItemMudanca update(@PathVariable String id, @RequestBody ItemMudanca item) {
        item.setId(id);
        return repository.save(item);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        repository.deleteById(id);
    }
}
