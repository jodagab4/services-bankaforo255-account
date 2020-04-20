package com.app.aforo255.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.aforo255.account.model.entity.Account;
import com.app.aforo255.account.repository.AccountRepository;

@Service
public class AccountServiceImp  implements IAccountService{

	@Autowired
	private AccountRepository accountRepo;
	
	@Override
	public List<Account> findAll() {
		return (List<Account>) accountRepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Account findyId(Integer id) {
		return accountRepo.findById(id).orElse(null);
	}

	@Override
	public Account save(Account account) {
		return accountRepo.save(account);
	}

}
