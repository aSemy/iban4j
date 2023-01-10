plugins {
  kotlin("jvm") version "1.7.10"

  id("org.jetbrains.kotlinx.dataframe") version "0.8.1"
}

group = "org.iban4j"
version = "3.2.3-RELEASE"
description = "iban4j"

dependencies {
  implementation(kotlin("stdlib"))

  implementation("org.jetbrains.kotlinx:dataframe:0.8.1")
  implementation("com.squareup.okio:okio:3.2.0")


  testImplementation("junit:junit:4.13.2")
  testImplementation("com.carrotsearch:junit-benchmarks:0.7.2")
}


val countriesVersion = "v2.6.0"
val countriesJson: TextResource = resources.text.fromUri(
  "https://raw.githubusercontent.com/stefangabos/world_countries/$countriesVersion/data/countries/en/countries.json"
)
val countriesCsv: TextResource = resources.text.fromUri(
  "https://raw.githubusercontent.com/stefangabos/world_countries/$countriesVersion/data/countries/en/countries.csv"
)

val downloadCountriesData by tasks.registering(Sync::class) {
  from(countriesJson) {
    rename { "countries_en.json" }
  }
  from(countriesCsv) {
    rename { "countries_en.csv" }
  }
  into(layout.buildDirectory.dir("world_countries"))
}

val downloadIbanRegistryData by tasks.registering(Sync::class) {
  from(
    resources.text.fromUri(
      "https://www.swift.com/swift-resource/11971/download"
    )
  ) {
    rename { "registry.tsv" }
  }
  into(layout.projectDirectory.dir("src/main/resources/iban_data"))
}


tasks.wrapper {
  gradleVersion = "7.5.1"
  distributionType = Wrapper.DistributionType.ALL
}

dataframes {
  schema {
    data = layout.projectDirectory.dir("src/main/resources/iban_data/registry.csv").asFile
    name = "org.example.Repository"
    csvOptions {
//      delimiter = '\t'
    }
  }
}
