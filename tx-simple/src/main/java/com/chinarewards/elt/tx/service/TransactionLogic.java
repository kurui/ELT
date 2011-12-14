package com.chinarewards.elt.tx.service;

import java.util.Date;

import com.chinarewards.elt.tx.domain.Transaction;
import com.chinarewards.elt.tx.exception.DuplicateUnitCodeException;

public interface TransactionLogic {

	public String deposit(Transaction transactoin, String accountId,
			String unitCode, double amount, Date transDate);

	public String withdraw(Transaction transactoin, String accountId,
			String unitCode, double amount, Date transDate);

	public double getBalance(String accountId, String unitCode);

	public String createNewAccount();

	public String createNewUnit(String name, String unitCode, double rate)
			throws DuplicateUnitCodeException;
}
