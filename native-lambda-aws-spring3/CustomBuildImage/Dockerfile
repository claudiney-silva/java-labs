FROM amazonlinux:2

RUN yum -y update \
    && yum install -y tar unzip gzip bzip2-devel ed gcc gcc-c++ gcc-gfortran \
    less libcurl-devel openssl openssl-devel readline-devel xz-devel \
    zlib-devel glibc-static libcxx libcxx-devel llvm-toolset-7 zlib-static \
    && rm -rf /var/cache/yum

ENV GRAAL_VERSION 22.3.1
ENV GRAAL_FOLDERNAME graalvm-ce-java17-${GRAAL_VERSION}
ENV GRAAL_FILENAME graalvm-ce-java17-linux-amd64-${GRAAL_VERSION}.tar.gz
RUN curl -4 -L https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-${GRAAL_VERSION}/${GRAAL_FILENAME} | tar -xvz
RUN mv $GRAAL_FOLDERNAME /usr/lib/graalvm
RUN rm -rf $GRAAL_FOLDERNAME

# Graal maven plugin requires Maven 3.3.x
ENV MVN_VERSION 3.6.3
ENV MVN_FOLDERNAME apache-maven-${MVN_VERSION}
ENV MVN_FILENAME apache-maven-${MVN_VERSION}-bin.tar.gz
RUN curl -4 -L https://mirrors.ukfast.co.uk/sites/ftp.apache.org/maven/maven-3/${MVN_VERSION}/binaries/${MVN_FILENAME} | tar -xvz
RUN mv $MVN_FOLDERNAME /usr/lib/maven
RUN rm -rf $MVN_FOLDERNAME

# Gradle
ENV GRADLE_VERSION 7.4.1
ENV GRADLE_FOLDERNAME gradle-${GRADLE_VERSION}
ENV GRADLE_FILENAME gradle-${GRADLE_VERSION}-bin.zip
RUN curl -LO https://downloads.gradle-dn.com/distributions/gradle-${GRADLE_VERSION}-bin.zip
RUN unzip gradle-${GRADLE_VERSION}-bin.zip
RUN mv $GRADLE_FOLDERNAME /usr/lib/gradle
RUN rm -rf $GRADLE_FOLDERNAME

VOLUME /project
WORKDIR /project

RUN /usr/lib/graalvm/bin/gu install native-image
RUN ln -s /usr/lib/graalvm/bin/native-image /usr/bin/native-image
RUN ln -s /usr/lib/maven/bin/mvn /usr/bin/mvn
RUN ln -s /usr/lib/gradle/bin/gradle /usr/bin/gradle

ENV JAVA_HOME /usr/lib/graalvm

ENTRYPOINT ["sh"]