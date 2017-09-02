package combine.plugin.crossref.model.citeproc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CRClinicalTrialNumber {
    // Required
    @JsonProperty("clinical-trial-number")
    private String clinicalTrialNumber;

    @JsonProperty
    private String registry;

    // Optional
    @JsonProperty
    private String type;
}

