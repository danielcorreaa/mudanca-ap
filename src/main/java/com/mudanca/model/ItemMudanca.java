package com.mudanca.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// --- MODELO (ENTIDADE MONGODB) ---
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "itens_mudanca")
public class ItemMudanca {
    @Id
    private String id;
    private String name;
    private Double value;
    private String category; // "novo" ou "antigo"
    private Boolean completed;
}