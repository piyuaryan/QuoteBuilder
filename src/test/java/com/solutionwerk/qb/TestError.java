package com.solutionwerk.qb;

import java.io.Serializable;

/**
 * Error Model object to parse fro Json String
 *
 * @author Piyush Ramavat
 */
public class TestError implements Serializable {

    private String error;

    //TODO: Add @SerialisedName
    private String errorDescription;

    public String getError() {
        return error;
    }

    public String getDescription() {
        return errorDescription;
    }
}
