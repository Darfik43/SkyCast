# Skycast

![Homepage](https://github.com/Darfik43/skycast/blob/master/img/homepage.jpg)

## Description

Skycast is a web application to check up the current weather. User can signup and start adding any locations.
After this homepage starts to show added locations

## Technologies Used

### Back-end:
- Java 19
- Apache Tomcat Server 10.1.8
- Servlets 6
- Gradle 8.7
- Hibernate ORM 6.4.4
- MariaDB database
- JUnit5
- Mockito
- Thymeleaf 3.1.2

### Front-end:
- HTML
- CSS
- Bootstrap 5

## Implementation

### MVCs pattern
- A model layer is a hibernate entity with DAO classes processing basic crud operations.
Additionally, it has DTO classes to transfers required data between layers and classes 
- A view layer is HTML+CSS stack using Bootstrap 5
- A controller layer is number of jakarta servlets. Also it has cookie implementation to
remember user when he is logged in

## Installation and Running

1. Clone the repository: git clone https://github.com/Darfik43/skycast
2. Get your [APIKey](https://openweathermap.org/)
3. Insert your key into /skycast/src/main/resource/config.properties
4. Open Terminal
5. cd [path-to-repository]/skycast
6. docker compose build
7. docker compose up
8. Open browser and go localhost:8080/skycast



## Authors

Author: Darfik43

## Contact

If you have any questions or suggestions, feel free to contact us at darfik43@gmail.com
