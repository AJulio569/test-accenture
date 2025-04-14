package co.com.bancolombia.api.exception;


import co.com.bancolombia.api.utils.JsonUtils;
import co.com.bancolombia.model.franchise.model.ErrorResponse;
import co.com.bancolombia.usecase.franchise.exception.FranchiseAlreadyExistsException;
import co.com.bancolombia.usecase.franchise.exception.FranchiseNotFoundException;
import co.com.bancolombia.usecase.franchise.exception.InvalidFranchiseException;

import com.mongodb.MongoException;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static co.com.bancolombia.api.utils.ErrorCodeEnum.*;

@Component
@Order(-2)
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {


    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        var response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        HttpStatus status;
        ErrorResponse errorResponse;

        if (ex instanceof FranchiseAlreadyExistsException) {
            status = HttpStatus.BAD_REQUEST;
            errorResponse = ErrorResponse.builder()
                    .code(FRANCHISE_ALREADY_EXISTS.getCode())
                    .message(FRANCHISE_ALREADY_EXISTS.getMessage())
                    .details(List.of(ex.getMessage()))
                    .build();

        } else if (ex instanceof FranchiseNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            errorResponse = ErrorResponse.builder()
                    .code(FRANCHISE_NOT_FOUND.getCode())
                    .message(FRANCHISE_NOT_FOUND.getMessage())
                    .details(List.of(ex.getMessage()))
                    .build();

        } else if (ex instanceof MongoException || ex instanceof DataAccessException) {
            status = HttpStatus.SERVICE_UNAVAILABLE;
            errorResponse = ErrorResponse.builder()
                    .code(DATABASE_ERROR.getCode())
                    .message(DATABASE_ERROR.getMessage())
                    .details(List.of(ex.getMessage()))
                    .build();

        }  else if (ex instanceof InvalidFranchiseException) {
            status = HttpStatus.BAD_REQUEST;
            errorResponse = ErrorResponse.builder()
                    .code(INVALID_FRANCHISE.getCode())
                    .message(INVALID_FRANCHISE.getMessage())
                    .details(List.of(ex.getMessage()))
                    .build();
        }else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            errorResponse = ErrorResponse.builder()
                    .code(INTERNAL_ERROR.getCode())
                    .message(INTERNAL_ERROR.getMessage())
                    .details(List.of(ex.getMessage()))
                    .build();
        }


        response.setStatusCode(status);


        String json = JsonUtils.toJson(errorResponse);

        return response.writeWith(Mono.just(response.bufferFactory().wrap(json.getBytes(StandardCharsets.UTF_8))));

    }

    /*
    @ExceptionHandler(BusinessException.class)
    public Mono<ResponseEntity<String>> handleBusinessException(BusinessException ex) {
        return Mono.just(ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage()));
    }

     */

    }
