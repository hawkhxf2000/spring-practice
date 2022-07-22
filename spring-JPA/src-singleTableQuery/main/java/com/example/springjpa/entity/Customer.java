package com.example.springjpa.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Data
@Table(name = "CUSTOMER")  //mapping to table customer in database
public class Customer {

    @Id                          //set custID as primary key
    @Column(name = "custID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //set custID as auto_increment
    private long custID;

    //set username not null, unique, length<50
    @Column(name="username", nullable = false, unique = true, length=50)
    private String username;

    //set password not null, length<50
    @Column(name="password", nullable = false, length = 50)
    private String password;

    //set email length<50
    @Column(name="email", nullable = true, length = 50)
    private String email;

    //set crete_time default current_timestamp
    @Column(name="create_time", columnDefinition = "timestamp default current_timestamp")
    private Timestamp createTime;

}
