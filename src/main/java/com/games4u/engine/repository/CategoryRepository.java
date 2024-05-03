package com.games4u.engine.repository;

import com.games4u.engine.model.Category;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends Neo4jRepository<Category, String> {
}
