Project Name:                     Student Grade Tracker (Console)
Language:                                    Java
Architecture:             Object-Oriented, Command Line Interface, Modular File I/O

                                               Features

This application was engineered with several advanced, robust features to handle unpredictable user behavior and real-world data constraints:

"Smart" File Parser: The file loader doesn't just read standard CSVs (Student,95.5). It can dynamically detect and parse "vertical" text files where the name is on line 1 and the grade is on line 2.

Windows Path Sanitization: Automatically detects and strips invisible literal quotes ("") from file paths if a user uses the Windows "Copy as path" feature, preventing FileNotFoundException crashes.

Bulletproof Input Buffer: Completely avoids Java's notorious Scanner.nextDouble() buffer bugs by reading all input strictly as strings (nextLine()) and safely parsing them into numbers.

Dynamic Timestamped Exports: Prevent accidental data loss. Exported reports automatically append a coded timestamp (e.g., Class_Report_20260214_190842.csv) to ensure previous exports are never overwritten.

Graceful Degradation: If an imported text file contains bad data (e.g., words instead of numbers, grades over 100), the system will safely skip only the bad line, log a warning, and continue importing the rest of the file rather than crashing the application.

                                            User Manual
Prerequisites
Java Development Kit (JDK) installed (Java 8 or higher).

Both Main.java and Student.java must be compiled and located in the same directory.

How to Run
Open your terminal or command prompt. (Similarly use Vscode etc.)

Compile the files: javac Main.java Student.java

Run the application: java Main

Operating Modes
Mode 1: Manual Entry
Choose Option 1 from the main menu. You will be prompted to enter a student's name and their grade (between 0 and 100). The system will catch blank names or invalid numbers. Type no when asked to stop.

Mode 2: File Import
Choose Option 2. Provide the absolute or relative path to a .csv or .txt file.

Acceptable CSV Format: 
Name,Grade (e.g., John Doe,85.5 )

Acceptable TXT Format: 
JD 85.5
   || 
John Doe
  85.5

After importing, the system will ask if you wish to add additional students manually before finalizing the report.

Exporting
Once data entry is complete, the application will display a tabular console report. It will then prompt you for a filename and export the final roster and statistics to your hard drive as a .csv.

                                  Function Reference
The application is highly modularized breaking in core logic functions for easy access and edit.

Menu page (Phase 1)

main(): The entry point. Manages the high-level try-finally execution and scanner closure.

displayMainMenu(): Prints the initial options to the console.

getUserChoice(): Reads and parses the user's initial menu selection.


Manual Data Entry (Phase 2)
handleManualEntryFlow(): The primary loop for manual keyboard inputs.

promptForStudentName(): Forces valid String input; rejects empty lines.

promptForStudentGrade(): Safely parses strings to doubles, handling NumberFormatException.

isValidGrade(): Boolean check against the MIN_VALID_GRADE and MAX_VALID_GRADE constants.

addStudent(): Instantiates the Student object and appends it to the ArrayList.

promptForAnotherStudent(): Manages the yes/no loop continuation logic.

Data Processing (Phase 3)
calculateAverage(): Iterates through the roster to return the mean grade.

findHighest(): Returns the maximum float value in the dataset.

findLowest(): Returns the minimum float value in the dataset.

Display & Exporting (Phase 4 & 6)
displayReport(): Generates the aligned tabular view in the console using printf.

exportToCSV(): Orchestrates the data export process.

promptForExportFileName(): Requests a custom filename or falls back to a default.

generateExportFileName(): Appends the LocalDateTime formatter to the base name.

writeCSVData(): Utilizes PrintWriter to physically write the array and stats to the disk.
File Importing (Phase 5)

handleFileLoadingFlow(): Orchestrates the file request, path sanitization, and loading.

loadFromFile(): The core "Smart Parser" engine reading external files line by line.

shouldSkipLine(): Helper function to ignore empty lines or CSV header rows.

parseAndAddStudent(): Attempts to split CSV lines, validate bounds, and add to the roster.

reportFileLoadResult(): Prints a success/error summary after parsing finishes.

promptForAdditionalManualEntry(): Bridges file loading and manual entry modes.


                                 Considerations & Design Choices

Magic Strings to Constants: All repeating text, table headers, and numerical boundaries (like 0.0 and 100.0) were hoisted to the top of the class as private static final variables. This makes maintaining the code incredibly easy. If the grading scale ever changes to 0-4.0, a developer only has to change the constant at the top of the file, not the underlying logic.

Scanner Lifecycle Management: A single Scanner(System.in) instance is created in main() and passed as an argument (Scanner scanner) to all helper methods. This prevents resource leaks and the dreaded NoSuchElementException that occurs when multiple scanners attempt to close the System.in stream prematurely.

Encapsulation: The Student class handles its own data validation (isValidGrade). If an external script attempts to force a bad grade into the object, it will safely throw an IllegalArgumentException, protecting the integrity of the data.

Try-With-Resources: The exportToCSV function utilizes Java's auto-closing try(PrintWriter...) block. This guarantees that the file stream is closed and saved to the hard drive even if an error occurs halfway through writing the file.