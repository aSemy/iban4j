rootProject.name = "iban4k"

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
