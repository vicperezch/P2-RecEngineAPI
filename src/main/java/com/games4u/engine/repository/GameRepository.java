package com.games4u.engine.repository;

import com.games4u.engine.model.Game;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface GameRepository extends Neo4jRepository<Game, String> {
    @Query("MATCH (g:Game)-[]->(:Genre {name: $genre}) RETURN g.name ORDER BY g.rating DESC LIMIT 10")
    List<String> findByGenreSorted(String genre);
}
