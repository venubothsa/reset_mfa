
package com.credit.one.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "href",
    "hints"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @JsonProperty("href")
    private String href;
    @JsonProperty("hints")
    private Hints__3 hints;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("href")
    public String getHref() {
        return href;
    }

    @JsonProperty("href")
    public void setHref(String href) {
        this.href = href;
    }

    @JsonProperty("hints")
    public Hints__3 getHints() {
        return hints;
    }

    @JsonProperty("hints")
    public void setHints(Hints__3 hints) {
        this.hints = hints;
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
