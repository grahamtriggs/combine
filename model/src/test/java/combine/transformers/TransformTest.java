package combine.transformers;

import org.junit.jupiter.api.Test;

public class TransformTest {
    @Test
    public void testMakeStamp() {
        String str;
        //str = Transform.makeApproximation("1. This is a test.");
        str = Transform.makeApproximation("My presentation ix");
        str = Transform.makeApproximation("My 1998/01/01,01-02-1998,9/12/2003 presentation ix");
        str = null;
    }
}
