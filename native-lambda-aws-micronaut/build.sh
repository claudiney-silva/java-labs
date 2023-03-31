#!/bin/sh
set -e
echo "---iniciando o build maven---"
mvn clean package

echo "---iniciando build nativo---"
USE_NATIVE_IMAGE_JAVA_PLATFORM_MODULE_SYSTEM=false
native-image  -H:Class=com.github.claudineysilva.lambda.micronaut.FunctionLambdaRuntime -H:Name=func --no-fallback -cp "/project/target/*:/project/target/classes/"