package combine.plugin.crossref.model.citeproc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CRFunder {
    // Required
    @JsonProperty
    private String name;

    // Optional
    @JsonProperty
    private String doi;

    @JsonProperty("award")
    private String[] awards;

    @JsonProperty("doi-asserted-by")
    private String doiAssertedBy;
}
