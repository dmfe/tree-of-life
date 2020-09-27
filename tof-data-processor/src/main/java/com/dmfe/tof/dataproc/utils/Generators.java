package com.dmfe.tof.dataproc.utils;

import java.util.UUID;

public final class Generators {

    private Generators() {
    }

    public static String generateRandomeUuid() {
        return UUID.randomUUID().toString();
    }
}
