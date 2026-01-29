package com.mudanca.repository;

import com.mudanca.model.ItemMudanca;
import org.springframework.data.mongodb.repository.MongoRepository;

// --- REPOSITÓRIO ---
public interface ItemRepository extends MongoRepository<ItemMudanca, String> {
}
