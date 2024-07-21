package com.agregador.agregadorDeInvestimentos.controller;


import com.agregador.agregadorDeInvestimentos.controller.dto.AccountStockResponseDto;
import com.agregador.agregadorDeInvestimentos.controller.dto.AssociateAccountDto;
import com.agregador.agregadorDeInvestimentos.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {


    private final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> createStock(@PathVariable("accountId") String accountId,
                                            @RequestBody AssociateAccountDto dto) {
        accountService.associateStock(accountId, dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockResponseDto>> getStock(@PathVariable("accountId") String accountId) {
        var stocks = accountService.listStocks(accountId);

        return ResponseEntity.ok(stocks);
    }
}
