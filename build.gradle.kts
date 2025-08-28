plugins {
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

group = "org.polar"
version = "1.0.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.oauth2.client)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.graphql)

    // Kotlin support
    implementation(libs.kotlin.reflect)
    implementation(libs.jackson.module.kotlin)

    // Payment Provider

    // Supabase
    implementation(platform(libs.superbase.bom))
    implementation(libs.bundles.supabase)
    implementation(libs.ktor)

    //TODO add log4j2

    // Test
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.kotlin.test.junit5)
    testImplementation(libs.spring.graphql.test)
    testImplementation(libs.spring.security.test)
    testRuntimeOnly(libs.junit.platform.launcher)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}


tasks.withType<Test> {
    useJUnitPlatform()
}