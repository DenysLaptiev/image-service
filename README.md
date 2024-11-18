# Image Service

## Project Overview
The **Image Service** is part of the **AWS Acquaintance Project**. It manages:
- File uploads to an AWS S3 bucket.
- Metadata storage in AWS DocumentDB (MongoDB) for better replication support.

## Features
- Uploading files to S3 using **Spring Boot Starter** for AWS.
- Publishing AWS SNS events upon successful upload.
- Handling requests from the Step Function to delete files.

## Deployment
- Deployed on AWS EC2 instances using **Terraform**.
- MongoDB hosted on AWS DocumentDB.

## Related Services
- [User Service](https://github.com/DenysLaptiev/user-service)
- [Lambda Function](https://github.com/DenysLaptiev/lambda)
