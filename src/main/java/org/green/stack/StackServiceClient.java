package org.green.stack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public class StackServiceClient {
    public static final TypeReference<List<Stack>> STACKS_TYPE_REF = new TypeReference<>() {
    };
    private final HttpClient httpClient;
    private final URI stackEndpoint;
    private final ObjectMapper objectMapper;

    private static final String DUMMY = "[{\n" +
            "    \"name\": \"stack1\",\n" +
            "    \"region\": \"useast\",\n" +
            "    \"id\": 1,\n" +
            "    \"resources\": [\n" +
            "      {\n" +
            "        \"name\": \"resource1\",\n" +
            "        \"resourceType\": \"resourceType1\",\n" +
            "        \"status\": \"Running\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"name\": \"resource2\",\n" +
            "        \"resourceType\": \"resourceType2\",\n" +
            "        \"status\": \"Running\"\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"stack1\",\n" +
            "    \"region\": \"uswest\",\n" +
            "    \"id\": 1,\n" +
            "    \"resources\": [\n" +
            "      {\n" +
            "        \"name\": \"resource3\",\n" +
            "        \"resourceType\": \"resourceType1\",\n" +
            "        \"status\": \"Running\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"name\": \"resource4\",\n" +
            "        \"resourceType\": \"resourceType2\",\n" +
            "        \"status\": \"Running\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }]";

    @Inject
    public StackServiceClient(HttpClient httpClient,
                              @Named("stackServiceEndpoint") URI stackEndpoint,
                              ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.stackEndpoint = stackEndpoint;
        this.objectMapper = objectMapper;
    }

    public CompletableFuture<List<Stack>> getStackInfo() {
        return httpClient.sendAsync(HttpRequest.newBuilder().GET().uri(stackEndpoint).build(),
                HttpResponse.BodyHandlers.ofInputStream()).handle(this::handle);
    }

    private List<Stack> handle(HttpResponse<InputStream> stack, Throwable throwable) {
        try {
            List<Stack> dummyStack = objectMapper.readValue(DUMMY, STACKS_TYPE_REF);
            if (throwable == null) {
                try {
                    return objectMapper.readValue(stack.body(), STACKS_TYPE_REF);
                } catch (Exception ex) {
                    System.out.println("Error converting explore response" + ex.getMessage());
                }
            }
            return dummyStack;
        } catch (JsonProcessingException e) {
            return Collections.emptyList();
        }
    }
}
