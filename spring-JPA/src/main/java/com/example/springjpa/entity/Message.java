package com.example.springjpa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "message")
public class Message {

    @Id
    @Column(name = "mid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long mid;

    @Column(name = "content")
    private String content;

    @Column(name="user_id")
    private long userId;

    public Message(String content) {
        this.content = content;
    }
}
