<p align="center"><img src="/assets/logo.png" width="400"></p>
<p align="center">
<a href="https://github.com/sgtechICT1009/ict1009-team64-2021/actions"><img src="https://github.com/sgtechICT1009/ict1009-team64-2021/workflows/CI/badge.svg?branch=main" alt="Build Status"></a>
</p>

### Getting started

- `git clone https://github.com/sgtechICT1009/ict1009-team64-2021.git`
- `mvn clean spring-boot:run` or `mvnw clean spring-boot:run` (cmd, for powershell: `.\mvnw`)

### Production dependencies

These are not required for development.

- PostgreSQL 12 (Persistence)
- Redis (Sessions, Cache)

## Docker

### Developing with Docker

There is a separate docker-compose file that starts up redis and postgres *only*. This is meant for development only.
`docker-compose -f docker-compose-dev.yml up`

### Deploying with Docker

- `mvn clean package spring-boot:repackage dockerfile:build -DskipTests`
- `docker-compose up`
