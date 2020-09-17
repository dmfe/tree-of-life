package com.dmfe.tof.dataproc.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public final class Generators {

    private Generators() {
    }

    public static String genrateUuidIfEmpty(String value) {
        return StringUtils.isNotEmpty(value) ? value : UUID.randomUUID().toString();
    }
}
