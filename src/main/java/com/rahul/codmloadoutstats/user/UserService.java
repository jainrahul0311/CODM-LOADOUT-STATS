package com.rahul.codmloadoutstats.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserService extends JpaRepository<UserDAO,Long> {
    public UserDAO findByUserIgn(String userIgn);
}
