# BACK-END API
## RUN
### Steps to run spring boot application
1. Open terminal in project directory and create a jar file for the application using:
```
$ mvn install
```
2. Change directory to target folder:
```
$ cd target
```
3. Execute jar file, using:
```
$ jar -jar api-0.0.1-SNAPSHOT.jar
```
**NOTE** You can clean target folder before running mvn install again, using:
```
$ mvn clean
```

**NOTE** First setup up all the prerequisites using the steps provided before running the application.

## CRUD OPERATION ROUTES SO FAR
API will be running on http://localhost:8080

### HTTP REQUESTS
#### Users
##### GET
* To obtain all entries in users table as a list of dictonaries using a GET request:
```
GET http://localhost:8080/api/users
```
* To obtain the entry in users table with a specific userId and/or username as a dictonary using a GET request:
```
GET http://localhost:8080/api/users/user?userId={{userId}}&username={{username}}
```
EXAMPLE 1: Getting user with userId 1:
```
GET http://localhost:8080/api/users/user?userId=1
```
EXAMPLE 2: Getting user with username "some-username":
```
GET http://localhost:8080/api/users/user?username=some-username
```
EXAMPLE 3: Getting user with userId 1 and username "some-username":
```
GET http://localhost:8080/api/users/user?userId=1&username=some-username
```
**NOTE** EXAMPLE 3 is not really necessary - rather use either of first 2 examples
##### POST
* Using a POST request to add a new user to users table with a dictoinary as input:
```
POST http://localhost:8080/api/users
```
Dictionary must be in the following form:
```
{
  "username": "some-unique-username",
  "email": "some-email@gmail.com",
  "password": "some-password"
}
```
##### DELETE
* To delete a user with userId from users table using a DELETE request:
```
DELETE http://localhost:8080/api/users/{{userId}}
```
e.g. deleting user with userId 1:
```
DELETE http://localhost:8080/api/users/1
```
##### PUT
* Update user properties (username, email, password) of user with userId in users table using a PUT request:
```
PUT http://localhost:8080/api/users/{{userId}}?username={{username}}&email={{email}}&password={{password}}
```
EXAMPLE 1: Setting username of user with userId 1 to "new-username":
```
PUT http://localhost:8080/api/users/1?username=new-username
```
EXAMPLE 2: Setting email of user with userId 1 to "new-email":
```
PUT http://localhost:8080/api/users/1?email=new-email@gmail.com
```
EXAMPLE 3: Setting password of user with userId 1 to "new-password":
```
PUT http://localhost:8080/api/users/1?password=new-password
```
EXAMPLE 4: Updating all properties of user with userId 1:
```
PUT http://localhost:8080/api/users/1?username=new-username&email=new-email&password=new-password
```

#### Groups
##### GET
* To obtain all entries in _groups ("groups" is a reserved word in MySQL, hence "_groups") table as a list of dictonaries using a GET request:
```
GET http://localhost:8080/api/groups
```
* To obtain the entry in _groups table with a specific groupId and/or groupName as a dictonary using a GET request:
```
GET http://localhost:8080/api/groups/group?groupId={{groupId}}&groupName={{groupName}}
```
EXAMPLE 1: Getting group with groupId 1:
```
GET http://localhost:8080/api/groups/group?groupId=1
```
EXAMPLE 2: Getting group with groupName "some-groupname":
```
GET http://localhost:8080/api/groupss/group?groupName=some-groupname
```
EXAMPLE 3: Getting group with groupId 1 and groupName "some-groupname":
```
GET http://localhost:8080/api/groups/group?groupId=1&groupName=some-groupname
```
**NOTE** EXAMPLE 3 is not really necessary - rather use either of first 2 examples
##### POST
* A user with with a specific userId can use a POST request to add a new group to _groups table with a dictoinary as input:
```
POST http://localhost:8080/api/groups?userId={{userId}}
```
Dictionary must be in the following form:
```
{
  "groupName": "some-unique-group-name"
}
```
e.g. if user with userId 1 created the group:
```
POST http://localhost:8080/api/groups?userId=1

{
  "groupName": "some-unique-group-name"
}
```

**NOTE:** Upon group creation user who created group is added to group. All info regarding groups and their members are kept in the members_info table
##### DELETE
* To delete a group with groupId from _groups table using a DELETE request:
```
DELETE http://localhost:8080/api/groups/{{groupId}}
```
e.g. deleting group with groupId 1:
```
DELETE http://localhost:8080/api/groups/1
```
##### PUT
* Update groupName of group with specific groupId in _groups table using a PUT request:
```
PUT http://localhost:8080/api/groups/{{groupId}}?groupName={{groupName}}
```
e.g. setting groupName of group with groupId 1 to "new-groupname":
```
PUT http://localhost:8080/api/groups/1?groupName=new-groupname
```

