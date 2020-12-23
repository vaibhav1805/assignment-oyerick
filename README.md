# Setting Up
This is a Spring Boot Application requires Java & Maven setup on the system.
The Application uses MYSQL as database.

### Steps to follow
- Setup MySQL. From the app directory run SQL script
```
mysql -u <username> -p <database_name> < scripts/oye.sql
```

- Edit src/main/resources/application.properties  with your MySQL credentials.
```
spring.datasource.username=<username>
spring.datasource.password=<your-password>
```
- If IntelliJ is available, import this project as Maven project, it will automatically detect run configuration. Run Application.
- Or run from CLI using ```mvn spring-boot:run``` 

After running, app will be available at http://localhost:8080

On clicking connect button, you will start recieving location of drivers on web page at  http://localhost:8080

# Overall Architecture
The web controller consists of two parts
- REST API 
- Websocket: To push locations of all drivers every 5s.

Database stores one table called  **drivers**, which stores following attributes
* driverId
* name
* latitude (till 7 decimals) - current lat of driver
* longitude (till 7 decimals) - current longitude of driver

REST APIs enable creating/updating/reading drivers.
For find drivers within radius 'r' application uses MySql's  **ST_Distance_Sphere** which is offered as Spatial Convenience Function from MySql 5.7

Websocket provides a channel called  **/topic/updates** to which as WS client can subscribe. App has a scheduler which pushes 
location of drivers to the channel every 5s.


# Overall Architecture
* controller - Code REST APIs exposed, defining request, response of APIs. ScheduledTasks.java is a scheduler to push all driver locations every 5s.
* service - Conatains business logic. TrackerService.java has business logic & db interactions.
* repository - Interface that actually interacts with DB calls via JPA.
* entity - Java classes that map to database objects.
* dto - API Request & Response Java objects
* config - Websocket & objectmapper config.

# REST APIs
Postman Collection - https://www.getpostman.com/collections/fe46b26aba3eb83ddab8

Following are the APIs

* **POST /driver/add**  - To add new driver
* **GET /driver/{driverId}** - To get driver details
* **GET /driver/find/range** - Find drivers in range
```

```
