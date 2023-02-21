package com.api.auth.repositories;

import com.api.auth.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository("UserRepository")
public interface UserRepository extends MongoRepository<UserModel, UUID> {
    boolean existsByEmail(String email);
    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findByEmailAndSenha(String email, String senha);
}
