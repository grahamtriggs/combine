package combine.plugin.crossref.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Query {
    @JsonProperty("start-index")
    private Long startIndex;

    @JsonProperty("search-terms")
    private String searchTerms;
}
