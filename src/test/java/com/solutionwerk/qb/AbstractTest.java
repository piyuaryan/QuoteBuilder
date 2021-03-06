package com.solutionwerk.qb;

import com.solutionwerk.qb.util.RequestContext;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * The AbstractTest class is the parent of all JUnit test classes. This class
 * configures the test ApplicationContext and test runner environment.
 *
 * @author Piyush Ramavat
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public abstract class AbstractTest {

    /**
     * The Account.username attribute value used by default for unit tests.
     */
    public static final String USERNAME = "unittest";

    /**
     * The Logger instance for all classes in the unit test framework.
     */
    protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * Prepares the test class for execution.
     */
    protected void setUp() {
        RequestContext.setUsername(AbstractTest.USERNAME);
    }
}
