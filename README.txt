Library Application
Overview

This project is a comprehensive library management application developed as part of the ICT Module 426.
It is designed to assist library staff in managing books, maintaining user accounts, and organizing the borrowing and return of media items efficiently.
The application is built using Java 21 and requires the proper installation of all dependencies to function seamlessly.

Before running the application, ensure that the following requirements are met:

    Java 21: Make sure Java 21 is installed and configured on your system.
    IDE: We recommend using IntelliJ for development and running the project.

Installation Guide
Step 1: Clone the Repository

To get a local copy of the project, use the following command in your terminal:

git clone https://github.com/verapas/Library_Modul_426

Step 2: Open the Project in your preferred IDE

    Open your preferred IDE
    Click on File > Open... and navigate to the directory where you cloned the project.
    Select the project folder and click OK.

Step 3: Configure Java 21

Ensure that IntelliJ is configured to use Java 21:

    Go to File > Project Structure.
    Under Project, select Project SDK and make sure Java 21 is selected.
    If Java 21 is not listed, click Add SDK > JDK and select the path where Java 21 is installed.

Step 4: Install All Dependencies

    Open the Terminal within IntelliJ or use your system’s terminal.
    Navigate to the project directory.
    Run the appropriate command in the Terminal to install all dependencies (e.g., using Maven):

    # For Maven Dependencies
    mvn install

This command will download and install all the necessary libraries and dependencies for the project.
Running the Application

To start the application in IntelliJ or your preferred IDE:

    Open the Run menu and select Run....
    To Run the Application choose "BibliotheksAppModul426Application" and Run.
    Click Run or press Shift + F10 to execute the application.

    Ensure your environment variables are correctly set for Java.
    If you encounter any issues with dependencies, try cleaning the project and rebuilding it in the Terminal:

    mvn clean install  # For Maven Dependencies

Troubleshooting

    Java Version Issues: Verify that your system’s PATH is correctly configured for Java 21.
    Dependency Problems: Check your build configuration file (e.g., pom.xml for Maven or ) to ensure all dependencies are correctly specified.

Accessing the Application

    IMPORTANT!
    Currently, only the bookoverview page is accessible. The functionality for managing users has not been implemented yet.

    Open any browser of your choice.
    Enter the following URL in the address bar:

http://localhost:8080/bookoverview.html

And voilà! You are now in the application. Feel free to explore and make the most of it.
