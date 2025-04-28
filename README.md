# Overview

This project is completed to fulfill Openway Technical Test

## Periplus Shopping Cart Test Automation

This project demonstrates automated testing of the Periplus e-commerce website using Selenium WebDriver, TestNG, and Java. The test automates a complete shopping flow, including login, product search, adding to cart, and verification.

## Features

- Automated login with secure credential handling
- Product search and selection
- Cart interaction and validation
- Automatic cleanup after test execution
- Environment variable support for credentials

## Prerequisites

that I used for this project
- Oracle Open JDK 22
- Maven 3.9
- Chrome browser
- Intellij IDEA Community Edition 2024

## Project Structure

```
OpenwayAssessment/
├── src/
│   ├── test/
│   │   ├── java/
│   │   │   └── OpenwayAssessmentTest.java
│   │   └── resources/
└── pom.xml
└── .env
└── README.md
```

## Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/alifismady/openway-assessment.git
cd OpenwayAssessment
```

### 2. Configure environment variables

Create a `.env` file in the project root directory with:
```declarative
cp .envexample .env
```

and fill .env with

```
EMAIL_PERIPLUS=your-email@example.com
PASSWORD_PERIPLUS=your-password
```

Replace `your-email@example.com` and `your-password` with valid Periplus account credentials.

### 3. Install dependencies

Intellij should handle dependencies installation from `pom.xml`

## Running Tests

### Run with Maven

```bash
mvn clean test
```

## Test Scenario

The automated test executes the following steps:

1. Opens Chrome browser in a new window
2. Navigates to the Periplus website
3. Logs in with credentials from environment variables
4. Searches for "Diary of a Wimpy Kid"
5. Selects the first product from search results
6. Adds the product to cart
7. Verifies that the product was successfully added to the cart
8. Removes the product from the cart (cleanup)
9. Closes the browser