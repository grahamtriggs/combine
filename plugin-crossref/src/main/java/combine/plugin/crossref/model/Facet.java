package combine.plugin.crossref.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Facet {
    @JsonProperty("value-count")
    private Long valueCount;

    @JsonProperty
    private Map<String, Long> values;
}
