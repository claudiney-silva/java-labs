FROM amazonlinux:2

ENV LANG=en_US.UTF-8

#RUN yum update -y && yum install -y gcc gcc-c++ zlib-devel zip tar gzip && yum clean all

RUN yum -y update \
    && yum install -y tar zip unzip gzip bzip2-devel ed gcc gcc-c++ gcc-gfortran \
    less libcurl-devel openssl openssl-devel readline-devel xz-devel \
    zlib-devel glibc-static libcxx libcxx-devel llvm-toolset-7 zlib-static \
    && yum clean all \
    && rm -rf /var/cache/yum

ENV GRAAL_VERSION 22.3.1
ENV GRAAL_FOLDERNAME graalvm-ce-java17-${GRAAL_VERSION}
ENV GRAAL_FILENAME graalvm-ce-java17-linux-amd64-${GRAAL_VERSION}.tar.gz
RUN curl -4 -L https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-${GRAAL_VERSION}/${GRAAL_FILENAME} | tar -xvz
RUN mv $GRAAL_FOLDERNAME /usr/lib/graalvm
RUN rm -rf $GRAAL_FOLDERNAME

#RUN curl -4 -L https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-22.3.0/graalvm-ce-java11-linux-amd64-22.3.0.tar.gz -o /tmp/graalvm.tar.gz \
#    && tar -zxf /tmp/graalvm.tar.gz -C /tmp \
#    && mv /tmp/graalvm-ce-java11-22.3.0 /usr/lib/graalvm \
#    && rm -rf /tmp/*

RUN /usr/lib/graalvm/bin/gu install native-image
RUN ln -s /usr/lib/maven/bin/mvn /usr/bin/mvn
#RUN ln -s /usr/lib/graalvm/bin/native-image /usr/bin/native-image
ENV PATH=/usr/lib/graalvm/bin:${PATH}
ENV JAVA_HOME=/usr/lib/graalvm

VOLUME /project
WORKDIR /project

ENTRYPOINT ["sh"]