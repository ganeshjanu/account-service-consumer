package com.friends.accountserviceconsumer.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.friends.accountserviceconsumer.beans.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AccountKafkaListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountKafkaListener.class);

    private final String accountTopicName = "account";

    @Autowired
    private ObjectMapper objectMapper;


    @KafkaListener(topics = accountTopicName, groupId = "account_group",
            containerFactory = "kafkaListenerFactory")
    public void consumeAccount(String accountStr) {
        LOGGER.info("Consumed message : " + accountStr);
        try {
            Account account = objectMapper.readValue(accountStr, Account.class);
            LOGGER.info("Account Obj : " + account);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
