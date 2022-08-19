rootProject.name = "iban4j"

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
  repositories {
    mavenCentral()
    gradlePluginPortal()
  }
  pluginManagement {
    repositories {
      gradlePluginPortal()
      mavenCentral()
    }
  }

  repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
}
