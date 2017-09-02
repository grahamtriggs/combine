package combine.plugin.crossref.model.citeproc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CRRelation {
    // Required
    @JsonProperty("id-type")
    private String idType;

    @JsonProperty
    private String id;

    @JsonProperty("asserted-by")
    private String assertedBy;
}
