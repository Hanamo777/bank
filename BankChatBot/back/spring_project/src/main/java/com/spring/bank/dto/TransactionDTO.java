package com.spring.bank.dto;

import java.time.LocalDateTime;

public class TransactionDTO {
    private int id;
    private int senderAccount;
    private int receiverAccount;
    private int amount;
    private LocalDateTime transactionTime;
    private int senderBalanceAfter;
    private int receiverBalanceAfter;
    private String senderName;
    private String receiverName;
    private int fee;
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
	public int getSenderBalanceAfter() {
		return senderBalanceAfter;
	}
	public void setSenderBalanceAfter(int senderBalanceAfter) {
		this.senderBalanceAfter = senderBalanceAfter;
	}
	public int getReceiverBalanceAfter() {
		return receiverBalanceAfter;
	}
	public void setReceiverBalanceAfter(int receiverBalanceAfter) {
		this.receiverBalanceAfter = receiverBalanceAfter;
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
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	
}