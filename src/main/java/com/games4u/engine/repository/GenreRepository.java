package com.games4u.engine.repository;

import com.games4u.engine.model.Genre;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface GenreRepository extends Neo4jRepository<Genre, String> {
}
