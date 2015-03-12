# Covisint Open Registration App

## Install Apache 
  1. Download Apache
    * (Windows: wamp/xampp/easyphp)
    * (Debian : sudo apt-get install apache2) 
    * (RHEL : sudo yum install apache2)
  2. Follow Instruction to install at http://php.net/manual/en/install.unix.apache2.php
  
## Clone the app repository
git clone https://github.com/Covisint/samples.git

## Configure property files
#### put app_id, client_id and client_secret
./includes/setting.ini

## Start app
* Enable mod_rewrite in apache conf file (Note:- In winodows it wont be enabled by default).
* Create a virtual Host in apache2
```
    listen 8000
    <VirtualHost *:8000>
        ServerName localhost
        DocumentRoot "current/directory/path"
        <Directory "current/directory/path">
           Options Indexes FollowSymLinks  
           AllowOverride all
           Require all granted
        </Directory>
    </VirtualHost>
```
## Use App
  Open prefered browser with the address http://localhost:8000/
