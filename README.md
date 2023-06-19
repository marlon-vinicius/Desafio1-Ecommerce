# Shopping cart simulation

This was a project developed as Challenge 1 of Compass.UOL's Spring Boot learning trail. This repository contains an example code in Java to simulate a shopping cart, where it is possible to register and update products, add and remove products from the cart, calculate the total value of the cart and complete the purchase.

## Technologies Used

The code utilizes the following technologies and libraries:

- **Java**: The programming language used for developing the project.

- **MySQL**: The relational database used to store product data.

- **JDBC**: The Java API used to connect and interact with the MySQL database.


## Prerequisites

To run the code on your computer, you need to have the following tools installed:
```
- JDK 19 (Java Development Kit): Required to compile and run the Java code.

- MySQL 8: Required to create the database and tables used by the program.
```

## Database configuration

Make sure you have MySQL 8 installed, otherwise you will have to download the specific connector for your version and change it in the project's **```/lib```** folder to a new **.jar** file. And make sure to create a path to use this new connector. Here is a site to download other connector versions:
```
https://downloads.mysql.com/archives/c-j/
```

## Running the code

To run the code on your computer, follow the steps below:

1. Clone this repository to your computer using Git:
```
git clone https://github.com/marlon-vinicius/Desafio1-Ecommerce.git
```
2. Open the project in your preferred Java development IDE.

3. Configure the project to use the JDK installed on your system.

4. Open the **`DBConnection.java`** file and update the database connection information, where it says **"YOUR_USERNAME"** and **"YOUR_PASSWORD"** change it for your username and password respectively. Make sure the information matches your local environment settings.

5. The database, the table and the data will be created automatically when running the program as long as you have correctly configured the connection. But **BE CAREFUL**, do not run the program if you have a database called **products_db_desafio** because the program will create and delete the database with that name.

5. Go to **`Main.java`** and run the code to test the shopping cart in the IDE's terminal.

## Final Thoughts

This project brought great challenges in the Java language where I could use the lessons learned during the learning trail. I want to thank the Compass instructors for providing this challenge and I'm glad I did.

Enjoy exploring the Java shopping cart!

## Author

Developed by [**Marlon Vinicius**](https://www.linkedin.com/in/marlon-vinicius-souza-30417a195/)
