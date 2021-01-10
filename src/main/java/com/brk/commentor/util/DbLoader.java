package com.brk.commentor.util;

import com.brk.commentor.model.Comment;
import com.brk.commentor.model.User;
import com.brk.commentor.repository.CommentRepository;
import com.brk.commentor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class DbLoader {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public DbLoader(UserRepository userRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @PostConstruct
    public void init() {
        User brk = new User();
        brk.setName("ahmet");
        brk.setPassword("ahmet");
        userRepository.save(brk);

        User memo = new User();
        memo.setName("mehmet");
        memo.setPassword("mehmet");
        userRepository.save(memo);

        Comment omg = new Comment();
        omg.setContent("omg");
        omg.setUser(brk);

        Comment rofl = new Comment();
        rofl.setContent("rofl");
        rofl.setUser(memo);

        commentRepository.save(omg);
        commentRepository.save(rofl);
    }
}
