package com.brk.commentor.repository;

import com.brk.commentor.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository  extends CrudRepository<User,Long> {
    User findByName(String name);
}
