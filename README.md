# Gluco Diary - Angular 8, Spring boot Microservice & Docker demo project


Gluco Diary is a small demo project for angular, spingboot microservices.

Project provides a simple ui for recording blood glucose readings. Application helps user to create and login to an account. Users can start recoding their blood glucose levels for both before and afterfood (Morning, Afternoon and Night).


*Note : The demo project is just a demo project and could improve a lot. I will keep updating the project whenever possible.*


## Front End

Front end for the application runs on Angular 8. The entire application is packaged inside a docker container. The compiled code will be running in a nginx webserver inside the docker.

![Screenshot_from_2019-07-31_19-40-37](/uploads/84f7916fbcc75e0cd38bd4a60462680a/Screenshot_from_2019-07-31_19-40-37.png)

## Back End

Backend is based on microservices artchitecture and is developed with springboot and netflix package.

### Architecture

<p align="center">
<img src="/uploads/cf3dfcae4ece5635acd3b951d0992ca6/Untitled_Diagram.png">
</p>

