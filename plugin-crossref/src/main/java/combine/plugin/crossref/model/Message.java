package combine.plugin.crossref.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import combine.plugin.crossref.model.citeproc.CRPublicationBase;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {
    @JsonProperty
    private Map<String, Facet> facets;

    @JsonProperty("next-cursor")
    private String nextCursor;

    @JsonProperty("total-results")
    private Long totalResults;

    @JsonProperty
    private WorkItem[] items;

    @JsonProperty("items-per-page")
    private Integer itemsPerPage;

    @JsonProperty
    private Query query;

}
