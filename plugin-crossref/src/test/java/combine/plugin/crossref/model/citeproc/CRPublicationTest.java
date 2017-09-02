package combine.plugin.crossref.model.citeproc;

import com.fasterxml.jackson.databind.ObjectMapper;
import combine.plugin.crossref.model.WorkList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class CRPublicationTest {

    @Test
    public void testCiteproc() {
        URL citeprocResource = getClass().getResource("/citeproc.json");
        URL worklistResource = getClass().getResource("/worklist2.json");

        ObjectMapper mapper = new ObjectMapper();
        try {
            CRPublication publication = mapper.readValue(citeprocResource, CRPublication.class);
            WorkList works = mapper.readValue(worklistResource, WorkList.class);

//            String json = mapper.writeValueAsString(publication);
//            publication = null;
//            works = null;
            boolean b = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
