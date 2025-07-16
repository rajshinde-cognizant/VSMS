-- Create required databases
CREATE DATABASE IF NOT EXISTS authdb;
CREATE DATABASE IF NOT EXISTS userdb;
CREATE DATABASE IF NOT EXISTS vehicledb;
CREATE DATABASE IF NOT EXISTS bookingdb;
CREATE DATABASE IF NOT EXISTS centerdb;
CREATE DATABASE IF NOT EXISTS invoicedb;

-- authdb
USE authdb;
TRUNCATE TABLE users;

-- userdb
USE userdb;
TRUNCATE TABLE users;
DELETE FROM users WHERE id = 1;

-- vehicledb
USE vehicledb;
TRUNCATE TABLE vehicle;

-- bookingdb
USE bookingdb;
TRUNCATE TABLE bookings;

-- centerdb
USE centerdb;
TRUNCATE TABLE mechanic;
TRUNCATE TABLE service_center;
TRUNCATE TABLE service_type;

-- invoicedb
USE invoicedb;
-- Add your invoice table operations here

-- Check users
SELECT user, host FROM mysql.user;
