
package com.credit.one.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "allow"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hints__1 {

    @JsonProperty("allow")
    private List<String> allow = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("allow")
    public List<String> getAllow() {
        return allow;
    }

    @JsonProperty("allow")
    public void setAllow(List<String> allow) {
        this.allow = allow;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
