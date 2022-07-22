package com.example.springjpa.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerPageRepository extends PagingAndSortingRepository<Customer, Long> {
}
