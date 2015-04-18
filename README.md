# Bootstrapping a microservice
The magnet-microservice project aims to deliver a minimal setup to bootstrap a new JVM based microservice.

This project is setup with some commonly used dependencies:

* Hibernate with H2 and PostgreSQL support.
* Lombok, Guava, and Guice for modern Java development.
* Logback and associated logging backends.

# How to setup your new service

1. Create a new Git repo, and copy the contents of this repo into your new Git repo.
2. Do a quick text search for "// TODO" to find and fix various references to your new service name.
3. If you require a SQL database to run your service, run the `ServiceApplication` class with the
   arguments `db migrate config.yml`.
4. You're now ready to boot the application. You can do this by running the main method in the
   `ServiceApplication` class with the arguments `server config.yml`.

# SQL stuff

* If you wish to define alternative database to operate on instead of H2 (highly recommended), you should
  make some changes to the `config.yml` file. Simply change the JDBC url and the database driver.
* If you wish to make changes to the structure of your database, add a new `changeset` at the end of the
  `migrations.xml` file. This liquibase file is executed by running the `ServiceApplication` class with the
  `db migrate config.xml` program arguments.
