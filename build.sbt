name := "new-yorker"

version := "0.1"

scalaVersion := "2.11.12"

resolvers ++= Seq(
  "Typesafe" at "http://repo.typesafe.com/typesafe/releases/",
  "Java.net Maven2 Repository" at "http://download.java.net/maven/2/"
)

libraryDependencies ++= {
  val sparkVersion = "2.4.1"
  Seq(
    "org.apache.spark" %% "spark-core" % sparkVersion,
    "org.apache.spark" %% "spark-sql" % sparkVersion,
    "com.datastax.spark" %% "spark-cassandra-connector" % sparkVersion
  )
}