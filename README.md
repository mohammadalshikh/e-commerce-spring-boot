# E-commerce Website - Java Spring Boot

## How To Run ?

- Have Spring Boot installed,  MySQL server, Wokrbench or any other ide


- Run mysql server


- Create the database and name it: ```springproject```


- Run the sql file in the project to add tables automatically (if you can't run it, do it manually through sql ide or terminal)


- Change the user (database) password to your user password in application.properties


- Change the user password to yours in every occurrence in AdminController, UserController and all the views .jsp files


- Reload pom.xml file


- Run JtSpringProjectApplication.java


- Go http://localhost:8080 



** If you encounter an error while running the java file, go to "Edit configuration" top right and change the working directory to: ```$MODULE_WORKING_DIR$``` then reload project from pom.xml and run it again **


## Log in 
Admin module (http://localhost:8080/admin) 
-  Username: admin
-  Password: 123

  User module (http://localhost:8080)
-  user name: jay
-  password: 123

