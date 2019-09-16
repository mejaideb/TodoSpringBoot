FROM java:8
COPY /target/ /var/www/java
WORKDIR var/www/java
CMD ["java","-jar","com.tavisca.workshops.todolist-0.0.1-SNAPSHOT.jar"]

