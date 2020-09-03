#!/bin/sh
arangosh \
    --server.endpoint tcp://arango:8529 \
    --server.password admin \
    --javascript.execute /arango-init/db-init.js \
    --console.history false \
    /u ${TOF_USER:-tof_arango} \
    /p ${TOF_PWD:-tof_arango} \
    /d ${TOF_DB:-tof_db}
