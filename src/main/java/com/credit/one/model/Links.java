
package com.credit.one.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "activate",
    "resend",
    "self",
    "user"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Links {

    @JsonProperty("activate")
    private Activate activate;
    @JsonProperty("resend")
    private List<Resend> resend = null;
    @JsonProperty("self")
    private Self self;
    @JsonProperty("user")
    private User user;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("activate")
    public Activate getActivate() {
        return activate;
    }

    @JsonProperty("activate")
    public void setActivate(Activate activate) {
        this.activate = activate;
    }

    @JsonProperty("resend")
    public List<Resend> getResend() {
        return resend;
    }

    @JsonProperty("resend")
    public void setResend(List<Resend> resend) {
        this.resend = resend;
    }

    @JsonProperty("self")
    public Self getSelf() {
        return self;
    }

    @JsonProperty("self")
    public void setSelf(Self self) {
        this.self = self;
    }

    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(User user) {
        this.user = user;
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
