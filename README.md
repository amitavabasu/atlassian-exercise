# atlassian-exercise

This project is implementation of 'Developer API Screening Exercise'.
- The main source file is - "exercise.AtlassianExercise.java" (This is the class where main logic is implemented)
    - Implemented using java 1.8 with the help of spring-rest-template to access proposed REST API(s)
    - run as a simple java application to check output with command line parameters as specified in the exercise doc.
    - example: $> "java exercise.AtlassianExercise http://localhost:8080/api/v1 story bug task". (make sure the rest services running at the specified location)
- All other part of this project is for testing purpose
    - Implemented using spring-boot-1.3.5.RELEASE
    - Check pom.xml for all dependencies
    - Can run "exercise.Application.java" to start spring-boot REST services (as specified in the excercise) at the location: http://localhost:8080/api/v1

- For complete end-to-end test first run the spring boot "exercise.Application" and then the run the "exercise.AtlassianExercise"