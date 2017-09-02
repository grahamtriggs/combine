package combine.plugin.crossref.model.citeproc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CRPublication extends CRPublicationBase {
    @JsonProperty
    private String title;

    @JsonProperty("container-title")
    private String containerTitle;

    @JsonProperty("container-title-short")
    private String containerTitleShort;     // Array?

    @JsonProperty("update-to")
    private CRUpdate updateTo;
}
