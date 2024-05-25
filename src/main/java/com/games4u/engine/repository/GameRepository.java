package com.games4u.engine.repository;

import com.games4u.engine.model.Game;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface GameRepository extends Neo4jRepository<Game, String> {
    @Query("MATCH (a)<-[r]-(g:Game)-[b]->(c:Genre {name: $genre}) WHERE NOT (:User {email: $email})-[]->(g) RETURN g, collect(r), collect(a), b ,c ORDER BY g.rating DESC LIMIT 150")
    List<Game> findByGenreSorted(String genre, String email);

    @Query("MATCH (g:Game)-[r]->(a) WHERE NOT (:User {email: $email})-[]->(g) RETURN g, collect(r), collect(a) ORDER BY g.rating DESC LIMIT 200")
    List<Game> findGamesSortedByRating(String email);
}
