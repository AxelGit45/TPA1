FROM maven:3.9.6-eclipse-temurin-17
WORKDIR /app
COPY . .
WORKDIR /app/DonaTrack
RUN mvn clean compile -DskipTests -pl donaciones-service -am
EXPOSE 9000
ENV PORT=9000
CMD ["mvn", "exec:java", "-pl", "donaciones-service", "-Dexec.mainClass=arg.com.utn.donatrack.Main"]