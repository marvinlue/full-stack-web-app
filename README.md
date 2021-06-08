# BACK-END API
## RUN
### Steps to run spring boot application
1. Open terminal in project directory and create a jar file for the application using:
```
$ . /etc/profile.d/maven.sh
$ mvn install
```
2. Change directory to target folder:
```
$ cd target
```
3. Execute jar file, using:
```
$ java -jar api-0.0.1-SNAPSHOT.jar
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
* A logged in user with a specific userId may delete a group with a specific groupId from _groups table using a DELETE request if they are a member of the group and have admin rights:
```
DELETE http://localhost:8080/api/groups/{{userId}}?groupId={{groupId}}
```
e.g. User with userId 1 deleting group with groupId 1:
```
DELETE http://localhost:8080/api/groups/1?groupId=1
```
##### PUT
* A logged in user with a specific userId may update the groupName of group with a specific groupId in _groups table using a PUT request if they are a member of the group and have admin rights:
```
PUT http://localhost:8080/api/groups/{{userId}}?groupId={{groupId}}&groupName={{groupName}}
```
e.g. User with userId 1 setting groupName of group with groupId 1 to "new-groupname":
```
PUT http://localhost:8080/api/groups/1?groupId=1&groupName=new-groupname
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
* A user with a specific userId can join a group with a specific groupId by using a POST request. This is done by adding a entry to members_info table with a dictoinary as input:
```
POST http://localhost:8080/api/members?groupId={{groupId}}&userId={{userId}}
```
Dictionary must be in the following form:
```
{
  "adminRights": "false"
}
```
e.g. if user with userId 1 joins group with groupId 1:
```
POST http://localhost:8080/api/members?groupId=1&userId=1

{
  "adminRights": "false"
}
```
##### DELETE
* A logged in user with a specific currentUserId may delete an user with userId from a group with groupId using a DELETE request if they are both members of the group, the currently logged in user has admin rights and the user being deleted does not. This is done by deleting corresponding entry in members_info table:
```
DELETE http://localhost:8080/api/members/{{currentUserId}}?userId={{userId}}&groupId={{groupId}}
```
e.g. Logged in user with currentUserId 1 deleting user with userId 2 from group with groupId 1:
```
DELETE http://localhost:8080/api/members/1?userId=2&groupId=1
```
**NOTE:** This should be the route used by group admins if they want to delete a member of a group
* A logged in user with a specific currentUserId may leave a group with groupId using a DELETE request if they are a member of the group. This is done by deleting corresponding entry in members_info table:
```
DELETE http://localhost:8080/api/members/{{currentUserId}}?groupId={{groupId}}
```
e.g. Logged in user with currentUserId 1 leaving group with groupId 1:
```
DELETE http://localhost:8080/api/members/1?groupId=1
```
**NOTE:** This should be the route used if a user wants to leave a group
##### PUT
* A logged in user with a specific currentUserId may update the adminRights of user with userId in group with groupId using a PUT request if they are both members of the group, the currently logged in user has admin rights and the other user does not. This is done by updating corresponding entry in members_info table:
```
PUT http://localhost:8080/api/members/{{currentUserId}}?userId={{userId}}&groupId={{groupId}}&adminRights={{adminRights}}
```
e.g. Logged in user with currentUserId 1 setting adminRights of user with userId 2 belonging to group with groupId 1 to "true":
```
PUT http://localhost:8080/api/members/1?userId=2&groupId=1&adminRights=true
```
* A logged in user with a specific currentUserId who is part of a group with specific groupId may update their own adminRights using a PUT request if they have admin rights to do so. This is done by updating corresponding entry in members_info table:
```
PUT http://localhost:8080/api/members/{{currentUserId}}?userId={{userId}}&groupId={{groupId}}&adminRights={{adminRights}}
```
e.g. Logged in user with currentUserId 1, belonging to group with groupId 1, setting their adminRights to "false":
```
PUT http://localhost:8080/api/members/1?userId=1&groupId=1&adminRights=false
```
### Note

HTTP-Testcases for working with Posts, Comments, Sites are already added under post.HTTPTests
Please play around with them to get a feeling for the response Structure!

### Posts

| Request | route | body | example | explanation |
| :---: | :---: | :---: | :---: | :---: |
| POST | api/posts/?userId={userId}&groupId={groupId} | {"post":"Text","category":"cat1","location":{"type":"Point","coordinates":[Lon,Lat]}} | api/posts/?userId=1&groupId=1 | Post is mapped to User and Group |
| GET | api/posts/byUser?userId={userId} | - | api/posts/byUser?userId=1 | Get all Posts from a User identified by its ID |
| GET | api/posts/byUserOrGroup?userId={userId}&groupId={groupId} | - | api/posts/byUserOrGroup?userId=1&groupId=2 | Get all Posts from a Group or User or both |
| GET | api/posts/byTime | {"time": "2021-06-05T07:26:59.529","operation": "greater"/"less"} | - | Get all Posts after / before certain Timestamp |
| GET | api/posts/byLocation | {"longitude":"65","latitude": "80","radius": "1"} | - | Gives all Posts around GPS Coord within Radius in km |
| GET | api/posts/bySite | {"sitename":"Site1","radius": "1"} | - | Get all Posts around a certain Site which is already stored in SiteTable
| DELETE | api/posts/{postId} | - | api/posts/1 | Delete a Post identified by its ID |
| PUT | api/posts/{postId} | {"text":"Update"} | api/posts/1 | Update the text of a Post |

### Comments

| Request | route | body | example | explanation |
| :---: | :---: | :---: | :---: | :---: |
| POST | api/posts/{postId}/comment | {"commentText":"Text","userId":"1"} | api/posts/1/comment | Adds Comment to a Post from a User |
| GET | api/posts/{postId}/comment | - | api/posts/1/comment | Get all Comments to a Post |
| DELETE | api/posts/comment/{commentId} | - | api/posts/comment/1 | Delete a Comment from a Post |
| PUT | api/posts/comment/{commentId} | {"updatedText":"Text"} | api/posts/comment/1 | Update the Text of a Comment from a Post |

### Sites

| Request | route | body | example | explanation |
| :---: | :---: | :---: | :---: | :---: |
| POST | api/site/ | {"siteName":"Site1","location":{"type":"Point","coordinates":[Lon,Lat]}} | - | Adds Site |
| GET | api/site/id?sId={Id} | - | api/site/id?sId=1 | Get Site by its ID |
| GET | api/site/name?name={Name} | - | api/site/name?name=Site1 | Get Site by its Name |
| GET | api/site/all | - | - | Get all Sites |
| DELETE | api/site/{siteId} | - | api/site/1 | Delete a Site |

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

4. Pick any password for root user, i.e. "password"

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
$ sudo mysql -u root -p
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
$ sudo mv ./jdk-16.0.1 /usr/lib/jvm/
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
$ sudo update-alternatives --install "/usr/bin/javac" "javac" "/usr/lib/jvm/jdk-16.0.1/bin/javac" 1
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
$ sudo ln -s /opt/apache-maven-3.8.1 /opt/maven
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




