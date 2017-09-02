package combine.plugin.crossref.model.citeproc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CRLicense {
    // Required
    @JsonProperty
    private String url;

    @JsonProperty("content-version")
    private String contentVersion;

    @JsonProperty("delay-in-days")
    private int delayInDays;

    @JsonProperty
    private CRPartialDate start;
}
