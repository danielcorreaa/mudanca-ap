package com.mudanca.repository;

import com.mudanca.model.ViagemItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ViagemItemRepository extends MongoRepository<ViagemItem, String> {}

