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

On clicking **Connect** button, you will **start recieving real time location of drivers** on web page at  http://localhost:8080

# Overall Architecture
The web controller consists of two parts
- REST API 
- Websocket: To push locations of all drivers every 1s.

Database stores one table called  **drivers**, which stores following attributes
* driverId
* name
* latitude (till 7 decimals) - current lat of driver
* longitude (till 7 decimals) - current longitude of driver

REST APIs enable creating/updating/reading drivers.
For find drivers within radius 'r' application uses MySql's  **ST_Distance_Sphere** which is offered as Spatial Convenience Function from MySql 5.7

Websocket provides a channel called  **/topic/updates** to which as WS client can subscribe. App has a scheduler which pushes 
location of drivers to the channel every 5s.


# Code Structure
* controller - Code REST APIs exposed, defining request, response of APIs. ScheduledTasks.java is a scheduler to push all driver locations every 5s.
* service - Conatains business logic. TrackerService.java has business logic & db interactions.
* repository - Interface that actually interacts with DB calls via JPA.
* entity - Java classes that map to database objects.
* dto - API Request & Response Java objects
* config - Websocket & objectmapper config.

# Testing
Didn't get time for Unit testing. Did fuctional testing with API calls.
For real time updates made a small HTML page available at http://localhost:8080 which on clicking connect starts displaying realtime cordinates of drivers.
Time Spent - Around 45 mins

# REST APIs
Postman Collection - https://www.getpostman.com/collections/fe46b26aba3eb83ddab8

Following are the APIs

* **POST /driver/add**  - To add new driver
```
Request
Status 201

curl --location --request POST 'http://localhost:8080/driver/add' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Hawkeye",
    "latitude": -0.00101,
    "longitude": -0.00101
}'
```

```
{
    "driverId": "689baf3c-f022-4711-b053-7b47328ae0f7",
    "name": "Hawkeye",
    "latitude": -0.00101,
    "longitude": -0.00101
}
```
* **GET /driver/{driverId}** - To get driver details
```
Request

curl --location --request GET 'http://localhost:8080/driver/689baf3c-f022-4711-b053-7b47328ae0f7'
```
```
Status 200
{
    "driverId": "689baf3c-f022-4711-b053-7b47328ae0f7",
    "name": "Hawkeye",
    "latitude": -0.0010100,
    "longitude": -0.0010100
}
```
* **GET /driver/find/range** - Find drivers in range

```
Request

curl --location --request GET 'http://localhost:8080/driver/find/range?radius=100&latitude=0.00&longitude=0.00'
```

```
Response
Status 200
{
    "drivers": [
        {
            "driverId": "9c7836c3-25eb-4174-a2d3-768fa72b7e5a",
            "name": "Spidey",
            "latitude": 0.0001000,
            "longitude": 0.0001000
        },
        {
            "driverId": "bbc7acb9-6e5c-47d6-86b4-abb3394f1b91",
            "name": "Thor",
            "latitude": 0.0001200,
            "longitude": 0.0001100
        }
    ]
}
```

* **POST /driver/add** - Update Driver (Should be a PUT request ideally)
```
curl --location --request POST 'http://localhost:8080/driver/add' \
--header 'Content-Type: application/json' \
--data-raw '{
    "driverId": "6025e2ee-e529-42d5-a64e-e1d15b22547d",
    "name": "Hulk",
    "latitude": -0.00301,
    "longitude": -0.00001
}'
```

```
Status 201
{
    "driverId": "6025e2ee-e529-42d5-a64e-e1d15b22547d",
    "name": "Hulk",
    "latitude": -0.00301,
    "longitude": -0.00001
}
```

* **GET /driver/all** - Update Driver

```
Request
curl --location --request GET 'http://localhost:8080/driver/all'
```
```
Response
[
    {
        "driverId": "6025e2ee-e529-42d5-a64e-e1d15b22547d",
        "name": "Hulk",
        "latitude": -0.0030100,
        "longitude": -0.0000100
    },
    {
        "driverId": "689baf3c-f022-4711-b053-7b47328ae0f7",
        "name": "Hawkeye",
        "latitude": -0.0010100,
        "longitude": -0.0010100
    },
    {
        "driverId": "9c7836c3-25eb-4174-a2d3-768fa72b7e5a",
        "name": "Spidey",
        "latitude": 0.0001000,
        "longitude": 0.0001000
    },
    {
        "driverId": "b7552562-020c-42b9-a475-1e0f1164f37d",
        "name": "Iron Man",
        "latitude": 0.0101000,
        "longitude": 0.0020000
    },
    {
        "driverId": "bbc7acb9-6e5c-47d6-86b4-abb3394f1b91",
        "name": "Thor",
        "latitude": 0.0001200,
        "longitude": 0.0001100
    }
]
```
