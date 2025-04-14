package co.com.bancolombia.api.handler;

import co.com.bancolombia.api.mapper.IFranchiseMapperDto;
import co.com.bancolombia.api.model.request.FranchiseRequest;
import co.com.bancolombia.api.model.response.FranchiseResponse;
import co.com.bancolombia.usecase.franchise.port.FranchiseServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FranchiseHandler {
private final FranchiseServicePort servicePort;
private final IFranchiseMapperDto mapperDto;

    public Mono<ServerResponse> getAllFranchise(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(servicePort.getAllFranchise()
                        .map(mapperDto::toResponse), FranchiseResponse.class);
    }

    public Mono<ServerResponse> getFranchiseById(ServerRequest serverRequest) {
        return servicePort.getFranchiseById(serverRequest.pathVariable("id"))
                .map(mapperDto::toResponse)
                .flatMap(dto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(dto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getFranchiseByName(ServerRequest serverRequest) {
        return servicePort.getFranchiseByName(serverRequest.pathVariable("name"))
                .map(mapperDto::toResponse)
                .flatMap(dto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(dto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createFranchise(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(FranchiseRequest.class)
                .map(mapperDto::toDomain)
                .flatMap(servicePort::createFranchise)
                .map(mapperDto::toResponse)
                .flatMap(dto -> ServerResponse.ok().bodyValue(dto));
    }
}
