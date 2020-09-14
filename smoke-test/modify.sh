#!/bin/bash

while getopts "p:f:m:" opt; do
    case ${opt} in
        p)
            path=${OPTARG}
            ;;
        f)
            file=${OPTARG}
            ;;
        m)
            method=${OPTARG}
            ;;
        \?)
            echo "Invalid option"
            exit 1
            ;;
    esac
done

[[ -z ${path} ]] && echo "Path is not specified" && exit 1
[[ -z ${file} ]] && echo "Data file is not specified" && exit 1

response=$(curl -d "@${file}" -H "Content-Type:application/json" -X${method:-POST} "http://localhost:8080/api/tof/dp${path}")
echo -e "response:\n${response}"

