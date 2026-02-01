package com.mudanca.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(collection = "itens_viagem")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViagemItem {
    @Id
    private String id;
    private String name;
    private BigDecimal value;
    private String categoryId;
    private LocalDate date = LocalDate.now();
    private String usuario; // Para associar ao dono da viagem
}