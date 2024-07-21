package com.agregador.agregadorDeInvestimentos.service;


import com.agregador.agregadorDeInvestimentos.controller.dto.AccountResponseDto;
import com.agregador.agregadorDeInvestimentos.controller.dto.AccountStockResponseDto;
import com.agregador.agregadorDeInvestimentos.controller.dto.AssociateAccountDto;
import com.agregador.agregadorDeInvestimentos.entity.AccountStock;
import com.agregador.agregadorDeInvestimentos.entity.AccountStockId;
import com.agregador.agregadorDeInvestimentos.repository.AccountRepository;
import com.agregador.agregadorDeInvestimentos.repository.AccountStockRepository;
import com.agregador.agregadorDeInvestimentos.repository.StockRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final StockRepository stockRepository;

    private final AccountStockRepository accountStockRepository;


    public AccountService(AccountRepository accountRepository, StockRepository stockRepository, AccountStockRepository accountStockRepository) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
    }

    public void associateStock(String accountId, AssociateAccountDto dto) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var stock = stockRepository.findById(dto.stockId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var id = new AccountStockId(account.getAccountId(), stock.getStockId());

        var entity = new AccountStock(
                id,
                account,
                stock,
                dto.quantity()
        );

        accountStockRepository.save(entity);
    }

    public List<AccountStockResponseDto> listStocks(String accountId) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return account.getAccountStockList()
                .stream()
                .map(as -> new AccountStockResponseDto(as.getStock().getStockId(), as.getQuantity(), 0.0))
                .toList();
    }
}
