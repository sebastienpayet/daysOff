import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.3.12.RELEASE"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.31"
	kotlin("plugin.spring")  version "1.5.31"
}

extra["log4j2.version"] = "2.17.0"

if (project.hasProperty("mongo")) {
	apply(from = "profile_mongo.gradle")
} else {
	apply(from = "profile_memory.gradle")
}

group = "com.archeon"
version = "0.1.0"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

extra["testcontainersVersion"] = "1.16.0"
extra["cucumberVersion"] = "6.11.0"

dependencies {
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.4")
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("commons-codec:commons-codec:1.15")
	testImplementation("org.jetbrains.kotlin:kotlin-test")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("io.cucumber:cucumber-spring:${property("cucumberVersion")}")
	testImplementation("io.cucumber:cucumber-junit:${property("cucumberVersion")}")
	testImplementation("io.cucumber:cucumber-core:${property("cucumberVersion")}")
	testImplementation("io.cucumber:cucumber-java8:${property("cucumberVersion")}")
	testImplementation("com.trivago.rta:cluecumber-report-plugin:2.7.0")
}

dependencyManagement {
	imports {
		mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

configurations {

}

val cucumberRuntime by configurations.creating {
    extendsFrom(configurations["testImplementation"])
}

task("cucumber") {
	dependsOn("assemble", "compileTestJava")
	doLast {
		javaexec {
			main = "io.cucumber.core.cli.Main"
			classpath = cucumberRuntime + sourceSets.main.get().output + sourceSets.test.get().output
			args = listOf("--plugin", "pretty", "--glue", "com.archeon.daysoff.acceptance", "src/test/resources")
		}
	}
}
