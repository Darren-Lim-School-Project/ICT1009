<p align="center"><img src="/assets/logo.png" width="400"></p>
<p align="center">
<a href="https://github.com/sgtechICT1009/ict1009-team64-2021/actions"><img src="https://github.com/sgtechICT1009/ict1009-team64-2021/workflows/CI/badge.svg?branch=main" alt="Build Status"></a>
</p>

### Getting started

- `git clone https://github.com/sgtechICT1009/ict1009-team64-2021.git`
- `mvn clean spring-boot:run`

### Production dependencies

These are not required for development.

- PostgreSQL 12 (Persistence)
- Redis (Sessions, Cache)

### Configuration

By default, spring will default to using an in memory database (h2) and tomcat's standard session implementation. To
change this, copy `application.yml.example` to `application.yml` under `config/` and configure accordingly.

## About

A Single Page Application (SPA) built with Vue (JS), with Spring MVC (Java) powering the backend. 

## Frontend
You should be at least running Node >14. Remember to start up the backend (`spring-boot:run`) in addition to running `hot`.

### Developing
- `npm install`
- `npm run hot`


## Docker

### Developing with Docker

There is a separate docker-compose file that starts up redis and postgres *only*. This is meant for development only.
`docker-compose -f docker-compose-dev.yml up`

### Deploying with Docker
- `npm run prod`
- `mvn clean package spring-boot:repackage dockerfile:build -DskipTests`
- `docker-compose up`
