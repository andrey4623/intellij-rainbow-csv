import org.jetbrains.grammarkit.tasks.GenerateLexer
import org.jetbrains.grammarkit.tasks.GenerateParser

plugins {
    java
    id("org.jetbrains.intellij") version "0.4.13"
    id("org.jetbrains.grammarkit") version "2019.2.2"
}

intellij {
    version = "2019.2"
    pluginName = "Rainbow CSV"
    intellij.updateSinceUntilBuild = false
}

allprojects {
    apply {
        plugin("org.jetbrains.grammarkit")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
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
    compile("org.junit.platform:junit-platform-gradle-plugin:1.2.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

val generateParser = task<GenerateParser>("generateParser") {
    group = "build"
    description = "Generate the Parser"
    source = "src/main/java/com/andrey4623/rainbowcsv/Csv.bnf"
    targetRoot = "gen/"
    pathToParser = "com/andrey4623/rainbowcsv/parser/CsvParser.java"
    pathToPsiRoot = "com/andrey4623/rainbowcsv"
    purgeOldFiles = true
}

val generateLexer = task<GenerateLexer>("generateLexer") {
    dependsOn(generateParser)
    group = "build"
    description = "Generate the Lexer"
    source = "src/main/java/com/andrey4623/rainbowcsv/Csv.flex"
    targetDir = "gen/com/andrey4623/rainbowcsv"
    targetClass = "CsvLexer"
    purgeOldFiles = true
}

tasks.withType<JavaCompile> {
    dependsOn(generateParser)
    dependsOn(generateLexer)
}

val cleanGenerated = task("cleanGenerated") {
    group = tasks["clean"].group
    description = "Remove all generated code"
    doFirst {
        delete("gen")
    }
}

tasks.withType<Delete> {
    dependsOn(cleanGenerated)
}
