package com.mudanca.controller;

import com.mudanca.controller.dto.UpdateValueDTO;
import com.mudanca.model.Categoria;
import com.mudanca.model.ViagemItem;
import com.mudanca.repository.CategoriaRepository;
import com.mudanca.repository.ViagemItemRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/trip")
@CrossOrigin(origins = "*")
@Tag(name = "Viagem", description = "Endpoints para gestão de gastos e categorias de viagem")
public class TripController {

    private final ViagemItemRepository itemRepo;
    private final CategoriaRepository catRepo;

    public TripController(ViagemItemRepository itemRepo, CategoriaRepository catRepo) {
        this.itemRepo = itemRepo;
        this.catRepo = catRepo;
    }

    @Operation(summary = "Inicializar categorias", description = "Cria as categorias padrão de viagem caso não existam no banco de dados")
    @PostMapping("/categories/init")
    public List<Categoria> initCategories() {
        if (catRepo.count() == 0) {
            catRepo.save(new Categoria("passagem", "Passagem", "🎫", "blue"));
            catRepo.save(new Categoria("hospedagem", "Hospedagem", "🏨", "purple"));
            catRepo.save(new Categoria("alimentacao", "Alimentação", "🍽️", "orange"));
            catRepo.save(new Categoria("passeio", "Passeio", "🎡", "pink"));
            catRepo.save(new Categoria("transporte", "Transporte", "🚗", "cyan"));
        }
        return catRepo.findAll();
    }

    @Operation(summary = "Listar todos os itens", description = "Retorna todos os itens de gastos de viagem registados")
    @GetMapping("")
    public List<ViagemItem> getAllItems() {
        return itemRepo.findAll();
    }

    @Operation(summary = "Adicionar item", description = "Regista um novo gasto de viagem")
    @PostMapping("")
    public ViagemItem addItem(@RequestBody ViagemItem item) {
        return itemRepo.save(item);
    }

    @Operation(summary = "Eliminar item", description = "Remove um gasto de viagem pelo seu ID")
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable String id) {
        itemRepo.deleteById(id);
    }

    @Operation(summary = "Listar categorias", description = "Retorna todas as categorias de viagem disponíveis")
    @GetMapping("/categories")
    public List<Categoria> getCategories() {
        return catRepo.findAll();
    }

    @Operation(summary = "Atualizar valor do item", description = "Atualiza o valor monetário de um gasto específico")
    @PutMapping("/{id}")
    public ViagemItem updateItemValue(@PathVariable String id, @RequestBody UpdateValueDTO updateValueDTO) {
        ViagemItem item = itemRepo.findById(id).orElseThrow();
        item.setValue(updateValueDTO.newValue());
        return itemRepo.save(item);
    }
}