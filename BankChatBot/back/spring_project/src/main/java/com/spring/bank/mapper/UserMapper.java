package com.spring.bank.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.spring.bank.dto.UserDTO;

@Mapper
public interface UserMapper {
    // 아이디 중복 체크
    public int checkId(String userId);
    
    // 회원가입
    public void register(UserDTO user);
    
    // 로그인
    public UserDTO login(UserDTO user);
    
 // 마지막 계좌번호 조회
    public int getLastAccountNumber();

    // 사용자 목록 조회
	public List<UserDTO> getUsers();

	// 사용자 계정 활성화 토글
	public boolean updateUserStatus(String userId, int isActive);

	// 사용자 정보
	public UserDTO getAccount(int accountNumber);
}