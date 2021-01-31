## Rodent Raid [![Build Status](https://github.com/sgtechICT1009/ict1009-team64-2021/workflows/CI/badge.svg?branch=main)](https://github.com/sgtechICT1009/ict1009-team64-2021/actions)

### Getting started
- Clone this repository: `git clone https://github.com/sgtechICT1009/ict1009-team64-2021.git`
- `mvn clean spring-boot:run` or `mvnw clean spring-boot:run` (cmd, for powershell: `.\mvnw`)

### Deploying with Docker
- `mvn clean package spring-boot:repackage dockerfile:build -DskipTests`
- `docker-compose up`
