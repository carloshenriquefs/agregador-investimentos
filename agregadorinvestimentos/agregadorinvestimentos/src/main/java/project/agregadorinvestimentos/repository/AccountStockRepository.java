package project.agregadorinvestimentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.agregadorinvestimentos.entity.AccountStock;

import java.util.UUID;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, UUID> {
}
