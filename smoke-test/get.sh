#!/bin/sh

while getopts "p:" opt; do
    case ${opt} in
        p)
            path=${OPTARG}
            ;;
        \?)
            echo "Invalid option"
            exit 1
            ;;
    esac
done

[[ -z ${path} ]] && echo "Path is not specified" && exit 1

response=$(curl -XGET "http://localhost:8080/api/tof/dp${path}")
echo -e "response: \n${response}"

