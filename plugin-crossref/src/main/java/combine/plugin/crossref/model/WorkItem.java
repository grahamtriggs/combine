package combine.plugin.crossref.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import combine.plugin.crossref.model.citeproc.CRPublicationBase;
import combine.plugin.crossref.model.citeproc.CRUpdate;

public class WorkItem extends CRPublicationBase {
    @JsonProperty
    private String[] title;

    @JsonProperty("container-title")
    private String[] containerTitle;

    @JsonProperty("short-container-title")
    private String[] containerTitleShort;

    @JsonProperty("update-to")
    private CRUpdate[] updateTo;

//    @JsonProperty
    /*
        "relation": {
          "cites": []
        },

     */
}
