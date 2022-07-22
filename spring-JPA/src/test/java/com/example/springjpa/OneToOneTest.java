package com.example.springjpa;

import com.example.springjpa.entity.Account;
import com.example.springjpa.entity.User;
import com.example.springjpa.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
public class OneToOneTest {

    @Autowired
    private UserRepository repository;

    @Test
//    @Transactional(readOnly = true)
    public void customerTest(){
        var account = new Account();
        account.setUsername("johndow");
        account.setPassword("123456");

        var user = new User();
        user.setCustName("John dow");
        user.setCustAddress("111 Saint Jean");
        user.setAccount(account);

        repository.save(user);
//        User record = repository.findUserByUid(2L);
//        System.out.println(record.getCustName());
    }
}
