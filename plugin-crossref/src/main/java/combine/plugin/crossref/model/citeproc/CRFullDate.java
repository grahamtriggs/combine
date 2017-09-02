package combine.plugin.crossref.model.citeproc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CRFullDate extends CRPartialDate {
    // Required
//    private int[][] dateParts;
    @JsonProperty("date-time")
    private String dateTime;

    @JsonProperty
    private long timestamp;
}

