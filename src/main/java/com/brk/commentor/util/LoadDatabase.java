package com.brk.commentor.util;

import com.brk.commentor.model.Comment;
import com.brk.commentor.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase
{
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CommentRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Comment("baslik1","content1")));
            log.info("Preloading " + repository.save(new Comment("baslik2","content2")));
        };
    }
}
