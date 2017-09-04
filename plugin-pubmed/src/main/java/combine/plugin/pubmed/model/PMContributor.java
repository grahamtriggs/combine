package combine.plugin.pubmed.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PMContributor {
    @JsonProperty
    private String name;

    @JsonProperty
    private String authtype;

    @JsonProperty
    private String clusterid;
}
