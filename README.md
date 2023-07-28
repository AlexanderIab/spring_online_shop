# Spring Online Shop

This is my project.
At the moment, the main structure of the project has been created.

It was deployed to Heroku: https://online-shop-iablonski.herokuapp.com/

**Technology stack:**

- Spring Boot
- Spring Security
- Spring Web
  <br><br>
- Spring Data JPA
- Hibernate
- PostgreSQL
- Flyway
  <br><br>
- Spring Boot Mail
- Spring Integration (as a trial additional feature - not working yet)
- Lombok
  <br><br>
- HTML
- Thymeleaf
- CSS
- jQuery (sorting tables, displaying the menu and alerts)

In order to enter the user profile, you need to click on the name in the upper right corner of the screen. 
In the profiles there is an opportunity to change the email and password, 
as well as delete the account and view the order history. 
To sort tables (products, order history, users) you need to click on any heading.
When you create an order, it is sent to your email, and the bucket is cleared.
Detailed information about roles and accesses is below.


### Roles (Admin, Manager, Client, Guest)

- #### **ADMIN** (default)
  username: **admin**<br>
  password: **pass**

Can be created only by Admin.<br>
Responsible for everything related to users: adding (setting roles for new users - except for the Guest),
deleting (permanently), viewing information about all users.

1. Admin cannot be deleted.
2. When you change your email, you do not need to verify it.
3. Has the right to make purchases, place orders and view only his order history.

- #### **MANAGER**
  username: **manager**<br>
  password: **pass**

Can be created only by Admin.<br>
Responsible for everything related to products: adding and deleting.<br>
Can delete his account, which is archived in the database.

1. The manager has the right to view information about all users.
2. When you change your email, you do not need to verify it.
3. Has the right to make purchases, place orders and view only his order history. 

- #### **CLIENT**

Can be created by any user only after confirming his email address.

1. Can delete his account, which is archived in the database.
2. When you change your email, you need to verify it.
3. Has the right to make purchases, place orders and view only his order history. 

- #### **GUEST**

Can be created by any user. All registered users, but not confirmed email.

1. Can delete his account, which is archived in the database.
2. When you change your email, you need to verify it.
3. Has no right to make purchases until the email is verified.
