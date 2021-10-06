java -Dotel.traces.exporter=jaeger \
  -Dotel.exporter.jaeger.endpoint=http://localhost:14250 \
  -Dotel.exporter.jaeger.timeout=4000 \
  -Dotel.javaagent.debug=false \
  -Dotel.service.name=SampleApp \
  -Dapplication.name=SampleApp \
  -javaagent:opentelemetry-javaagent-all.jar \
  -jar target/*.jar