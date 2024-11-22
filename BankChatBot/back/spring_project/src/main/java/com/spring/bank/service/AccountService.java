package com.spring.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.spring.bank.mapper.AccountMapper;
import com.spring.bank.dto.AccountDTO;
import com.spring.bank.dto.TransactionDTO;

@Service
public class AccountService {
    
    @Autowired
    private AccountMapper accountMapper;
    
    // 계좌 조회
    public AccountDTO getAccount(int accountNumber) {
        return accountMapper.getAccount(accountNumber);
    }
    
    // 계좌 조회 (추천인 ID)
    public AccountDTO getAccountByUserId(String userId) {
    	return accountMapper.getAccountByUserId(userId);
    }
    
    // 계좌 이체
    @Transactional
    public boolean transfer(int senderAccount, int receiverAccount, int amount, int fee) {
        // 송신자 계좌 확인
        AccountDTO sender = accountMapper.getAccount(senderAccount);
        if(sender.getBalance() < amount + fee) { // 수수료 500원 포함
            return false;
        }
        
        // 수신자 계좌 확인
        AccountDTO receiver = accountMapper.getAccount(receiverAccount);
        if(receiver == null) {
            return false;
        }
        
        // 송신자 잔액 감소
        sender.setBalance(sender.getBalance() - amount - fee);
        accountMapper.updateBalance(sender);
        
        // 수신자 잔액 증가
        receiver.setBalance(receiver.getBalance() + amount);
        accountMapper.updateBalance(receiver);
        
        // 송신자 거래내역 저장
        TransactionDTO senderTx = new TransactionDTO();
        senderTx.setSenderAccount(senderAccount);
        senderTx.setReceiverAccount(receiverAccount);
        senderTx.setAmount(amount + fee); // 수수료 포함
        senderTx.setBalanceAfter(sender.getBalance());
        accountMapper.saveTransaction(senderTx);
        
        // 수신자 거래내역 저장
        TransactionDTO receiverTx = new TransactionDTO();
        receiverTx.setSenderAccount(senderAccount);
        receiverTx.setReceiverAccount(receiverAccount);
        receiverTx.setAmount(amount);
        receiverTx.setBalanceAfter(receiver.getBalance());
        accountMapper.saveTransaction(receiverTx);
        
        return true;
    }
    
    // 거래내역 조회
    public List<TransactionDTO> getTransactionHistory(int accountNumber) {
        return accountMapper.getTransactionHistory(accountNumber);
    }
}