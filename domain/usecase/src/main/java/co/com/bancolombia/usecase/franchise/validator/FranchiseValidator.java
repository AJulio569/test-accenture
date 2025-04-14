package co.com.bancolombia.usecase.franchise.validator;


import co.com.bancolombia.model.franchise.model.Franchise;
import co.com.bancolombia.usecase.franchise.exception.InvalidFranchiseException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FranchiseValidator {
    public static Mono<Franchise> validate(Franchise franchise){
        if (franchise.getName() ==null || franchise.getName().isBlank()){
            return Mono.error(new InvalidFranchiseException("The Franchise name cannot be null or blank."));
        }
        if (!franchise.getName().matches("^[\\p{L}0-9 ]+$")){
            return  Mono.error( new InvalidFranchiseException("The Franchise  name can only contain letters, numbers, and spaces."));
        }
        return Mono.just(franchise);

    }

    public static Mono<String> validateByName(String name){
        if (name == null || name.isBlank() || !name.matches("^[\\p{L}0-9 ]+$")){
            return Mono.error(new InvalidFranchiseException("The Franchise name is invalid."));
        }
        return Mono.just(name);
    }

    public static Mono<String> validateById(String id){
        if (id == null || id.isBlank() || !id.matches("^[a-zA-Z0-9]+$")){
            return Mono.error(new InvalidFranchiseException("The Franchise ID is invalid."));
        }
        return Mono.just(id);
    }



}
