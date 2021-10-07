FROM openliberty/open-liberty:full-java11-openj9-ubi

ARG VERSION=1.0
ARG REVISION=SNAPSHOT

LABEL \
  org.opencontainers.image.authors="Your Name" \
  org.opencontainers.image.vendor="IBM" \
  org.opencontainers.image.url="local" \
  org.opencontainers.image.source="https://github.com/OpenLiberty/guide-docker" \
  org.opencontainers.image.version="$VERSION" \
  org.opencontainers.image.revision="$REVISION" \
  vendor="Open Liberty" \
  name="system" \
  version="$VERSION-$REVISION" \
  summary="The system microservice from the Docker Guide" \
  description="This image contains the system microservice running with the Open Liberty runtime."

USER root
# make is for snapit
# porcps-ng is for vmstat, ps, top (in linperf.sh)
# net-tools is for netstat (in inperf.sh)
# gdb is for gcore
RUN dnf install -y make procps-ng net-tools gdb
ADD --chown=1001:0 https://www.ibm.com/support/pages/system/files/inline-files/\$FILE/linperf.sh /config/
ADD --chown=1001:0 https://ftp.riken.jp/net/apache/db/derby/db-derby-10.14.2.0/db-derby-10.14.2.0-bin.tar.gz /output/resources/
RUN mkdir -p /pdprof/dump \
    && chown -R 1001:0 /pdprof && chmod 755 /config/linperf.sh \
    && tar -C /output/resources/ -zxf /output/resources/db-derby-10.14.2.0-bin.tar.gz \
    && rm /output/resources/db-derby-10.14.2.0-bin.tar.gz && chown -R 1001:0 /output/resources/
COPY --chown=1001:0 trapit /config/
COPY --chown=1001:0 config/server.xml /config/
COPY --chown=1001:0 config/jvm.options /config/
COPY --chown=1001:0 apps/db.connections.war /config/apps/

USER 1001
