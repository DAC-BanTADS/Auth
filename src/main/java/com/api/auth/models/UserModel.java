package com.api.auth.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;

@Document("USER")
public class UserModel implements Serializable {
    @Id
    public String id;

    public String nome;
    public String email;
    public BCryptPasswordEncoder senha;
    public String cargo;

    public UserModel() {}

    public UserModel(String nome, String email, BCryptPasswordEncoder senha, String cargo) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return String.format(
                "UserModel[id=%s, nome='%s', email='%s', senha='%s', cargo='%s']",
                id, nome, email, senha, cargo);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BCryptPasswordEncoder getSenha() {
        return senha;
    }

    public void setSenha(BCryptPasswordEncoder senha) {
        this.senha = senha;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}