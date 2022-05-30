package com.example.springshiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springshiro.entity.Account;
import com.example.springshiro.service.AccountService;
import com.example.springshiro.mapper.AccountMapper;
import org.springframework.stereotype.Service;

/**
* @author hawkh
* @description 针对表【account】的数据库操作Service实现
* @createDate 2022-05-27 10:43:48
*/
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account>
    implements AccountService{

}




