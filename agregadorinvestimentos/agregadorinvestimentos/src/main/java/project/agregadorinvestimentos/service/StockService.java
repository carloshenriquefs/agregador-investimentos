package project.agregadorinvestimentos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.agregadorinvestimentos.dto.CreateStockDto;
import project.agregadorinvestimentos.entity.Stock;
import project.agregadorinvestimentos.repository.StockRepository;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public void createStock(CreateStockDto createStockDto) {

        var stock = new Stock(
                createStockDto.stockId(),
                createStockDto.description()
        );

        stockRepository.save(stock);
    }
}
