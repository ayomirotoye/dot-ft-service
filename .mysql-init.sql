USE mysql;
UPDATE user SET authentication_string=PASSWORD('d0tp@yus3r') WHERE User='root';
FLUSH PRIVILEGES;