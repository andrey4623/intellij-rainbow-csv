import org.jetbrains.grammarkit.tasks.GenerateLexerTask
import org.jetbrains.grammarkit.tasks.GenerateParserTask

plugins {
    java
    id("org.jetbrains.intellij") version "1.5.3"
    id("org.jetbrains.grammarkit") version "2021.2.2"
}

intellij {
    version.set("2022.1")
    pluginName.set("Rainbow CSV")
    updateSinceUntilBuild.set(false)
}

tasks.runPluginVerifier {
    ideVersions.add("2022.1")
}

allprojects {
    apply {
        plugin("org.jetbrains.grammarkit")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

sourceSets {
    main {
        java.srcDirs("src/main/java", "gen")
        resources.srcDir("src/main/resources")
    }

    test {
        java.srcDirs("src/test/java")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.junit.platform:junit-platform-gradle-plugin:1.2.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

val generateParser = tasks.register<GenerateParserTask>("generateParserTask") {
    source.set("src/main/java/com/andrey4623/rainbowcsv/Csv.bnf")
    group = "build"
    targetRoot.set("gen/")
    pathToParser.set("com/andrey4623/rainbowcsv/parser/CsvParser.java")
    pathToPsiRoot.set("com/andrey4623/rainbowcsv")
}

val generateLexer = tasks.register<GenerateLexerTask>("generateLexerTask") {
    source.set("src/main/java/com/andrey4623/rainbowcsv/Csv.flex")
    targetDir.set("gen/com/andrey4623/rainbowcsv")
    targetClass.set("CsvLexer")
}

val cleanGenerated = task("cleanGenerated") {
    group = tasks["clean"].group
    description = "Remove all generated code"
    doFirst {
        delete("gen")
    }
}

tasks.withType<Copy> {
    setDuplicatesStrategy(DuplicatesStrategy.EXCLUDE)
}

tasks.withType<JavaCompile> {
    dependsOn(generateParser)
    dependsOn(generateLexer)
}

tasks.withType<Delete> {
    dependsOn(cleanGenerated)
}
