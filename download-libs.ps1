# download-libs.ps1
# Stops the script if any command fails.
$ErrorActionPreference = "Stop"

# Ensure the libs directory exists
if (-not (Test-Path "libs")) {
    New-Item -ItemType Directory -Path "libs" | Out-Null
}

############################################
# MySQL Connector/J 9.2.0
############################################
if (-not (Test-Path "libs\mysql-connector-j-9.2.0")) {
    Write-Host "Downloading MySQL Connector/J 9.2.0..."
    Invoke-WebRequest -Uri "https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-j-9.2.0.tar.gz" -OutFile "libs\mysql-connector-j-9.2.0.tar.gz"
    
    Write-Host "Extracting MySQL Connector/J 9.2.0..."
    New-Item -ItemType Directory -Path "libs\mysql-connector-j-9.2.0" | Out-Null
    
    # Windows 10/11 include tar. This command extracts the tar.gz and strips the first directory level.
    tar -xzf "libs\mysql-connector-j-9.2.0.tar.gz" -C "libs\mysql-connector-j-9.2.0" --strip-components=1
    
    Remove-Item "libs\mysql-connector-j-9.2.0.tar.gz"
}

Write-Host "All libraries have been downloaded and extracted successfully."
