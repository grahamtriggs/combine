package combine.transformers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LeadingNumericsTransformerTest {
    @Test
    public void testLeadingNumerics() {
        LeadingNumericsTransformer transformer = new LeadingNumericsTransformer();

        assertTrue("1".equals(transformer.transform("1. This is a test.")));
        assertTrue("".equals(transformer.transform("Another 1 test.")));
        assertTrue("1".equals(transformer.transform("    1. Spaces")));
        assertTrue("1.1.1".equals(transformer.transform("1.1.1 Hierarchy")));
        assertTrue("1,2,3".equals(transformer.transform("1,2,3 Commas")));
    }
}
