package com.spring.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.bank.dto.AccountDTO;
import com.spring.bank.dto.TransactionDTO;
import com.spring.bank.dto.UserDTO;
import com.spring.bank.mapper.AccountMapper;
import com.spring.bank.mapper.UserMapper;

@Service
public class AdminService {
    
    @Autowired
    private UserMapper userMapper;
    
    // 사용자 목록 조회
	public List<UserDTO> getUsers() {
		return userMapper.getUsers();
	}

	// 사용자 계정 활성화 토글
	public boolean updateUserStatus(String userId, int isActive) {
		return userMapper.updateUserStatus(userId, isActive);
	}
}