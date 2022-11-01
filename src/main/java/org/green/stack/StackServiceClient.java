package org.green.stack;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.inject.Named;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class StackServiceClient {
    public static final TypeReference<List<Stack>> STACKS_TYPE_REF = new TypeReference<>() {
    };
    private final HttpClient httpClient;
    private final URI stackEndpoint;
    private final ObjectMapper objectMapper;

    private final List<Stack> dummy;

    @Inject
    public StackServiceClient(HttpClient httpClient,
                              @Named("stackServiceEndpoint") URI stackEndpoint,
                              @Named("dummyInfo") List<Stack> dummy) {
        this.httpClient = httpClient;
        this.stackEndpoint = stackEndpoint;
        this.objectMapper = new ObjectMapper();
        this.dummy = dummy;
    }

    public CompletableFuture<List<Stack>> getStackInfo() {
        return httpClient.sendAsync(HttpRequest.newBuilder().GET().uri(stackEndpoint).build(),
                HttpResponse.BodyHandlers.ofInputStream()).handle(this::handle);
    }

    private List<Stack> handle(HttpResponse<InputStream> stack, Throwable throwable) {
    	if (throwable == null) {
            try {
                return objectMapper.readValue(stack.body(), STACKS_TYPE_REF);
            } catch (Exception ex) {
                System.out.println("Error converting explore response" + ex.getMessage());
            }
        }
        return dummy;
    }
}
