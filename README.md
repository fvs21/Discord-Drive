# Discord-Storage

Web application that allows users to upload files to a Spring Boot backend, that then, using the discord api, uploads it to a private discord server to store them.

As discord only allows an upload size limit of 25 mb, the backend splits the file into serveral 25 mb chunks to succesfully upload them, also encrypting them.

The Vue application allows users to upload files and later on retrieve them.

## Features
* PostgreSQL: Stores information about the user's uploaded files, their names and id's.
* Spring Boot: Provides the RESTful API and handles the application logic such as receiving the file, splitting it, encrypting and uploading to discord.
* VueJS: Used for building the frontend and UI.

## Prerequisites
* Java
* Node.js and npm
* PostgreSQL

## Setup
### Server
1. Clone this repository
2. Open the backend in your preferred IDE.
3. In the application.properties file located in src/main/resources directory, add your PostgreSQL credentials
4. In the terminal run:

```
mvn spring-boot:run
```

### Client
1. Navigate to the frontend directory.
2. Run npm install
3. Run npm run dev
