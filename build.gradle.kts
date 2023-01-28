import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform") version "1.8.0"
    id("io.kotest.multiplatform") version "5.5.4"
}

group = "fadian"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        withJava()
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
    js(IR) {
        binaries.executable()
        binaries.library()
        nodejs {
        }
    }
    linuxX64()
    linuxArm32Hfp()
    linuxMips32()
    linuxMipsel32()
    mingwX64()
    mingwX86()
    androidNativeArm64()
    androidNativeArm32()
    targets.forEach {
        if(it is KotlinNativeTarget) {
            it.binaries.sharedLib()
        }
    }
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation("io.kotest:kotest-framework-engine:5.5.4")
                implementation("io.kotest:kotest-assertions-core:5.5.4")
            }
        }
        val jvmMain by getting
        val jvmTest by getting {
            dependencies {
                implementation("io.kotest:kotest-runner-junit5:5.5.4")
            }
        }
        val jsMain by getting
        val nativeMain by creating {

        }
        val linuxMain by creating {
            dependsOn(nativeMain)
        }
        forEach {
            if (it.name.startsWith("linux") && it.name.endsWith("Main"))
                it.dependsOn(linuxMain)
        }
        val mingwMain by creating {
            dependsOn(nativeMain)
        }
        forEach {
            if (it.name.startsWith("mingw") && it.name.endsWith("Main"))
                it.dependsOn(mingwMain)
        }
        val androidNativeMain by creating {
            dependsOn(nativeMain)
        }
        forEach {
            if (it.name.startsWith("androidNative") && it.name.endsWith("Main"))
                it.dependsOn(androidNativeMain)
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.named<Test>("jvmTest") {
    useJUnitPlatform()
}
