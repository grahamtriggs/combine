package combine.plugin.crossref.model.citeproc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CRContributor {
    // Required
    @JsonProperty("family")
    private String familyName;

    // Optional
    @JsonProperty("given")
    private String givenName;

    @JsonProperty("ORCID")
    private String orcid;

    @JsonProperty("authenticated-orcid")
    private boolean authenticatedOrcid;

    @JsonProperty("affiliation")
    private CRAffiliation[] affiliations;
}
