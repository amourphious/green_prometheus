package org.green.stack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stack {
    private final String name;
    private final String region;
    private final Long id;
    private final List<Resource> resources;

    public Stack(@JsonProperty("name") String name,
                 @JsonProperty("region") String region,
                 @JsonProperty("id") Long id,
                 @JsonProperty("resources") List<Resource> resources) {
        this.name = name;
        this.region = region;
        this.id = id;
        this.resources = resources;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public Long getId() {
        return id;
    }

    public List<Resource> getResources() {
        return resources;
    }
}
