package com.mudanca.controller;

import com.mudanca.controller.dto.MetaGastoDTO;
import com.mudanca.model.MetaGasto;
import com.mudanca.model.TipoOperacao;
import com.mudanca.repository.MetaGastoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/metas")
@CrossOrigin(origins = "*")
@Tag(name = "Metas de Gastos", description = "Gestão de limites e orçamentos por tipo de operação")
public class MetaGastoController {

    private final MetaGastoRepository repository;

    public MetaGastoController(MetaGastoRepository repository) {
        this.repository = repository;
    }

    @Operation(summary = "Consultar valor da meta", description = "Obtém o valor limite definido para um tipo de operação")
    @GetMapping("/{tip}/{operacaoId}")
    public ResponseEntity<Double> getValorMeta(@PathVariable TipoOperacao tip, @PathVariable String operacaoId) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return repository.findByUserIdAndTipoOperacao(userId, tip)
                .map(meta -> ResponseEntity.ok(meta.getValorMeta()))
                .orElse(ResponseEntity.ok(0.0));
    }

    @Operation(summary = "Guardar meta", description = "Cria ou atualiza o limite de gastos para um tipo de operação")
    @PostMapping("/{tip}")
    public ResponseEntity<MetaGasto> saveMeta(@PathVariable TipoOperacao tip, @RequestBody MetaGastoDTO metaGastoDTO) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String operacaoId = tip.toString() + "--"+ userId;

        MetaGasto meta = repository.findByUserIdAndTipoOperacao(userId, tip)
                .orElse(new MetaGasto(operacaoId, tip, metaGastoDTO.valor(), userId));
        meta.setValorMeta(metaGastoDTO.valor());
        return ResponseEntity.ok(repository.save(meta));
    }
}