#### Members
##### GET
* To obtain all entries in members_info table as a list of dictonaries using a GET request:
```
GET http://localhost:8080/api/members
```
* To obtain all entries in members_info table with a specific groupId using a GET request as a list of dictonaries:
```
GET http://localhost:8080/api/members?groupId={{groupId}}
```
e.g. getting all the members belonging the group with groupId 1:
```
GET http://localhost:8080/api/members?groupId=1
```
* To obtain all entries in members_info table with a specific userId as a list of dictonaries using a GET request:
```
GET http://localhost:8080/api/members?userId={{userId}}
```
e.g. getting all the groups that user with userId 1 belongs to:
```
GET http://localhost:8080/api/members?userId=1
```
* To obtain the entry in members_info table with a specific groupId and userId as a dictonary using a GET request:
```
GET http://localhost:8080/api/members/{{groupId}}?userId={{userId}}
```
e.g. to get user with userId 1 belonging to group with groupId 1:
```
GET http://localhost:8080/api/members/1?userId=1
```
##### POST
* A user with a specific userId can be added to a group with a specific groupId by using a POST request. This is done by adding a entry to members_info table with a dictoinary as input:
```
POST http://localhost:8080/api/members?groupId={{groupId}}&userId={{userId}}
```
Dictionary must be in the following form:
```
{
  "adminRights": "true/false"
}
```
e.g. if user with userId 1 is added to group with groupId 1 and is granted adminRights:
```
POST http://localhost:8080/api/members?groupId=1&userId=1

{
  "adminRights": "true"
}
```
##### DELETE
* A user with userId 1 can be deleted from a group with groupId using a DELETE request. This is done by deleting corresponding entry in members_info table:
```
DELETE http://localhost:8080/api/members/{{groupId}}?userId={{userId}}
```
e.g. deleting user with userId 1 from group with groupId 1:
```
DELETE http://localhost:8080/api/members/1?userId=1
```
##### PUT
* The adminRights of user with userId in group with groupId can be updated using a PUT request. This is done by updating corresponding entry in members_info table:
```
PUT http://localhost:8080/api/members/{{groupId}}?userId={{userId}}&adminRights={{adminRights}}
```
e.g. setting adminRights of user with userId 1 belonging to group with groupId 1 to "true":
```
PUT http://localhost:8080/api/members/1?userId=1&adminRights=true
```

## SETUP
### Steps to set up MySQL
This project uses a MySQL database.
#### Step 1 — Installing MySQL
1. To install it, update the package index on your server if you’ve not done so recently:
```
$ sudo apt update
```
2. Then install the mysql-server package:
```
$ sudo apt install mysql-server
```

#### Step 2 — Configuring MySQL
1. Run the security script with sudo:
```
$ sudo mysql_secure_installation utitlity
```
2. Press y|Y to setup VALIDATE PASSWORD component

3. Enter 0 to set the password validation policy to LOW

4. Pick any password for root user

5. Run the following commands to allow remote access to the mysql server:
```
$ sudo ufw enable
$ sudo ufw allow mysql
```
6. Start the database server by running the following command:
```
$ sudo systemctl start mysql
```

#### Step 3 — Creating a Dedicated MySQL User, Creating Database and Granting Privileges
1. Access MySQL shell, using:
```
$ mysql -u root -p
```
2. Once you have access to the MySQL prompt, you can set up database, using:
```
mysql> source database/db_setup.sql;
```
3. Create a new user, using:
```
mysql> CREATE USER 'user'@'localhost' IDENTIFIED BY 'password';
```
4. Grant user the ALL PRIVILEGES privilege, using:
```
mysql> GRANT ALL PRIVILEGES ON *.* TO 'user'@'localhost' WITH GRANT OPTION;
```

### Steps to set up Java 16
1. Download [Java SE Development Kit 16.0.1](https://www.oracle.com/java/technologies/javase-jdk16-downloads.html#license-lightbox)
2. Open terminal in the location to which the archive was downloaded
3. Extract the contents of the archive, using:
```
$ tar -xzvf jdk-16.0.1_linux-x64_bin.tar.gz
```
4. Move the extracted folder to /usr/lib/jvm:
```
$ sudo mv ./jdk-16.0.1 /usr/lib/jvm
```
5. Setup environment variable, using;
```
$ sudo nano /etc/profile
```
6. Scroll down by pressing Page Down button and add at the end of this file (_Ctrl + X_ to exit):
```
# Java 16
JAVA_HOME=/usr/lib/jvm/jdk-16.0.1
PATH=$PATH:$HOME/bin:$JAVA_HOME/bin
export JAVA_HOME
export PATH
```
7. Configure Java Alternatives, using:
```
$ sudo update-alternatives --install "/usr/bin/java" "java" "/usr/lib/jvm/jdk-16.0.1/bin/java" 1
```
8. Configure Java Compiler Alternatives, using:
```
$ sudo update-alternatives --install "/usr/bin/javac" "javac" "/usr/lib/jvm/jdk-16.0.1/bin/java" 1
```
9. Configure Java, using:
```
$ sudo update-alternatives --config java
```
10. Enter the number of the selection containing the path: "/usr/lib/jvm/jdk-16.0.1/bin/java"
11. Configure Java compiler, using:
```
$ sudo update-alternatives --config javac
```
12. Enter the number of the selection containing the path: "/usr/lib/jvm/jdk-16.0.1/bin/javac"

### Steps to set up Maven
1. Install Maven, using:
```
$ sudo apt install maven
```
2. Download the Apache Maven in the /tmp directory:
```
$ wget https://downloads.apache.org/maven/maven-3/3.8.1/binaries/apache-maven-3.8.1-bin.tar.gz -P /tmp
```
3. Extract the archive in the /opt directory:
```
$ sudo tar xf /tmp/apache-maven-*.tar.gz -C /opt
```
4. Create a symbolic link that will point to the Maven installation directory:
```
$ sudo ln -s /opt/apache-maven-3.6.3 /opt/maven
```
5. Setup environment variables:
```
$ sudo nano /etc/profile.d/maven.sh
```
6. Paste the following code (_Ctrl + X_ to exit):
```
export JAVA_HOME=/usr/lib/jvm/jdk-16.0.1
export M2_HOME=/opt/maven
export MAVEN_HOME=/opt/maven
export PATH=${M2_HOME}/bin:${PATH}
```
7. Make script executable, using:
```
$ sudo chmod +x /etc/profile.d/maven.sh
```
8. Load environment variables, using:
```
$ . /etc/profile.d/maven.sh
```




