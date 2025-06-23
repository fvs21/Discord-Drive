# Discord-Storage

Cloud storage solution, that utilizes Discord as a bucket to store files, bypassing (if necessary) the platform’s 10MB per-file upload limit by chunking, encrypting, and reassembling files. Enables users to store and retrieve large files seamlessly within a Discord server.

## Features
* File chunking and encryption: Upload files of any size, and storing them by breaking them into encrypted chunks.
* Unlimited storage: Leverages Discord's own infraestructure (via a bot and a private server) to store files indefinitely.

## Tech stack
* Backend: Java, Spring Boot, Discord 4J, AES Encryption
* Frontend: React
* Storage: Discord Private Server + Message Attachments

## How it works
* The user uploads a file.
* Frontend sends the file to the backend.
* Eaech chunk is uploaded as an attachment to a Discord bot-managed server.
* Metadata and file reconstruction logic allows downloading the full file later.
* The frontend lists uploaded files and manages uploads.

## Disclaimer
This project is for personal use only. It is not affiliated with or endorsed by Discord. Heavy use or abuse could violate Discord’s ToS.

## Usage

```
git clone https://github.com/fvs21/Discord-Drive.git
```

### Frontend
```
cd frontend
npm run dev
```

### Backend
```
cd backend
mvn spring-boot:run
```

## Setting up enviroment variables
Create an .env file in the backend directory and add the following enviroment variables:
```
DISCORD_TOKEN=your discord bot token
CHANNEL_ID=the id of the private discord server you created
SECRET_KEY=a secret key used for encryption
PSQL_USER=your database user username
PSQL_PASSWORD=your database user password
```

