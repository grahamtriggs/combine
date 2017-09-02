package combine.plugin.crossref.model.citeproc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CRAssertion {
    // Required
    @JsonProperty
    private String name;

    @JsonProperty
    private String value;

    // Optional
    @JsonProperty
    private String url;

    @JsonProperty
    private String explanation;

    @JsonProperty
    private String label;

    @JsonProperty
    private int order;

    @JsonProperty("assertion-group")
    private CRAssertionGroup assertionGroup;
}
