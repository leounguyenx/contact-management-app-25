# Contact Management Application

As a User, 
- I want to register to the system as User role
- I want to login to the system
- I want to add a new contact (done)
- I want to view a contact details (done)
- I want to update a contact (done)
- I want to delete a contact (done)
-I want to update my user information (done)
- I want to view my contact list:
  - I can view all my contacts with pagination (done)
  - I can sort my contact list (done)
As an Admin,
- I want to login to the system as Admin role (Admin should be created before)
- I want to view a list of all Users in the system (done)
- I want to create a new user (done)
- I want to view user information (done)
- I want to update a user information (done)
- I want to delete a user (done)

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
* Authorization

![img.png](img.png)

![img_1.png](img_1.png)