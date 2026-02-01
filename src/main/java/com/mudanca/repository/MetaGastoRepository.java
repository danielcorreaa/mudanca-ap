package com.mudanca.repository;

import com.mudanca.model.MetaGasto;
import com.mudanca.model.TipoOperacao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetaGastoRepository extends MongoRepository<MetaGasto, String> {

    // Procura a meta específica de uma operação (ex: uma mudança específica) para um usuário
    Optional<MetaGasto> findByUserIdAndTipoOperacao(
            String userId,
            TipoOperacao tipoOperacao
    );
}
