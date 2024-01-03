package com.sprng.distanceToNeighboursStorage.repository;

import com.sprng.distanceToNeighboursStorage.model.CityStorage;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CityStorageRepository extends ReactiveMongoRepository<CityStorage,String> {
}
