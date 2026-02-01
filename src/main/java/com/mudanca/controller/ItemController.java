package com.mudanca.controller;

import com.mudanca.model.ItemMudanca;
import com.mudanca.repository.ItemRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/itens")
@CrossOrigin(origins = "*")
@Tag(name = "Itens de Mudança", description = "Gestão de itens e inventário para mudança")
public class ItemController {

    private final ItemRepository repository;

    public ItemController(ItemRepository repository) {
        this.repository = repository;
    }

    private String getAuthenticatedUserId() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Operation(summary = "Listar itens do utilizador", description = "Retorna todos os itens pertencentes ao utilizador autenticado")
    @GetMapping
    public List<ItemMudanca> getAll() {
        String userId = getAuthenticatedUserId();
        return repository.findByUserId(userId);
    }

    @Operation(summary = "Criar item", description = "Adiciona um novo item à lista do utilizador")
    @PostMapping
    public ItemMudanca create(@RequestBody ItemMudanca item) {
        String userId = getAuthenticatedUserId();
        item.setId(null);
        item.setUserId(userId);
        return repository.save(item);
    }

    @Operation(summary = "Criar itens em lote", description = "Permite a criação de múltiplos itens de uma só vez")
    @PostMapping("/batch")
    public List<ItemMudanca> createBatch(@RequestBody List<ItemMudanca> itens) {
        String userId = getAuthenticatedUserId();
        itens.forEach(item -> {
            item.setId(null);
            item.setUserId(userId);
        });
        return repository.saveAll(itens);
    }

    @Operation(summary = "Atualizar item", description = "Modifica os dados de um item existente")
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

    @Operation(summary = "Eliminar item", description = "Remove um item da lista")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        String userId = getAuthenticatedUserId();
        repository.findById(id).ifPresent(item -> {
            if (item.getUserId().equals(userId)) {
                repository.deleteById(id);
            }
        });
    }

    @Operation(summary = "Setup inicial (Dani)", description = "Gera uma lista predefinida de itens essenciais para o utilizador")
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