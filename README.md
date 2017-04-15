# gmologin

## Introduction

This project is a simple Web application allowing a user to register and to access a secure page only after successfully logged in.

Spring Security is used to handle the session and the creation of the password.

JPA (Java Persistence API) is used to access the database (hibernate). For running and testing this project the database is in-memory. The database is deleted each time the server is shutdown.

Annotations with BindingResult are used to validate parameters.

## Start the project

To start the project:
```
$ ./gradlew bootRun
```
And open your browser to http://localhost:8080.

## Run the tests

To run the unit tests:
```
$ ./gradlew test
```

## Good practice

Here is a quick list of some good practices about security.

### Listening to the traffic

In 2017, all sites dealing with sensitive data must use a certificate and HTTPS to encrypt the connection between the client and the server.
This will prevent from people sniffing the connection to see the data sent back and forth. For this project I'm using HTTP just because it is a test application.

### Password storage

The password is not stored in plain text in the database. With Spring Security, a random salt is added to the password and then it is hashed.
This prevent from brute force attack and if the database is leaked, then the passwords are not readable.

### Timing attack

When the authentication failed, it's better to not return right away to avoid timing attack. It's better to sleep for a random short time so the hacker can't deduct when the validation failed.

### Error message

The message "login or password is incorrect" is used instead of "password incorrect" or "login incorrect" to not give too much information.
For a public forum, where usernames are public, this has not so relevant.

### Sql injection

It's important to sanitize the parameters received from a user before running a sql query.

### Session hijacking attack

The session is invalidated after the user logs out to prevent session hijacking attack. 


## Possible improvments

* use 2 factors authentications
* use [YubiKey](https://www.yubico.com/start/)
* get the country from the IP and if the country is different than before, send an email
* after 5 wrong attempts, ban the ip for 5min

