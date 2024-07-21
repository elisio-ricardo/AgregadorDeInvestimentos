package com.agregador.agregadorDeInvestimentos.service;


import com.agregador.agregadorDeInvestimentos.controller.dto.CreateStockDto;
import com.agregador.agregadorDeInvestimentos.entity.Stock;
import com.agregador.agregadorDeInvestimentos.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {


    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDto createStockDto) {

        var stock = new Stock(
                createStockDto.stockId(),
                createStockDto.description()
        );

        stockRepository.save(stock);
    }
}
