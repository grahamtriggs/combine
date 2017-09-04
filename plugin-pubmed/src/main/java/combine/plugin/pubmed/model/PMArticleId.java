package combine.plugin.pubmed.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PMArticleId {
    @JsonProperty
    private String idtype;

    @JsonProperty
    private Integer idtypen;

    @JsonProperty
    private String value;
}
