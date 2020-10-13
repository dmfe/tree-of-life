package com.dmfe.tof.dataproc.utils;

import java.util.UUID;

public final class Generators {

    private Generators() {
    }

    public static String generateRandomUuid() {
        return UUID.randomUUID().toString();
    }
}
