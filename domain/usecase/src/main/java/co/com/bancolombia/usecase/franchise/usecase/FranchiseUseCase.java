package co.com.bancolombia.usecase.franchise.usecase;

import co.com.bancolombia.model.franchise.gateways.FranchiseRepositoryPort;
import co.com.bancolombia.model.franchise.model.Branch;
import co.com.bancolombia.model.franchise.model.Franchise;
import co.com.bancolombia.model.franchise.model.Product;
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
        return repositoryPort.findAll()
                .switchIfEmpty(Flux.error( new FranchiseNotFoundException("No franchises found")));
    }

    @Override
    public Mono<Void> deleteFranchise(String id) {
        return FranchiseValidator.validateById(id)
                .flatMap(repositoryPort::deleteById)
                .switchIfEmpty(Mono.error( new FranchiseNotFoundException("No Franchise were found with that ID")));
    }

    @Override
    public Mono<Franchise> addBranchToFranchise(String franchiseId, Branch newBranch) {
        return FranchiseValidator.validateById(franchiseId)
                .flatMap(repositoryPort::findById)
                .flatMap(franchise ->{
                    boolean branchExists = franchise.getBranches().stream()
                            .anyMatch(branch -> branch.getName().equalsIgnoreCase(newBranch.getName()));

                    if (branchExists){
                        return  Mono.error(new FranchiseAlreadyExistsException("Branch already exists: " + newBranch.getName()));
                    }
                    franchise.getBranches().add(newBranch);
                    return repositoryPort.save(franchise);
                });
    }

    @Override
    public Mono<Product> addProductToBranch(String franchiseId, String branchName, Product product) {
        return FranchiseValidator.validateById(franchiseId)
                .flatMap(repositoryPort::findById)
                .flatMap(franchise -> {
                    Branch branch = franchise.getBranches().stream()
                            .filter(b -> b.getName().equalsIgnoreCase(branchName))
                            .findFirst()
                            .orElseThrow(() -> new FranchiseNotFoundException("Branch not found: " + branchName));
                    boolean productExists = branch.getProducts().stream()
                            .anyMatch(p -> p.getName().equalsIgnoreCase(product.getName()));
                    if (productExists){
                        return  Mono.error( new FranchiseAlreadyExistsException("Product already exists: " + product.getName()));
                    }
                    branch.getProducts().add(product);
                    return  repositoryPort.save(franchise)
                            .thenReturn(product);
                });
    }

    @Override
    public Flux<Branch> getBranchesByFranchise(String franchiseId) {
        return null;
    }

    @Override
    public Mono<Product> updateProductStock(String franchiseId, String branchName, String productName, int newStock) {
        return null;
    }
}
