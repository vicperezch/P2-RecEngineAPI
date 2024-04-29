package com.games4u.engine.repository;

import com.games4u.engine.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserRepository extends Neo4jRepository<User, String>{
}
