package ${package};

import io.wcm.qa.galenium.device.TestDevice;
import io.wcm.qa.galenium.testcase.AbstractGaleniumBase;

/**
 * Abstract base class for common functionality needed by multiple tests.
 */
public class AbstractExampleBase extends AbstractGaleniumBase {
    /**
     * @param testDevice test device to use for test
     */
    public AbstractExampleBase(TestDevice testDevice) {
        super(testDevice);
    }
}
