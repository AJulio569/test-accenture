package co.com.bancolombia.usecase.franchise.port;

import co.com.bancolombia.model.franchise.model.Franchise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseServicePort {
    Mono<Franchise> createFranchise(Franchise franchise);
    Mono<Franchise> getFranchiseById(String id);
    Mono<Franchise> getFranchiseByName(String name);
    Flux<Franchise> getAllFranchise();
}
