package com.rahul.codmloadoutstats.content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadoutService extends JpaRepository<LoadoutDAO,Long> {
}
