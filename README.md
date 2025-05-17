# CricketPlayerDatabaseSystem plus IPL Team Management

A JavaFX-based application for desktop environments.

## Features

- Modern JavaFX UI
- Responsive design
- Modular code structure
- Easy to extend and customize

## Requirements

- Java 11 or higher
- JavaFX SDK (matching your Java version)
- Maven or Gradle (optional, for build automation)

## Getting Started

### Clone the repository

```bash
git clone https://github.com/RiyadunNabi/CricketPlayerDatabseSystem_1-2.git
cd MyJavaFXProject
```

### Build and Run

#### Using Maven

```bash
mvn clean javafx:run
```

#### Using Gradle

```bash
gradle run
```

#### Manual

1. Download and install Java and JavaFX.
2. Compile the source files:
    ```bash
    javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -d out src/main/java/com/example/*.java
    ```
3. Run the application:
    ```bash
    java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -cp out com.example.Main
    ```

## Project Structure

```
MyJavaFXProject/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── example/
│                   ├── Main.java
│                   └── ...
├── pom.xml / build.gradle
└── README.md
```

## Contributing


## License

