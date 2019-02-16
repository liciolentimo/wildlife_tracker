# Wildlife Tracker

## Description

This is a web app that enables rangers to track wildlife in real time. The user first has to add an animal to the database.The animals are categorized as endangered and non-endangered. Once that is complete the user can now make a sighting and input the ranger name and location.

## Prerequisites

* Java `jdk`
* Text editor such as `Visual Studio Code` or IDE such as `IntelliJ IDEA Community Edition`.
* Postgres server `sudo apt-get install postgresql postgresql-contrib libpq-dev`

## Installation

* Clone the repo: `https://github.com/liciolentimo/wildlife_tracker.git`
 Make sure Postgres server is installed and run the following commands:
* `sudo -u postgres createuser --superuser $USER` to create your role. Enter your desired password when prompted.
* `sudo -u postgres createdb $USER` to create a database with your username.
* Run `psql`
* `\c your_username` to change into your database.
* Run `CREATE DATABASE wildlife_tracker;`
* `\c wildlife_tracker` to enter into wildlife_tracker database.
* `CREATE TABLE animals(id serial PRIMARY KEY, name varchar, age varchar, health varchar, endangered boolean, species varchar);`
* `CREATE TABLE sightings(id serial PRIMARY KEY, location varchar, spotted timestamp,rangername varchar);`
* `CREATE TABLE animals_sightings(animal_id int, sighting_id int);`
* `CREATE DATABASE wildlife_tracker_test WITH TEMPLATE wildlife_tracker;`

## Author 

[MIT](LICENSE) © Licio Lentimo