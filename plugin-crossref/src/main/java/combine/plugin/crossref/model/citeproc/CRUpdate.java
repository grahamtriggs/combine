package combine.plugin.crossref.model.citeproc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CRUpdate {
    // Required
    @JsonProperty
    private CRPartialDate updated;

    @JsonProperty("DOI")
    private String doi;

    @JsonProperty
    private String type;

    // Optional
    @JsonProperty
    private String label;
}
