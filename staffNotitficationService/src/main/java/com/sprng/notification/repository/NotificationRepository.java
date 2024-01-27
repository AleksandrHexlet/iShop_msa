package com.sprng.notification.repository;

import com.sprng.notification.model.ExceptionMessageFullFail;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface NotificationRepository extends ReactiveMongoRepository<ExceptionMessageFullFail, Integer> {
}
