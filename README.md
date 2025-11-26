# EHealth Management System - Review 1 Submission

## Overview
This repository contains the initial project skeleton for Review 1. The focus is on core Java concepts, OOP design, JDBC connectivity, and database schema design.

## Structure
- `src/main/java/com/ehealth` : Java source code (models, dao, db helper, app)
- `src/main/resources` : configuration files
- `database` : SQL scripts
- `docs` : diagrams and documentation
- `presentation` : Review 1 presentation file (PPTX)

## Setup & Run (local)
1. Create a MySQL database named `ehealth` and run the SQL script in `database/schema.sql` to create tables.
2. Update the DB credentials in `com.ehealth.db.DatabaseConnection`.
3. Compile and run `MainApp.java` using your IDE or `javac`/`java` commands.
   Example (from project root):
   ```
   javac -d out src/main/java/com/ehealth/**/*.java
   java -cp out com.ehealth.app.MainApp
   ```

## Database schema
A simple `patient` table is used as an example. See `database/schema.sql`

## Notes
- This is a starter skeleton for Review 1. Add more DAOs, services, and unit tests in future iterations.
- Code is intentionally simple and commented for learning purposes.
