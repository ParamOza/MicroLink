# Stage 1: Build the application using Gradle
FROM gradle:8.13-jdk17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy Gradle files to cache dependencies in intermediate builds
COPY build.gradle.kts settings.gradle.kts /app/

# Copy the source code
COPY src /app/src

# Copy the Gradle wrapper to ensure the build uses it
COPY gradlew /app/gradlew
COPY gradle /app/gradle
RUN ./gradlew clean build -x test

# Stage 2: Create the final image with the built JAR
FROM amazoncorretto:17
COPY --from=build /app/build/libs/MicroLink-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]