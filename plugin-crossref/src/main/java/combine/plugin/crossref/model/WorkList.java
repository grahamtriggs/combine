package combine.plugin.crossref.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkList {
    @JsonProperty
    private String status;

    @JsonProperty("message-type")
    private String messageType;

    @JsonProperty("message-version")
    private String messageVersion;

    @JsonProperty
    private Message message;
}
