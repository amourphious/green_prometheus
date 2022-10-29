package org.green.stack;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Resource {
    private final String name;
    private final String resourceType;
    private final String status;

    public Resource(@JsonProperty("name") String name,
                    @JsonProperty("resourceType") String resourceType,
                    @JsonProperty("status") String status) {
        this.name = name;
        this.resourceType = resourceType;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getStatus() {
        return status;
    }
}
