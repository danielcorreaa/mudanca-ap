package com.mudanca.controller;

import com.mudanca.model.ItemMudanca;
import com.mudanca.repository.ItemRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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

    private String getAuthenticatedUserId() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    @GetMapping
    public List<ItemMudanca> getAll() {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return repository.findByUserId(userId);
    }

    @PostMapping
    public ItemMudanca create(@RequestBody ItemMudanca item) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        item.setId(null);
        item.setUserId(userId);
        return repository.save(item);
    }

    @PostMapping("/batch")
    public List<ItemMudanca> createBatch(@RequestBody List<ItemMudanca> itens) {
        String userId = getAuthenticatedUserId();
        itens.forEach(item -> {
            item.setId(null);
            item.setUserId(userId);
        });
        return repository.saveAll(itens);
    }

    @PutMapping("/{id}")
    public ItemMudanca update(@PathVariable String id, @RequestBody ItemMudanca item) {
        String userId = getAuthenticatedUserId();
        return repository.findById(id)
                .filter(existing -> existing.getUserId().equals(userId))
                .map(existing -> {
                    item.setId(id);
                    item.setUserId(userId);
                    return repository.save(item);
                })
                .orElseThrow(() -> new RuntimeException("Item não encontrado ou acesso negado"));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        String userId = getAuthenticatedUserId();
        repository.findById(id).ifPresent(item -> {
            if (item.getUserId().equals(userId)) {
                repository.deleteById(id);
            }
        });
    }

    // Endpoint para gerar a lista padrão para a Dani (ou qualquer utilizador logado)
    @PostMapping("/setup-dani")
    public List<ItemMudanca> setupDani() {
        String userId = getAuthenticatedUserId();

        List<ItemMudanca> itensPadrao = Arrays.asList(
                new ItemMudanca(null, "Piso (Compra)", 0.0, "novo", false, userId),
                new ItemMudanca(null, "Pedreiro (Mão de obra)", 0.0, "novo", false, userId),
                new ItemMudanca(null, "Box Banheiro (Vidraceiro)", 0.0, "novo", false, userId),
                new ItemMudanca(null, "Sofá", 0.0, "novo", false, userId),
                new ItemMudanca(null, "Geladeira", 0.0, "novo", false, userId),
                new ItemMudanca(null, "Máquina de Lavar", 0.0, "novo", false, userId),
                new ItemMudanca(null, "Pintura Geral", 0.0, "antigo", false, userId),
                new ItemMudanca(null, "Tampar buracos nas paredes", 0.0, "antigo", false, userId)
        );

        return repository.saveAll(itensPadrao);
    }
}
