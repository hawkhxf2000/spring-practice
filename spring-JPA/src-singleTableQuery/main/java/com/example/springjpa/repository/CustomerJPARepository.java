package com.example.springjpa.repository;

import org.springframework.data.repository.CrudRepository;

//set parameters to Long because primary key custID is Long, if custID is int, it should be set to Integer here
public interface CustomerJPARepository extends CrudRepository<Customer, Long> {

}
