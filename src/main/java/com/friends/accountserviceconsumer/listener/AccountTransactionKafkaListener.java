package com.friends.accountserviceconsumer.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.friends.accountserviceconsumer.beans.AccountTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AccountTransactionKafkaListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountTransactionKafkaListener.class);

    private final String accountTransactionTopicName = "accountTransaction";

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = accountTransactionTopicName, groupId = "account_transaction_group",
            containerFactory = "kafkaListenerFactory")
    public void consumeAccountTransaction(String accountTransactionStr) {
        LOGGER.info("Consumed message : " + accountTransactionStr);
        try {
            AccountTransaction accountTransaction = objectMapper.readValue(accountTransactionStr, AccountTransaction.class);
            LOGGER.info("Account Transaction Obj : " + accountTransaction);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
