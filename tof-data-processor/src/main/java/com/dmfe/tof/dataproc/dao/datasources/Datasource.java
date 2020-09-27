package com.dmfe.tof.dataproc.dao.datasources;

public interface Datasource<DB> {
    DB getDb();
}
