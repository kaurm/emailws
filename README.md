# Feature:
Email web service to send emails.
This service will send emails via SendGrid or MailGun APIs.

# Scope:
To deliver emails to recipients with a failover recovery.

# Assumptions or Improvements :
* Input to API will be in JSON format. Sample Input 
```
{
"to":["email1","email2],
"cc":["email1", "email2"],
"bcc":["email1", "email2"],
"subject":"Hello New Subject!!",
"body":"I am here in Local"
}
```
* It doesn't have any unit tests, as I used inbuilt spring validation which we can't unit test.
* API keys for third party APIs are from properties file, will add in DB ideally.
* For failover, used Spring Retry, assuming only 2 third party libraries are used.

# Pre-requisites for Set up/ Deployment
* Java 8
* Tomcat Server
* If you use gradle wrapper, no need of gradle else need to install gradle 5.6.2
* Access to internet.

# Set Up/Deploy
* Clone the repository in your local and import this project as a gradle project.
* Create accounts with Sendgrid and Mailgun to get their API keys. 
* Add those API keys in application.properties file, (DON'T COMMIT API KEYS TO GITHUB Public Repo)
* Also Update the Mailgun domain in application.properties file under "emailws.mailgun.endpoint", just remeber to add "/messages" at the end to complete the endpoint.
* Build your gradle project using gradle wrapper.
* To run it locally, just use command "./gradlew bootrun"
* To deploy it on server, go to <project_home>/build/libs/, copy the war to the tomcat webapps folder
* Now run the tomcat using ./catalina.sh run , catalina.sh will be found in <tomcat_home>/bin/
* To check whether your service is running, use "http://localhost:8080/check", this should print " Service is running..."
* To send emails please refer below section.


# Sending emails 
Use Curl command to send email

```
curl -X POST \
  http://localhost:8080/sendEmail \
  -H 'Content-Type: application/json' \
  -d '{
"to":["email1"],
"cc":["email2"],
"subject":"Hello New Subject!!",
"body":"I am here in Local"
}'
```
or you can use a tool like Postman to make a call.

If successful, you'll see message  "Email Sent Successfully!!"

For errors, Error message in form of JSON will be displayed.

# JSON Input Field Description
* to : Array of emails in TO field of an email; Mandatory
* cc : Array of emails in CC field of an email; Optional
* bcc : Array of emails in BCC field of an email; Optional
* body : Plain text body (String) of an email; Mandatory
* subject : Subject of an email (String) ; Mandatory


