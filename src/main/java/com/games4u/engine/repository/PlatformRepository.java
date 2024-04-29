package com.games4u.engine.repository;

import com.games4u.engine.model.Platform;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PlatformRepository extends Neo4jRepository<Platform, String> {
}
