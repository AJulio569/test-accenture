package co.com.bancolombia.usecase.franchise.port;

import co.com.bancolombia.model.franchise.model.Branch;
import co.com.bancolombia.model.franchise.model.Franchise;
import co.com.bancolombia.model.franchise.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseServicePort {
    Mono<Franchise> createFranchise(Franchise franchise);
    Mono<Franchise> getFranchiseById(String id);
    Mono<Franchise> getFranchiseByName(String name);
    Flux<Franchise> getAllFranchise();
    Mono<Void> deleteFranchise(String id);

    Mono<Franchise> addBranchToFranchise(String franchiseId, Branch newBranch);
    Mono<Product> addProductToBranch(String franchiseId, String branchName, Product product);
    Flux<Branch> getBranchesByFranchise(String franchiseId);
    Mono<Product> updateProductStock(String franchiseId, String branchName, String productName, int newStock);

}
