package com.spring.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.bank.dto.AccountDTO;
import com.spring.bank.dto.UserDTO;
import com.spring.bank.mapper.AccountMapper;
import com.spring.bank.mapper.UserMapper;

@Service
public class UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private AccountMapper accountMapper;
    
    @Transactional  // 추가
    public boolean register(UserDTO user) {
        // 아이디 중복 체크
        if(userMapper.checkId(user.getUserId()) > 0) {
            return false;
        }
        
        // 최신 계좌번호 조회 후 +1
        int lastAccountNumber = userMapper.getLastAccountNumber();
        int newAccountNumber = lastAccountNumber + 1;
        
        // 회원가입 시 계좌번호 설정
        user.setAccountNumber(newAccountNumber);
        userMapper.register(user);
        
        // 계좌 생성
        AccountDTO account = new AccountDTO();
        account.setAccountNumber(newAccountNumber);
        account.setUserId(user.getUserId());
        account.setBalance(100000);
        accountMapper.createAccount(account);
        
        return true;
    }
    
    // 로그인
    public UserDTO login(UserDTO user) {
        return userMapper.login(user);
    }

    // 사용자 정보
	public UserDTO getAccount(int accountNumber) {
	    return userMapper.getAccount(accountNumber);
	}
}