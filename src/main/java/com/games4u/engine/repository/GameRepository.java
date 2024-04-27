package com.games4u.engine.repository;

import com.games4u.engine.model.Game;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface GameRepository extends Neo4jRepository<Game, String> {
}
