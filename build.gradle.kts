import com.google.protobuf.gradle.id

plugins {
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
    id("org.springframework.boot") version "3.3.2"
    id("com.google.protobuf") version "0.9.4"
    id("io.spring.dependency-management") version "1.1.6"

    idea
}

group = "com.marrrang"
version = "0.0.1-SNAPSHOT"

val grpcSpringBootStarterVersion = "4.4.5"
val grpcProtoVersion = "1.63.0"
val grpcVersion = "1.36.0"
val grpcKotlinVersion = "1.4.1"
val grpcProtoKotlinVersion = "4.27.3"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

sourceSets {
    getByName("main") {
        java {
            srcDirs(
                "build/generated/source/proto/main/java",
                "build/generated/source/proto/main/kotlin"
            )
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:3.2.4")
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.4")

    implementation("net.devh:grpc-spring-boot-starter:3.1.0.RELEASE")

    implementation("io.grpc:grpc-protobuf:$grpcProtoVersion")
    implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
    implementation("com.google.protobuf:protobuf-kotlin:$grpcProtoKotlinVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$grpcProtoKotlinVersion"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpcProtoVersion"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:$grpcKotlinVersion:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach { generateProtoTask ->
            generateProtoTask.plugins {
                id("grpc")
                id("grpckt")
            }
            generateProtoTask.builtins {
                id("kotlin")
            }
        }
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
