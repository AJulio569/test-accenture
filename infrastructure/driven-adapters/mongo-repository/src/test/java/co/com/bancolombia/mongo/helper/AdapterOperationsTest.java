package co.com.bancolombia.mongo.helper;

import co.com.bancolombia.model.franchise.model.Franchise;
import co.com.bancolombia.mongo.mapper.IFranchiseMapper;
import co.com.bancolombia.mongo.model.FranchiseEntity;
import co.com.bancolombia.mongo.repository.IFranchiseDBRepository;
import co.com.bancolombia.mongo.adapter.FranchiseRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.Example;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AdapterOperationsTest {

    @Mock
    private IFranchiseDBRepository repository;

    @Mock
    private IFranchiseMapper mapper;

    private FranchiseRepositoryAdapter adapter;

    private FranchiseEntity entity;
    private Franchise domain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        entity = FranchiseEntity.builder()
                .id("1")
                .name("Franchise Montería")
                .build();

        domain = Franchise.builder()
                .id("1")
                .name("Franchise Montería")
                .build();

        when(mapper.toDomain(entity)).thenReturn(domain);
        when(mapper.toEntity(domain)).thenReturn(entity);

        adapter = new FranchiseRepositoryAdapter(repository, mapper);
    }

    @Test
    void testSave() {
        when(repository.save(entity)).thenReturn(Mono.just(entity));

        StepVerifier.create(adapter.save(domain))
                .expectNext(domain)
                .verifyComplete();
    }


    @Test
    void testFindById() {
        when(repository.findById("1")).thenReturn(Mono.just(entity));

        StepVerifier.create(adapter.findById("1"))
                .expectNext(domain)
                .verifyComplete();
    }



    @Test
    void testFindAll() {
        when(repository.findAll()).thenReturn(Flux.just(entity));

        StepVerifier.create(adapter.findAll())
                .expectNext(domain)
                .verifyComplete();
    }


}
