import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
}

group = "com.fgsantana"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("mysql:mysql-connector-java:8.0.29")
	implementation("org.hibernate:hibernate-core:5.6.9.Final")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
	testImplementation ("io.mockk:mockk:1.9.3")

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
