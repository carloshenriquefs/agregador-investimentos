package project.agregadorinvestimentos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.agregadorinvestimentos.dto.AssociateAccountStockDto;
import project.agregadorinvestimentos.entity.AccountStock;
import project.agregadorinvestimentos.entity.AccountStockId;
import project.agregadorinvestimentos.repository.AccountRepository;
import project.agregadorinvestimentos.repository.AccountStockRepository;
import project.agregadorinvestimentos.repository.StockRepository;

import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private AccountStockRepository accountStockRepository;

    public void associateStock(String accountId, AssociateAccountStockDto associateAccountStockDto) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var stock = stockRepository.findById(associateAccountStockDto.stockId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var id = new AccountStockId(account.getAccountId(), stock.getStock_id());
        var entity = new AccountStock(
                id,
                account,
                stock,
                associateAccountStockDto.quantity()
        );

        accountStockRepository.save(entity);
    }
}
