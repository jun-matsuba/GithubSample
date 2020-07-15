import org.gradle.api.JavaVersion.VERSION_1_8
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.version

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", Versions.kotlin))
    }
}

plugins {
    id("java-library")
    kotlin("jvm") version Versions.kotlin
}
apply {
    plugin("kotlin")
}

dependencies {
    implementation(Depends.Kotlin.stdlib)
    implementation(Depends.moshi)
    implementation(Depends.Room.runtime)
}

java {
    sourceCompatibility = VERSION_1_8
    targetCompatibility = VERSION_1_8
}
