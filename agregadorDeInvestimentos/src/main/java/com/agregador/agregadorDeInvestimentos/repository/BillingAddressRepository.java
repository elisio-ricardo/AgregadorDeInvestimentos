package com.agregador.agregadorDeInvestimentos.repository;

import com.agregador.agregadorDeInvestimentos.entity.BillingAddress;
import com.agregador.agregadorDeInvestimentos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface BillingAddressRepository extends JpaRepository<BillingAddress, UUID> {
}

