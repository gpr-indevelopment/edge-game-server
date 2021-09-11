FROM ubuntu:bionic-20191202

RUN dpkg --add-architecture i386 \
    && apt-get update \
    && apt install software-properties-common -y \
    && add-apt-repository ppa:gregory-hainaut/pcsx2.official.ppa -y \
    && apt-get update \
    && apt-get install pcsx2 -y

RUN ln -s /usr/games/PCSX2 /usr/local/bin/PCSX2

