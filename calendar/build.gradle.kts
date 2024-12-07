import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	alias(libs.plugins.multiplatform)
	alias(libs.plugins.compose.compiler)
	alias(libs.plugins.compose)
	alias(libs.plugins.android.library)
	alias(libs.plugins.ktlint)
	id("com.vanniktech.maven.publish") version "0.27.0"
}

mavenPublishing {
	coordinates("io.github.wojciechosak", "calendar")
	pom {
		name.set("KMP Calendar")
		description.set("Kotlin Compose Multiplatform UI library that simplifies usage of calendar views")
		inceptionYear.set("2024")
		url.set("https://github.com/wojciechosak/calendar/")
		licenses {
			license {
				name.set("Apache-2.0 License")
				url.set("https://www.apache.org/licenses/LICENSE-2.0")
				distribution.set("https://www.apache.org/licenses/LICENSE-2.0")
			}
		}
		developers {
			developer {
				id.set("wojciech.osak")
				name.set("Wojciech Osak")
				url.set("https://github.com/wojciechosak/")
			}
		}
		scm {
			url.set("https://github.com/wojciechosak/calendar/")
			connection.set("scm:git:git://github.com/wojciechosak/calendar.git")
			developerConnection.set("scm:git:ssh://git@github.com/wojciechosak/calendar.git")
		}
	}
	publishToMavenCentral(SonatypeHost.S01)
	signAllPublications()
}

kotlin {
	androidTarget {
		compilations.all {
			compileTaskProvider {
				compilerOptions {
					jvmTarget.set(JvmTarget.JVM_1_8)
					freeCompilerArgs.add("-Xjdk-release=${JavaVersion.VERSION_1_8}")
				}
			}
		}
	}

	jvm()

	js {
		browser()
		binaries.executable()
	}

	listOf(
		iosX64(),
		iosArm64(),
		iosSimulatorArm64(),
	).forEach {
		it.binaries.framework {
			baseName = "ComposeApp"
			isStatic = false
		}
	}

	sourceSets {
		all {
			languageSettings {
				optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
			}
		}
		commonMain.dependencies {
			implementation(compose.runtime)
			implementation(compose.material3)
			implementation(compose.materialIconsExtended)
			@OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
			implementation(compose.components.resources)
			implementation(libs.kotlinx.coroutines.core)
			implementation(libs.composeIcons.featherIcons)
			api(libs.kotlinx.datetime)
		}

		androidMain.dependencies {
			implementation(libs.androidx.appcompat)
			implementation(libs.androidx.activityCompose)
			implementation(libs.compose.uitooling)
			implementation(libs.kotlinx.coroutines.android)
		}

		jvmMain.dependencies {
			implementation(compose.desktop.common)
			implementation(compose.desktop.currentOs)
			implementation(libs.kotlinx.coroutines.swing)
		}

		jsMain.dependencies {
			implementation(compose.html.core)
		}

		iosMain.dependencies {
		}
	}
}


android {
	namespace = "com.wojciechosak.calendar"
	compileSdk = 34

	defaultConfig {
		minSdk = 24
	}
	sourceSets["main"].apply {
		manifest.srcFile("src/androidMain/AndroidManifest.xml")
		res.srcDirs("src/androidMain/resources")
		resources.srcDirs("src/commonMain/resources")
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	buildFeatures {
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = libs.versions.compose.asProvider().get()
	}
}

compose.desktop {
	application {
		mainClass = "MainKt"

		nativeDistributions {
			targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
			packageName = "com.wojciechosak.calendar.desktopApp"
			packageVersion = "1.0.0"
		}
	}
}

compose.experimental {
	web.application {}
}

