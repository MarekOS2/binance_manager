package com.marek.binance_manager.repository;

import com.marek.binance_manager.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Optional<Authority> getAuthorityByName(String name);
}
