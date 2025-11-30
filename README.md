# EHealth Management System — Review 1 (Transactions & MVC Enforcement)

## Summary of changes
This update enforces strict MVC separation and implements JDBC transaction management for critical operations (booking an appointment). Key improvements:
- Business logic moved to `service` layer (`AppointmentService`) — UI/MainApp only calls services.
- DAOs provide transaction-aware methods that accept a `java.sql.Connection` so multiple DAO calls can share one transaction.
- `doctor_schedule` table added to manage available slots; `DoctorDAO.reserveSlot(conn, doctorId, slot)` updates availability within the transaction.
- `AppointmentService.bookAppointment()` demonstrates the correct pattern: start transaction, validate, reserve slot, create appointment, commit; rollback on error.
- README and example `MainApp` updated to show usage.

## How booking is atomic (important)
The `bookAppointment` method obtains a single `Connection`, sets `autoCommit=false`, calls DAO methods (passing the connection), and finally commits or rolls back the transaction depending on success or failure. This guarantees that related changes (slot reservation + appointment row) either both happen or neither does.

## How to test locally
1. Create the `ehealth` database and run `database/schema.sql` and optionally `database/seed.sql`.
2. Set environment variables or edit `DatabaseConnection` defaults:
   - EHEALTH_DB_URL, EHEALTH_DB_USER, EHEALTH_DB_PASS
3. Build the project with Maven: `mvn clean package`
4. Run the MainApp: `mvn exec:java -Dexec.mainClass="com.ehealth.app.MainApp"` (add exec plugin if needed)
5. Verify that booking a slot twice results in an error and that no partial data is left in the database.
