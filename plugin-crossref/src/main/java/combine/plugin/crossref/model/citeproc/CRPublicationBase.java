package combine.plugin.crossref.model.citeproc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class CRPublicationBase {
    // Required
    @JsonProperty
    private String publisher;

    @JsonProperty("reference-count")
    private Integer referenceCount;         // Deprecated

    @JsonProperty("references-count")
    private Integer referencesCount;

    @JsonProperty("is-referenced-by-count")
    private Integer isReferencedByCount;

    @JsonProperty
    private String source;

    @JsonProperty
    private String prefix;

    @JsonProperty("DOI")
    private String doi;

    @JsonProperty("URL")
    private String url;

    @JsonProperty
    private String member;

    @JsonProperty
    private String type;

    @JsonProperty
    private CRFullDate created;

    @JsonProperty
    private CRFullDate deposited;

    @JsonProperty
    private CRFullDate indexed;

    @JsonProperty
    private CRPartialDate issued;

    // Optional
    @JsonProperty("original-title")
    private String[] originalTitles;

    @JsonProperty("short-title")
    private String[] shortTitles;

    @JsonProperty("abstract")
    public String abstractText;

    @JsonProperty
    private CRPartialDate posted;

    @JsonProperty
    private CRPartialDate accepted;

    @JsonProperty("subtitle")
    private String[] subtitles;

    @JsonProperty("group-title")
    private String groupTitle;

    @JsonProperty
    private String issue;

    @JsonProperty
    private String volume;

    @JsonProperty
    private String page;

    @JsonProperty("article-number")
    private String articleNumber;

    @JsonProperty("published-print")
    private CRPartialDate publishedPrint;

    @JsonProperty("published-online")
    private CRPartialDate publishedOnline;

    @JsonProperty("subject")
    private String[] subjects;

    @JsonProperty("ISSN")
    private String[] issns;

    @JsonProperty("issn-type")
    private CRTypedValue[] issnTypes;

    @JsonProperty("ISBN")
    private String[] isbns;

    @JsonProperty("archive")
    private String[] archives;

    @JsonProperty("license")
    private CRLicense[] licenses;

    @JsonProperty("funder")
    private CRFunder[] funders;

    @JsonProperty("assertion")
    private CRAssertion[] assertions;

    @JsonProperty("author")
    private CRContributor[] authors;

    @JsonProperty("editor")
    private CRContributor[] editors;

    @JsonProperty("chair")
    private CRContributor[] chairs;

    @JsonProperty("translator")
    private CRContributor[] translators;

    @JsonProperty("update-policy")
    private String updatePolicy;

    @JsonProperty("link")
    private CRResourceLink[] links;

    @JsonProperty("clinical-trial-number")
    private CRClinicalTrialNumber[] clinicalTrialNumbers;

    @JsonProperty("alternative-id")
    private String[] alternativeIds;

    @JsonProperty("reference")
    private CRPubReference[] references;

    @JsonProperty("content-domain")
    private CRContentDomain contentDomain;

    @JsonProperty
    private Double score;

    @JsonProperty
    private Map<String, CRRelation[]> relation;
}
