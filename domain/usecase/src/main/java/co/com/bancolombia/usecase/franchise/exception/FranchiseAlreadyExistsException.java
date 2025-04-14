package co.com.bancolombia.usecase.franchise.exception;

public class FranchiseAlreadyExistsException extends RuntimeException {
    public FranchiseAlreadyExistsException(String message) {
        super(message);
    }

}
