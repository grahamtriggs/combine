package combine.plugin.pubmed.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PMPublication {
    @JsonProperty
    private String uid;

    @JsonProperty
    private String pubdate;

    @JsonProperty
    private String epubdate;

    @JsonProperty
    private String source;

    @JsonProperty
    private PMContributor[] authors;

    @JsonProperty
    private String lastauthor;

    @JsonProperty
    private String title;

    @JsonProperty
    private String sorttitle;

    @JsonProperty
    private String volume;

    @JsonProperty
    private String issue;

    @JsonProperty
    private String pages;

    @JsonProperty("lang")
    private String[] languages;

    @JsonProperty
    private String nlmuniqueid;

    @JsonProperty
    private String issn;

    @JsonProperty
    private String eissn;

    @JsonProperty("pubtype")
    private String[] pubtypes;

    @JsonProperty
    private String recordstatus;

    @JsonProperty
    private String pubstatus;

    @JsonProperty
    private PMArticleId[] articleids;

    @JsonProperty
    private PMHistory[] history;

    // references - array

    @JsonProperty
    private String[] attributes;

    @JsonProperty
    private String pmcrefcount;

    @JsonProperty
    private String fulljournalname;

    @JsonProperty
    private String elocationid;

    @JsonProperty
    private String doctype;

    // srccontriblist - array

    @JsonProperty
    private String booktitle;

    @JsonProperty
    private String medium;

    @JsonProperty
    private String edition;

    @JsonProperty
    private String publisherlocation;

    @JsonProperty
    private String publishername;

    @JsonProperty
    private String srcdate;

    @JsonProperty
    private String reportnumber;

    @JsonProperty
    private String availablefromurl;

    @JsonProperty
    private String locationlabel;

    // doccontriblist - array

    @JsonProperty
    private String docdate;

    @JsonProperty
    private String bookname;

    @JsonProperty
    private String chapter;

    @JsonProperty
    private String sortpubdate;

    @JsonProperty
    private String sortfirstauthor;

    @JsonProperty
    private String vernaculartitle;
}
