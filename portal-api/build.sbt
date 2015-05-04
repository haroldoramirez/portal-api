name := "portal-api"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaCore,
  javaJpa.exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
  "org.postgresql" % "postgresql" % "9.3-1102-jdbc4",
  "org.springframework" % "spring-context" % "4.0.6.RELEASE",
  "javax.inject" % "javax.inject" % "1",
  "org.springframework.data" % "spring-data-jpa" % "1.6.4.RELEASE",
  "org.springframework" % "spring-expression" % "4.0.6.RELEASE",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.6.Final",
  "org.mockito" % "mockito-core" % "1.9.5" % "test"
)

lazy val root = (project in file(".")).enablePlugins(PlayJava)