package com.example.demo.service;

import java.util.List;

import com.example.demo.exception.AccountNotFoundException;
import com.example.demo.model.Account;

public interface AccountService {
	Account createAccount(Account account);

	List<Account> getAllAccounts();
	Account getAccountByaccountNumber(String accountNumber) throws AccountNotFoundException;
	Account updateAccountByAccountNumber(String accountNumber,Account account)throws com.example.demo.exception.AccountNotFoundException;
	public void deleteAccountByAccountNumber(String accountNumber)	throws	AccountNotFoundException;
	Account	getAccountByEmail(String email)	throws	AccountNotFoundException;
	double withdrawMoney(String accountId, Account account) throws AccountNotFoundException;
	void deposit(String accountNumber,Account account)	throws AccountNotFoundException;
}