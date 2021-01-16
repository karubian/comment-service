package com.brk.commentor.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
@Data
@Entity
@ToString
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long comment_id;

    private String title;

    private String content;

    public Comment(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Comment() {

    }
}
