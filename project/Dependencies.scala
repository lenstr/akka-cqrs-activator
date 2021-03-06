import sbt._

// format: off

object Version {
  final val Akka                     = "2.4.16"
  final val AkkaKryoSerialization    = "0.5.0"
  final val AkkaHttp                 = "10.0.1"
  final val AkkaHttpJson             = "1.5.4"
  final val AkkaPersistenceCassandra = "0.20"
  final val AkkaPersistenceInmemory  = "1.3.18"
  final val Circe                    = "0.4.0"
  final val ServerSentEvents         = "2.0.0"
  final val Scala                    = "2.11.8"
  final val ScalaTest                = "3.0.0"
}

object Library {
  val akkaKryoSerialization    = "com.github.romix.akka" %% "akka-kryo-serialization"    % Version.AkkaKryoSerialization
  val akkaHttp                 = "com.typesafe.akka"     %% "akka-http"                  % Version.AkkaHttp
  val akkaHttpTestkit          = "com.typesafe.akka"     %% "akka-http-testkit"          % Version.AkkaHttp
  val akkaHttpJson             = "de.heikoseeberger"     %% "akka-http-circe"            % Version.AkkaHttpJson
  val akkaPersistence          = "com.typesafe.akka"     %% "akka-persistence"           % Version.Akka
  val akkaPersistenceCassandra = "com.typesafe.akka"     %% "akka-persistence-cassandra" % Version.AkkaPersistenceCassandra
  val akkaPersistenceInmemory  = "com.github.dnvriend"   %% "akka-persistence-inmemory"  % Version.AkkaPersistenceInmemory
  val circeGeneric             = "io.circe"              %% "circe-generic"              % Version.Circe
  val scalaTest                = "org.scalatest"         %% "scalatest"                  % Version.ScalaTest
  val serverSentEvents         = "de.heikoseeberger"     %% "akka-sse"                   % Version.ServerSentEvents
}
