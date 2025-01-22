# notification-service
Notification Service 


[Backend] Rate-Limited Notification Service

Notification system that sends out email notifications of various types (status update, daily news, project invitations, etc). We need to protect recipients from getting too many emails, either due to system errors or due to abuse, so let’s limit the number of emails sent to them by implementing a rate-limited version of NotificationService.

The system must reject requests that are over the limit.

Some sample notification types and rate limit rules, e.g.:

-- Status: not more than 2 per minute for each recipient
-- News: not more than 1 per day for each recipient
-- Marketing: not more than 3 per hour for each recipient

Etc. these are just samples, the system might have several rate limit rules!

- Tools:
  - Java 21
  - Gradle 8.2.2
  - Kotlin
  - Spring Framework
  - H2
  - Postman

- Notes:
    For Using run the app with the following command
    ./gradlew bootRun

- Using Postman run the DEMO
  curl --location 'localhost:8080/notifications/v1/demo'
  - This will run the service and will try to send some notifications to some users, some will send other not in base the rate limit rules store in the database
 
- Send one message
  - We can try to send a message to an specific user (Some data is already store in the Memory DB, check that for some interactions with the service to avoid NotFounds errors)
  - curl --location --request POST 'localhost:8080/notifications/v1/send?type=Marketing&userId=1&message='Hello'

- Get the RateLimit from one Notification Type
  - curl --location 'localhost:8080/notifications/v1/rate-limit?type=News'



