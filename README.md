# Agila

A Java-based CRUD program developed using Eclipse IDE for managing barangay citizen entries.

<p align="center">
  <img src="res/images/splash_agila.png" alt="Agila splash" width="200" height="200"/>
</p>

## Requirements

- Java Development Kit (JDK)
- Eclipse IDE

> **Note:** No need for Maven or Gradle – all dependencies are managed via the provided libraries.

## Project Structure

Below is the project structure:

```
Agila/
├── README.md
├── docker-compose.yml
├── download-libs         # (Unix shell script for downloading libraries)
├── download-libs.ps1     # (PowerShell script for downloading libraries on Windows)
├── bin/
│   ├── libs             # Libraries referenced by the project (see below)
│   └── mysql            # Contains MySQL initialization files and data
├── libs/
│   ├── iText-Core-9.0.0-only-jars/   # iText libraries (downloaded)
│   ├── jcalendar-1.4/                # jCalendar library files (downloaded)
│   ├── jgoodies-forms-1.8.0.jar        # Already included in the repo
│   ├── jgoodies-forms-1.8.0-sources.jar# Already included in the repo
│   ├── miglayout15-swing.jar           # Already included in the repo
│   ├── mysql-connector-j-9.2.0/        # MySQL Connector/J files (downloaded)
│   └── poi-bin-5.2.3/                # Apache POI libraries (downloaded)
├── mysql/
│   ├── init.sql
│   └── mysql_data/
├── res/
│   └── images/             # Images used by the project
└── src/
    ├── CitizensList.java
    ├── DataEditPanel.java
    ├── DataPrintPanel.java
    ├── HomePanel.java
    ├── PDFPrinter.java
    ├── SettingPanel.java
    ├── Dashboard.java
    ├── DataHandlingPanel.java
    ├── ExcelPrinter.java
    ├── LoginWindow.java
    ├── RegisterWindow.java
    ├── TablePreview.java
    ├── main.java
    ├── database/          # Database-related classes
    └── utils/             # Utility classes
```

Your Eclipse `.classpath` file is already configured to point to the libraries in the `libs` folder, so no further changes are required.

## External Libraries and Download Links

The project depends on several external libraries. The required libraries and their download links (DDL) are as follows:

- **iText-Core-9.0.0-only-jars**  
  Download Link: [https://github.com/itext/itext-java/releases/tag/9.0.0](https://github.com/itext/itext-java/releases/tag/9.0.0)

- **jCalendar-1.4**  
  Download Link: [https://www.toedter.com/download/jcalendar-1.4.zip](https://www.toedter.com/download/jcalendar-1.4.zip)

- **Apache POI (poi-bin-5.2.3)**  
  Download Link: [https://archive.apache.org/dist/poi/release/bin/poi-bin-5.2.3-20220909.zip](https://archive.apache.org/dist/poi/release/bin/poi-bin-5.2.3-20220909.zip)

- **MySQL Connector/J (mysql-connector-j-9.2.0)**  
  Download Link: [https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-j-9.2.0.tar.gz](https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-j-9.2.0.tar.gz)

Other libraries (JGoodies Forms and MigLayout) are already included in the repository.

## Installation

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/yourusername/Agila.git
   cd Agila
   ```

2. **Download External Libraries:**

   Two scripts are provided to download and extract the required external libraries into the correct folder structure.

   ### For Windows (PowerShell):

   - Open PowerShell in the project root.
   - If necessary, allow script execution for this session:
     
     ```powershell
     Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass
     ```
     
   - Run the script:
     
     ```powershell
     .\download-libs.ps1
     ```

   ### For Unix-like Systems (Linux/Mac):

   - Open a terminal in the project root.
   - Make the script executable and run it:
     
     ```bash
     chmod +x download-libs
     ./download-libs
     ```

3. **Import the Project into Eclipse:**

   - Open Eclipse.
   - Select **File > Import > Existing Projects into Workspace**.
   - Navigate to the cloned repository and import the project.
   - Eclipse will use the provided `.classpath` configuration that points to the libraries in the `libs` directory.

## Running the Application

1. **Ensure all external libraries are downloaded** (using the appropriate download script).
2. **Run the Application:**
   - In Eclipse, locate your main class (e.g., `main.java` or the class containing your `public static void main(String[] args)` method) in the **src** folder.
   - Right-click the class and select **Run As > Java Application**.

## Interface Preview

<p align="center">
  <img src="https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/98a0bcfc-ba20-41d8-8212-e0b9a0957496/diwan05-cc8a1c34-7ac5-4e57-85db-09bca0deb845.png/v1/fit/w_828,h_468,q_70,strp/list_agila_demo_by_xxxc2ginxxx_diwan05-414w-2x.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9NzIzIiwicGF0aCI6IlwvZlwvOThhMGJjZmMtYmEyMC00MWQ4LTgyMTItZTBiOWEwOTU3NDk2XC9kaXdhbjA1LWNjOGExYzM0LTdhYzUtNGU1Ny04NWRiLTA5YmNhMGRlYjg0NS5wbmciLCJ3aWR0aCI6Ijw9MTI4MCJ9XV0sImF1ZCI6WyJ1cm46c2VydmljZTppbWFnZS5vcGVyYXRpb25zIl19.FuWJomzoARX4yzOEsS6ntA_wZ7jkhH-ZNWTWFHn7s8s"
      loading="eager"
      alt="Agila splash"
      width="auto" height="480"
      loading="eager"
      style="display: block; margin: 0 auto"/>
</p>

## Docker (Database)

The repository includes a `docker-compose.yml` file for setting up a MySQL database. To use Docker:

1. Ensure Docker is installed and running.
2. From the project root, run:

   ```bash
   docker-compose up -d
   ```

This will start a MySQL container using the initialization script in `mysql/init.sql`.

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch:
   ```bash
   git checkout -b feature/YourFeature
   ```
3. Commit your changes:
   ```bash
   git commit -m 'Add some feature'
   ```
4. Push to your branch:
   ```bash
   git push origin feature/YourFeature
   ```
5. Open a Pull Request.

For any major changes, please open an issue first to discuss what you would like to change.

## Troubleshooting

- **Missing Libraries:**  
  If the application fails due to missing libraries, ensure you run the appropriate download script (`download-libs` for Unix or `download-libs.ps1` for Windows).

## License

This project is licensed under the [The Unlicense](LICENSE).

## Acknowledgements

- **JGoodies Forms**
- **MigLayout**
- **MySQL Connector/J**
- **iText**
- **jCalendar**
- **Apache POI**

Special thanks to all contributors and the maintainers of these libraries.