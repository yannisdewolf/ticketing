

# Start

## In memory
`java -jar -Dspring.profiles.active=inmemory,loaddata target/ticketing-infrastructure-1.0-SNAPSHOT.jar`

## Postgresql
`java -jar -Dspring.profiles.active=postgres target/ticketing-infrastructure-1.0-SNAPSHOT.jar`

# Profiles
- loaddata to load test data from production-data.sql
- inmemory to run against inmemory db
- postgres to run agains postgres db 

