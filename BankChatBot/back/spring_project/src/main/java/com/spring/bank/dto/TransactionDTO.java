package com.spring.bank.dto;

import java.time.LocalDateTime;

public class TransactionDTO {
    private int id;
    private int senderAccount;
    private int receiverAccount;
    private int amount;
    private LocalDateTime transactionTime;
    private int balanceAfter;    // 추가
    private String senderName;
    private String receiverName;
    private int fee;
                
 // getter/setter 추가
	 public int getBalanceAfter() {
	     return balanceAfter;
	 }
	 public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public void setBalanceAfter(int balanceAfter) {
	     this.balanceAfter = balanceAfter;
	 }
	    
    // Getter/Setter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getSenderAccount() {
        return senderAccount;
    }
    public void setSenderAccount(int senderAccount) {
        this.senderAccount = senderAccount;
    }
    public int getReceiverAccount() {
        return receiverAccount;
    }
    public void setReceiverAccount(int receiverAccount) {
        this.receiverAccount = receiverAccount;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }
    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
}