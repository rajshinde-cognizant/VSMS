-- Switch to authdb and manage users table
USE authdb;
SELECT * FROM users;
TRUNCATE TABLE users;

-- Switch to userdb and manage users table
USE userdb;
SELECT * FROM users;
TRUNCATE TABLE users;
DELETE FROM users WHERE id = 5;

-- Switch to vehicledb and manage vehicle table
USE vehicledb;
TRUNCATE TABLE vehicle;
SELECT * FROM vehicle;

-- Switch to bookingdb and manage bookings table
USE bookingdb;
SELECT * FROM bookings;
TRUNCATE TABLE bookings;

-- Switch to centerdb and manage service center and mechanic tables
USE centerdb;
SELECT * FROM service_center;
TRUNCATE TABLE mechanic;
TRUNCATE TABLE service_center;
SELECT * FROM service_center;
TRUNCATE TABLE service_type;

-- Switch to invoicedb
USE invoicedb;
