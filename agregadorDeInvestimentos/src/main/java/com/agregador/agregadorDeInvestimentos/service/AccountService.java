package com.agregador.agregadorDeInvestimentos.service;


import com.agregador.agregadorDeInvestimentos.client.BrapiClient;
import com.agregador.agregadorDeInvestimentos.controller.dto.AccountStockResponseDto;
import com.agregador.agregadorDeInvestimentos.controller.dto.AssociateAccountDto;
import com.agregador.agregadorDeInvestimentos.entity.AccountStock;
import com.agregador.agregadorDeInvestimentos.entity.AccountStockId;
import com.agregador.agregadorDeInvestimentos.repository.AccountRepository;
import com.agregador.agregadorDeInvestimentos.repository.AccountStockRepository;
import com.agregador.agregadorDeInvestimentos.repository.StockRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    //Criar a variavel, clicar no run/debug -> edit configuration -> environment variebles
    @Value("#{envioronment.token")
    private String token;

    private final AccountRepository accountRepository;

    private final StockRepository stockRepository;

    private final AccountStockRepository accountStockRepository;

    private final BrapiClient brapiClient;


    public AccountService(AccountRepository accountRepository, StockRepository stockRepository, AccountStockRepository accountStockRepository, BrapiClient brapiClient) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
        this.brapiClient = brapiClient;
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
                .map(as -> new AccountStockResponseDto(
                        as.getStock().getStockId(),
                        as.getQuantity(),
                        getTotal(as.getQuantity(),
                                as.getStock().getStockId())))
                .toList();
    }

    private double getTotal(Integer quantity, String stockId) {

        var response = brapiClient.getQuote(token, stockId);

        var price = response.results().get(0).regularMarketPrice();

        return quantity * price;
    }


}
