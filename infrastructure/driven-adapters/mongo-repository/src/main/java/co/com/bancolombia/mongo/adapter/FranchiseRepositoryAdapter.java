package co.com.bancolombia.mongo.adapter;

import co.com.bancolombia.model.franchise.gateways.FranchiseRepositoryPort;
import co.com.bancolombia.model.franchise.model.Franchise;
import co.com.bancolombia.mongo.mapper.IFranchiseMapper;
import co.com.bancolombia.mongo.repository.IFranchiseDBRepository;
import co.com.bancolombia.mongo.helper.AdapterOperations;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class FranchiseRepositoryAdapter implements FranchiseRepositoryPort {
    private final IFranchiseDBRepository repository;
    private final IFranchiseMapper mapper;


    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return repository.save(mapper.toEntity(franchise))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Franchise> findById(String id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Franchise> findByName(String name) {
        return repository.findByName(name)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Franchise> findAll() {
        return repository.findAll()
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }
}
