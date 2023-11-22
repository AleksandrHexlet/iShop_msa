package com.sprng.users.repository;

import com.sprng.library.entity.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedBackRepository extends JpaRepository<FeedBack, Integer> {
    //в дженерике сущность с которой работает репозиторий и id сущности


}
