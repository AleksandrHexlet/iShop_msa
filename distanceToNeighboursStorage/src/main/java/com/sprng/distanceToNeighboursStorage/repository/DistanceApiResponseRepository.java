package com.sprng.distanceToNeighboursStorage.repository;

import com.sprng.distanceToNeighboursStorage.model.CityStorage;
import com.sprng.distanceToNeighboursStorage.model.DistanceApiResponse;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface DistanceApiResponseRepository extends ReactiveMongoRepository<DistanceApiResponse,Integer> {
}
