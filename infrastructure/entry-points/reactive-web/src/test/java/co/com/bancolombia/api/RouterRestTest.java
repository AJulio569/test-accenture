package co.com.bancolombia.api;

import co.com.bancolombia.api.handler.FranchiseHandler;
import co.com.bancolombia.api.rest.RouterRest;
import co.com.bancolombia.model.franchise.model.Franchise;
import co.com.bancolombia.usecase.franchise.port.FranchiseServicePort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {RouterRest.class, FranchiseHandler.class})
@WebFluxTest
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @Mock
    private FranchiseServicePort franchiseServicePort;

    @InjectMocks
    private FranchiseHandler franchiseHandler;

    private final Franchise sampleFranchise = Franchise.builder()
            .id("1")
            .name("Franchise Montería")
            .build();

    @Test
    void testGetAllFranchises() {
        when(franchiseServicePort.getAllFranchise()).thenReturn(Flux.just(sampleFranchise));

        webTestClient.get()
                .uri("/api/franchise")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Franchise.class)
                .value(franchises -> assertThat(franchises).hasSize(1));
    }

    @Test
    void testGetFranchiseById() {
        when(franchiseServicePort.getFranchiseById("1")).thenReturn(Mono.just(sampleFranchise));

        webTestClient.get()
                .uri("/api/franchise/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Franchise.class)
                .value(franchise -> assertThat(franchise.getId()).isEqualTo("1"));
    }

    @Test
    void testCreateFranchise() {
        when(franchiseServicePort.createFranchise(any(Franchise.class))).thenReturn(Mono.just(sampleFranchise));

        webTestClient.post()
                .uri("/api/franchise")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                    {
                        "id": "1",
                        "name": "Franchise Montería"
                    }
                    """)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Franchise.class)
                .value(franchise -> assertThat(franchise.getName()).isEqualTo("Franchise Montería"));
    }
}
