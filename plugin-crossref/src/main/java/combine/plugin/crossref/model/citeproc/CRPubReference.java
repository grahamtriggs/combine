package combine.plugin.crossref.model.citeproc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CRPubReference {
    // Required
    @JsonProperty
    private String key;

    // Optional
    @JsonProperty("DOI")
    private String doi;

    @JsonProperty("doi-asserted-by")
    private String doiAssertedBy;

    @JsonProperty
    private String issue;

    @JsonProperty("first-page")
    private String firstPage;

    @JsonProperty
    private String volume;

    @JsonProperty
    private String edition;

    @JsonProperty
    private String component;

    @JsonProperty("standard-designator")
    private String standardDesignator;

    @JsonProperty("standards-body")
    private String standardsBody;

    @JsonProperty
    private String author;

    @JsonProperty
    private String year;

    @JsonProperty
    private String unstructured;

    @JsonProperty("journal-title")
    private String journalTitle;

    @JsonProperty("article-title")
    private String articleTtile;

    @JsonProperty("series-title")
    private String seriesTitle;

    @JsonProperty("volume-title")
    private String volumeTitle;

    @JsonProperty("ISSN")
    private String issn;

    @JsonProperty("issn-type")
    private String issnType;

    @JsonProperty("ISBN")
    private String isbn;

    @JsonProperty("isbn-type")
    private String isbnType;
}
