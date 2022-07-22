package com.example.springjpa;

import com.example.springjpa.entity.Account;
import com.example.springjpa.entity.Message;
import com.example.springjpa.entity.User;
import com.example.springjpa.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class OneToManyTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void createEntityTest(){
        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message("你好呀！"));
        messageList.add(new Message("吃了吗？"));

        Account account = new Account();
        account.setUsername("jeanbull");
        account.setPassword("password");

        User user = new User();
        user.setCustName("Jean Bull");
        user.setCustAddress("111 saint-charles");
        user.setAccount(account);
        user.setMessages(messageList);

        repository.save(user);
    }
}
