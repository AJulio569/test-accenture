package co.com.bancolombia.mongo.repository;

import co.com.bancolombia.mongo.model.FranchiseEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IFranchiseDBRepository extends ReactiveMongoRepository<FranchiseEntity, String>{
    Mono<FranchiseEntity> findByName(String name);
}
