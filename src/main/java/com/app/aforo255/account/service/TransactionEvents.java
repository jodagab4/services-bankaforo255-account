package com.app.aforo255.account.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.aforo255.account.model.entity.Account;
import com.app.aforo255.account.model.entity.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TransactionEvents {

	private Logger log = LoggerFactory.getLogger(TransactionEvents.class);
	
	@Autowired
	private IAccountService repository;
	
	@Autowired
	ObjectMapper objectMapper;
	
	public void processTransacctionEvent(ConsumerRecord<Integer, String> consumerRecord) 
			throws JsonMappingException, JsonProcessingException {
		Account objAccount = new Account();
		Transaction transactionEvent = objectMapper.readValue(consumerRecord.value(), Transaction.class);
		log.info("transactionEvent : {} ", transactionEvent.getAccountId());
		objAccount=repository.findyId(transactionEvent.getAccountId());
		log.info("get Amount : {} ", transactionEvent.getAmount());
		
		double newmonto=0;
		switch (transactionEvent.getType()) {
		case "deposito":
			newmonto=objAccount.getTotalAmount()+transactionEvent.getAmount();
			break;
		case "retiro":
			newmonto=objAccount.getTotalAmount()-transactionEvent.getAmount();
			break;					
		default:
			log.info("Invalid Library Event Type");
		}
		log.info("set new amount : {} ", newmonto);
		objAccount.setTotalAmount(newmonto);
		repository.save(objAccount);
	}
	
}
