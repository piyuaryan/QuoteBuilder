package com.solutionwerk.qb.util;

import org.springframework.beans.BeanUtils;

public class Util {

    public static void copyProperties(Object source, Object target, boolean ignoreTransactionEntityFields) {
        if (ignoreTransactionEntityFields) {
            BeanUtils.copyProperties(source, target, "id", "referenceId", "version", "createdBy", "createdAt", "updatedBy", "updatedAt");
        } else {
            BeanUtils.copyProperties(source, target);
        }
    }
}
