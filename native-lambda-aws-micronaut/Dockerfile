FROM basebuildimage:latest AS graalvm

WORKDIR /home/app
COPY classes /home/app/classes
COPY dependency/* /home/app/libs/
ENV USE_NATIVE_IMAGE_JAVA_PLATFORM_MODULE_SYSTEM=false
RUN native-image  -H:Class=com.github.claudineysilva.lambda.micronaut.FunctionLambdaRuntime -H:Name=func --no-fallback -cp "/home/app/libs/*:/home/app/classes/"

#COPY --from=graalvm /home/app/application /function/func
RUN echo "#!/bin/sh" >> bootstrap && echo "set -euo pipefail" >> bootstrap && echo "./func -Djava.library.path=$(pwd)" >> bootstrap
RUN chmod 777 bootstrap
RUN chmod 777 func
RUN zip -j function.zip bootstrap func