package com.mudanca.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "meta-gasto")
public class MetaGasto {

    @Id
    private String id;
    private TipoOperacao tipoOperacao; // Define se é MUDANCA ou VIAGEM
    private Double valorMeta;
    private String userId; // Dono


}
