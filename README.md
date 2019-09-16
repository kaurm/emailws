# Feature:
# emailws
Email web service to send emails.
This service will send emails via SendGrid or MailGun APIs.

# Scope:
To deliver emails to recipients with a failover recovery.

# Assumptions or Improvements :
* Input to API will be in JSON format. Sample Input 
{
"to":["email1","email2],
"cc":["email1", "email2"],
"bcc":["email1", "email2"],
"subject":"Hello New Subject!!",
"body":"I am here in Local"
}

* It doesn't have any unit tests, as I used inbuilt spring validation which we can't unit test.
* API keys for third party APIs are from properties file, will add in DB ideally.
* For failover, used Spring Retry, assuming only 2 third party libraries are used.

# Sending emails 
Use Curl command to send email

curl -X POST \
  http://localhost:8080/sendEmail \
  -H 'Content-Type: application/json' \
  -d '{
"to":["k83.mandy@gmail.com"],
"cc":["mandeep.imt@gmail.com"],
"subject":"Hello New Subject!!",
"body":"I am here in Local"
}'

or you can use a tool like Postman to make a call.

If successful, you'll see message  "Email Sent Successfully!!"
For errors, Error message in form of JSON will be displayed.

# JSON Input Field Description
to : Array of emails in TO field of an email; Mandatory
cc : Array of emails in CC field of an email; Optional
bcc : Array of emails in BCC field of an email; Optional
body : Plain text body (String) of an email; Mandatory
subject : Subject of an email (String) ; Mandatory


