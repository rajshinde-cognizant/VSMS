#!/bin/bash

# Prompt for MySQL root password and execute SQL commands
mysql -u root -p -e "
CREATE DATABASE IF NOT EXISTS authdb;
CREATE DATABASE IF NOT EXISTS userdb;
CREATE DATABASE IF NOT EXISTS vehicledb;
CREATE DATABASE IF NOT EXISTS bookingdb;
CREATE DATABASE IF NOT EXISTS centerdb;
CREATE DATABASE IF NOT EXISTS invoicedb;
"
echo "✅ Databases created successfully."
