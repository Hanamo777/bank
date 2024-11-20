package com.spring.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.spring.bank.service.AccountService;
import com.spring.bank.dto.AccountDTO;
import com.spring.bank.dto.TransactionDTO;
import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;
    
    @GetMapping("/{accountNumber}")
    public AccountDTO getAccount(@PathVariable int accountNumber) {
        System.out.println("계좌 조회: " + accountNumber);  // 디버깅용
        return accountService.getAccount(accountNumber);
    }
    
    @GetMapping("/find/{userId}")
    public AccountDTO getAccountByUserId(@PathVariable String userId) {
    	System.out.println("추천인 ID: " + userId);
    	return accountService.getAccountByUserId(userId);
    }
    
    @PostMapping("/transfer")
    public String transfer(@RequestBody TransactionDTO transaction) {
        System.out.println("이체 요청: " + transaction.getSenderAccount() + " -> " + transaction.getReceiverAccount());  // 디버깅용
        boolean result = accountService.transfer(
            transaction.getSenderAccount(), 
            transaction.getReceiverAccount(), 
            transaction.getAmount()
        );
        return result ? "success" : "fail";
    }
    
    @GetMapping("/history/{accountNumber}")
    public List<TransactionDTO> getHistory(@PathVariable int accountNumber) {
        System.out.println("거래내역 조회: " + accountNumber);  // 디버깅용
        return accountService.getTransactionHistory(accountNumber);
    }
}