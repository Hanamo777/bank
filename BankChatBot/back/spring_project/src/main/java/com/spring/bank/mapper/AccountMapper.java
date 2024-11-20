package com.spring.bank.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.spring.bank.dto.AccountDTO;
import com.spring.bank.dto.TransactionDTO;

@Mapper
public interface AccountMapper {
    // 계좌 잔액 조회
    public AccountDTO getAccount(int accountNumber);
    
    // 계좌 잔액 업데이트
    public void updateBalance(AccountDTO account);
    
    // 거래내역 저장
    public void saveTransaction(TransactionDTO transaction);
    
    // 거래내역 조회
    public List<TransactionDTO> getTransactionHistory(int accountNumber);

 // 계좌 생성
    public void createAccount(AccountDTO account);
    
    // 추천인 ID로 계좌 조회
    public AccountDTO getAccountByUserId(String userId);
}