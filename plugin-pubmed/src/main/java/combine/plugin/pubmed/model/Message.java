package combine.plugin.pubmed.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {
    @JsonProperty
    private Header header;

    @JsonProperty("result")
    @JsonIgnoreProperties({ "uids" })
    private Map<String, PMPublication> works;
}
