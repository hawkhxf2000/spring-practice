package com.example.springjpa;

import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootTest
public class JPAPageTest {

    @Autowired
    private CustomerPageRepository repository;

    @Test
    //pagination query
    public void findAllPaging(){
        Page<Customer> list = repository.findAll(PageRequest.of(0,5));
        System.out.println(list.getContent());  //the contents of current page
        System.out.println(list.getSize());     //the item number of each page
        System.out.println(list.getTotalElements());  //the total number of items
        System.out.println(list.getTotalPages());    //total page
    }

    @Test
    public void findAllSorting(){
        List<Customer> sortingListAsc = (List<Customer>) repository.findAll(Sort.by("custID").ascending());
        List<Customer> sortingListDesc = (List<Customer>) repository.findAll(Sort.by("username").descending());
        //can use and(Sort.by()) to use multi sorting condition.
        System.out.println(sortingListAsc);
        System.out.println(sortingListDesc);
    }
}
