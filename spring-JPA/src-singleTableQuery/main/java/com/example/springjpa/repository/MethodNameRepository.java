package com.example.springjpa.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MethodNameRepository extends PagingAndSortingRepository<Customer,Long> {

    List<Customer> findCustomerByUsername(String username);

    List<Customer> findCustomersByUsernameIsLikeIgnoreCase(String username);
}
