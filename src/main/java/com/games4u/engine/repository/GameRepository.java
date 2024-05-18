package com.games4u.engine.repository;

import com.games4u.engine.model.Game;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface GameRepository extends Neo4jRepository<Game, String> {
    @Query("MATCH (g:Game)-[r:BELONGS_TO]->(gn:Genre {name: $genre}) RETURN g ORDER BY g.rating DESC LIMIT 5")
    List<Game> findBySortedGenre(String genre);
}
