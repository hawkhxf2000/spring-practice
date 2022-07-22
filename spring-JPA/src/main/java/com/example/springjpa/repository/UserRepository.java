package com.example.springjpa.repository;

import com.example.springjpa.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findUserByUid(long uid);
}
