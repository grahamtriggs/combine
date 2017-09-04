package combine.plugin.pubmed.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;

public class PMPublicationTest {
    @Test
    public void testPubMed() {
        URL pubmedResource = getClass().getResource("/pubmed.json");

        ObjectMapper mapper = new ObjectMapper();
        try {
            Message message = mapper.readValue(pubmedResource, Message.class);

//            String json = mapper.writeValueAsString(publication);
//            publication = null;
//            works = null;
            boolean b = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
