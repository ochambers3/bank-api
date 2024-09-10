# Bank API Project

## Overview

This project includes a Spring Boot application for managing user accounts in a banking system, and a Python script for running tests against the API endpoints.

## Prerequisites

- Python 3.x
- Java Development Kit (JDK) 17 or above: https://www.oracle.com/th/java/technologies/downloads/
- Apache Maven 3.8.x or above.

### To install Apache Mave:

On MacOS:
Install Maven using Homebrew
    brew install maven
Verify the installation
    mvn -v

On Linux:
Install Maven using your package manager
    sudo apt update
    sudo apt install maven
Verify the installation
    mvn -
    
On Windows:
Download Maven from the official Maven website
    https://maven.apache.org/download.cgi
Extract the archive and add the bin folder to your system's PATH
Verify the installation
    mvn -v


# Steps to Run The Project

Clone the Repository:
git clone https://github.com/ochambers3/bank-api.git

Navigate to project folder:
cd bank-api

Build the Project:
mvn clean install

Run the Application:
mvn spring-boot:run

Create a virtual environment and install dependencies:
python -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt

Run illustration of implementation:
python start_script.python

Run project tests:
mvn test

For any question or issues, please contact:
mr.owen.chambers@gmail.com

