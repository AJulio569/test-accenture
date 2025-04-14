package co.com.bancolombia.usecase.franchise.usecase;

import co.com.bancolombia.model.franchise.gateways.FranchiseRepositoryPort;
import co.com.bancolombia.model.franchise.model.Franchise;
import co.com.bancolombia.usecase.franchise.exception.FranchiseAlreadyExistsException;
import co.com.bancolombia.usecase.franchise.exception.FranchiseNotFoundException;
import co.com.bancolombia.usecase.franchise.port.FranchiseServicePort;
import co.com.bancolombia.usecase.franchise.validator.FranchiseValidator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class FranchiseUseCase implements FranchiseServicePort {
    private final FranchiseRepositoryPort repositoryPort;

    public FranchiseUseCase(FranchiseRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Mono<Franchise> createFranchise(Franchise franchise) {
        return FranchiseValidator.validate(franchise)
                .flatMap(validateFranchise ->
                        repositoryPort.findByName(validateFranchise.getName())
                                .flatMap(existing ->
                                        Mono.<Franchise>error(
                                                new FranchiseAlreadyExistsException("There is already a Franchise with this name.")))
                                .switchIfEmpty(repositoryPort.save(validateFranchise)));
    }

    @Override
    public Mono<Franchise> getFranchiseById(String id) {
        return FranchiseValidator.validateById(id)
                .flatMap(repositoryPort::findById)
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException("No Franchise were found with that ID")));
    }

    @Override
    public Mono<Franchise> getFranchiseByName(String name) {
        return FranchiseValidator.validateByName(name)
                .flatMap(repositoryPort::findByName)
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException("No Franchise were found with that name")));
    }

    @Override
    public Flux<Franchise> getAllFranchise() {
        return repositoryPort.findAll();
    }
}
