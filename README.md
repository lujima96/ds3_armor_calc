```markdown
# DS3 Armor Calculator

## Overview

The DS3 Armor Calculator is a backend-focused application designed to optimize your armor setup in Dark Souls III. It calculates the best combination of armor pieces (chest, gauntlets, helmet, leggings) to maximize damage reduction while keeping your equipment load within specified thresholds. This project was developed as part of my Backend Bootcamp final project, showcasing modern backend development techniques using Spring Boot, JPA, REST APIs, and a custom brute-force optimization algorithm.

## Features

- **Armor Optimization:**  
  - Evaluates every possible armor combination based on a boss’s damage profile.
  - Supports different optimization strategies: fast roll builds (max 30% of max equip load) and medium roll builds (max 70% of max equip load).
  - Computes a reduction score to help balance damage mitigation and agility.

- **Data Management:**  
  - Uses a custom Data Loader to import JSON files (armor, weapons, rings, shields) into the database.
  - Leverages Spring Data JPA for efficient and clean data access.

- **RESTful API:**  
  - Exposes endpoints for CRUD operations on builds, players, and build-items.
  - Ensures easy integration and extensibility for future enhancements.

- **User Interface:**  
  - Features an AWT-based GUI for demonstration purposes, guiding users through equipment selection and displaying optimized builds.
  - Although the GUI is secondary, it serves as a visual confirmation of the robust backend processing.

- **Modular and Scalable Architecture:**  
  - Clear separation between the data layer, service layer, and controller layer.
  - Clean, maintainable code that demonstrates solid design principles and modern backend practices.

## Technologies Used

- Java 11+
- Spring Boot
- Spring Data JPA (Hibernate)
- RESTful API design
- AWT for GUI demonstration
- Jackson for JSON processing
- Git & GitHub

## Getting Started

### Prerequisites

- **Java JDK 11 or higher:** Ensure that Java is installed.
- **Build Tool:** Maven 
- **Database:** A relational database  MySQL
- **Git:** To clone and manage the repository.

### Installation

1. **Clone the Repository:**
   ```bash
   git clone <repository-URL>
   cd ds3-armor-calc
   ```

2. **Build the Project:**
   - Using Gradle:
     ```bash
     gradle build
     ```

3. **Configure the Database:**
   Update the `application.properties` file with your database connection details.

### Running the Project

- **Start the Backend Server:**  
  Run the application as a Spring Boot application. This starts the backend on the default port (8080).
  ```bash
  mvn spring-boot:run
  ```
  
- **Data Loading:**  
  On startup, the DataLoader component automatically imports JSON files from the classpath into the database.

- **Accessing the API:**  
  Use tools like Postman or cURL to interact with the REST endpoints for builds, players, and items.

- **Launching the GUI :**  
  The AWT-based GUI will launch once the Spring Boot application is ready, providing a visual demonstration of the system's capabilities.

## Usage

1. **Enter Character Details:**  
   Input your character's name and maximum equipment load.

2. **Select Equipment:**  
   Choose rings, weapons, and shields using the GUI panels (optional, for demo purposes).

3. **Optimize Armor:**  
   The backend evaluates all armor combinations based on the selected boss profile and weight constraints to determine the optimal build.

4. **View & Save Builds:**  
   View the optimized builds and save them to the database for future reference.

## Project Structure

- **entities:**  
  Contains JPA entity classes such as `Item`, `Build`, `BuildItem`, `BuildItemId`, and `Player`.

- **repositories:**  
  Spring Data JPA interfaces for data access, including `ItemRepository`, `BuildRepository`, `BuildItemRepository`, and specialized repositories for different armor pieces (e.g., `GauntletRepository`, `HelmRepository`, `LeggingsRepository`, `ChestRepository`).

- **service:**  
  Contains business logic for armor optimization (`ArmorOptimizerService`), build management (`BuildService`), and equipment retrieval (e.g., `RetrieveChests`, `RetrieveGauntlets`, `RetrieveHelms`, `RetrieveLeggings`).

- **gui:**  
  AWT-based user interface components for demonstrating equipment selection and build optimization.

- **data_loader:**  
  A custom component that imports JSON data into the database on startup.

- **dto:**  
  Data Transfer Objects like `ArmorCombination`, `BossProfile`, and `BossProfiles` for communication between layers.

- **model:**  
  The `EquipmentSelection` class that holds user inputs and cumulative equipment weights.

## Contributing

Contributions are welcome! Feel free to open issues or submit pull requests for improvements or additional features.


```
