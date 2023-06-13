package com.marek.binance_manager.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    private String password;
    private String apiKey;
    private String secretKey;
}
