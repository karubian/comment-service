package com.brk.commentor.exception;

import com.brk.commentor.model.Comment;

public class CommentNotFoundException extends RuntimeException{

    public CommentNotFoundException(Long id) {
        super("Could not find comment" + id);
    }



}
