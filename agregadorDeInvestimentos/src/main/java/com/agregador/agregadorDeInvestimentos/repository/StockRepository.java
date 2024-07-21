package com.agregador.agregadorDeInvestimentos.repository;

import com.agregador.agregadorDeInvestimentos.entity.Account;
import com.agregador.agregadorDeInvestimentos.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
}

