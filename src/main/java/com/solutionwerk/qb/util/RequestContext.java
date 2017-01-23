package com.solutionwerk.qb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The RequestContext facilitates the storage of information for the duration of
 * a single request (or web service transaction).
 * <p/>
 * RequestContext attributes are stored in ThreadLocal objects.
 *
 * @author Piyush Ramavat
 */
public class RequestContext {

    /**
     * The Logger for this class.
     */
    private static Logger LOGGER = LoggerFactory.getLogger(RequestContext.class);

    /**
     * ThreadLocal storage of username Strings.
     */
    private static ThreadLocal<String> userName = new ThreadLocal<>();

    private RequestContext() {

    }

    /**
     * Get the username for the current thread.
     *
     * @return A String username.
     */
    public static String getUsername() {
        return userName.get();
    }

    /**
     * Set the userName for the current thread.
     *
     * @param userName A String userName.
     */
    public static void setUsername(String userName) {
        RequestContext.userName.set(userName);
        LOGGER.debug("RequestContext added userName {} to current thread", userName);
    }

    /**
     * Initialize the ThreadLocal attributes for the current thread.
     */
    public static void init() {
        userName.set(null);
    }

}
