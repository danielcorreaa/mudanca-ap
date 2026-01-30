package com.mudanca.repository;

import com.mudanca.model.ItemMudanca;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

// --- REPOSITÓRIO ---
public interface ItemRepository extends MongoRepository<ItemMudanca, String> {

    List<ItemMudanca> findByUserId(String userId);

}
