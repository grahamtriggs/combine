package combine.plugin.crossref.model.citeproc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CRResourceLink {
    // Required
    @JsonProperty("URL")
    private String url;

    @JsonProperty("content-version")
    private String contentVersion;

    @JsonProperty("intended-application")
    private String intendedApplication;

    // Optional
    @JsonProperty("content-type")
    private String contentType;
}
