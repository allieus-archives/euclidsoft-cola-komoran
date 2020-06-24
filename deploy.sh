#!/bin/sh

IMAGE_TAG=cola-komoran:`date "+%Y%m%d"`
SERVICE_NAME=cola-komoran
CPU_CORES=$((`nproc` / 2)) # 50% Performance
MEMORY=4G

if [ ! -f "userdic.txt" ]; then
    >&2 echo "Error) Not found userdic.txt"
    exit 1
fi

echo "building ${IMAGE_TAG} ..."
docker build -t ${IMAGE_TAG} .

echo "drop container ${SERVICE_NAME}"
docker rm -f ${SERVICE_NAME}

echo "run service with cpu_cores(${CPU_CORES}) from ${IMAGE_TAG}"
docker run -d \
    --restart=always \
    --cpus ${CPU_CORES} \
    --memory ${MEMORY} \
    --publish 50051:50051 \
    --name ${SERVICE_NAME} \
    ${IMAGE_TAG}

docker container ls

