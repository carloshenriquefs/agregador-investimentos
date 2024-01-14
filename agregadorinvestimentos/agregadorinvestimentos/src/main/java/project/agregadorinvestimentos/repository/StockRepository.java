package project.agregadorinvestimentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.agregadorinvestimentos.entity.AccountStockId;
import project.agregadorinvestimentos.entity.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
}
