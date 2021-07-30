
![Projet 6 du parcours développeur Java OpenClassrooms](paymybuddylogo.png)



DB : MySql 8<br>
Java : 11 <br>
Build : Maven 3.6.0 <br>
SpringBoot : 2.5.0 <br>
DB script : src/main/resources/create_db_mysql.sql (pay_my_buddy & pay_my_buddy_test)<br>
src/test/resources/PopulateDBTest.sql (populate pay_my_buddy_test)<br>

### Start application

* copy/clone this project on your computer.
* run sql query from webapp/src/main/mysql/create_db_mysql.sql
* run sql query from webapp/src/main/mysql/PopulateDBTest.sql
* run the following maven command on your terminal : cd {yourFolderName}/paymybuddy/webapp && mvn spring-boot:run
* Access endpoints on localhost:8080/ 

### Modèle physique des données pour la base de données

![modèle physique des données pour la bdd](db_model.png)

### Diagramme de classe

![diagramme de classe](classDiagram.png)

Monetization : 0.5% charge on Transfer, saved on DB table user (id == 1)

