# Contact Management Application

As a User, 
- I want to register to the system as User role
- I want to login to the system
- I want to add a new contact 
- I want to view a contact details 
- I want to update a contact 
- I want to delete a contact 
-I want to update my user information 
- I want to view my contact list:
  - I can view all my contacts with pagination 
  - I can sort my contact list 
  
As an Admin,
- I want to login to the system as Admin role
- I want to view a list of all Users in the system
- I want to create a new user
- I want to view user information 
- I want to update a user information 
- I want to delete a user 

## Entity Relationship Diagram
- User: userId, firstName, lastName, email, password phone, address
- Role: roleId, name (USER, ADMIN)
- Contact: contactId, contactName, phone, email, address, note, tag
- Address: addressId, street, city, state, zip

![img_5.png](img_5.png)

## Domain Model Diagram

![img_4.png](img_4.png)

### 1. User Management      
* Register new users
* Login (JWT-based)
* User roles (USER, ADMIN)
### 2. Contact Management
* Create user
![img_7.png](img_7.png)
* View user list (with pagination)
![img_8.png](img_8.png)
* View user details
![img_9.png](img_9.png)
* Update user
![img_10.png](img_10.png)
* Delete user
![img_11.png](img_11.png)
After deleting userId 4
![img_12.png](img_12.png)
* Create contact
![img_6.png](img_6.png)
* View contact list (with pagination)
![img_14.png](img_14.png)
* View contact details
![img_15.png](img_15.png)
* Update contact
![img_16.png](img_16.png)
* Delete contact
![img_17.png](img_17.png)
Contact table data after deleting contactId 6
![img_18.png](img_18.png)
### 3.	Exception Handling
* Custom exception handling
![img_20.png](img_20.png)
![img_21.png](img_21.png)

### 4. Security
* Spring Security - Basic Authentication
![img_22.png](img_22.png)
* JWT-based authentication
![img_23.png](img_23.png)

### 5. Deploy to Cloud
* Deploy MySql database to Azure
![img_24.png](img_24.png)
![img_26.png](img_26.png)
![img_25.png](img_25.png)
* Deploy Spring Boot web application to Azure
![img_27.png](img_27.png)
![img_28.png](img_28.png)
![img_29.png](img_29.png)
![img_30.png](img_30.png)

#### Security after deploying to Azure
![img_33.png](img_33.png)

### 6. Unit Testing
* Controller
![img_31.png](img_31.png)
* Service
![img_32.png](img_32.png)
* Repository
![img_34.png](img_34.png)

### 7. Docker
![img_35.png](img_35.png)

![img.png](img.png)

![img_1.png](img_1.png)