package combine.plugin.crossref.model.citeproc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CRContentDomain {
    // Required
    @JsonProperty("domain")
    private String[] domains;

    @JsonProperty("crossmark-restriction")
    private boolean crossmarkRestriction;
}
