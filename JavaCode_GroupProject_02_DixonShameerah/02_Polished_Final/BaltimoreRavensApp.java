// Baltimore Ravens 2026 Team Application
//
// Individual contribution by Tannequa Whitehead:
// Created the JOptionPane welcome screen, name input,
// personalized greeting, main navigation menu, and repeating menu loop.
//
// Individual contribution by Shameerah Dixon:
// Cleaned and organized the project code and implemented CSV File I/O.

import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BaltimoreRavensApp {

    private static final String PLAYERS_FILE = "csv_files/ravens_players.csv";
    private static final String COACHES_FILE = "csv_files/ravens_coaches.csv";
    private static final String SUPPORT_STAFF_FILE =
            "csv_files/ravens_support_staff.csv";
    private static final String EXPORT_FILE = "csv_files/ravens_team_export.csv";

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(
                null,
                "Welcome to the Baltimore Ravens 2026 Team Application!"
        );

        String userName = JOptionPane.showInputDialog(
                null,
                "Please enter your name:"
        );

        if (userName == null || userName.trim().isEmpty()) {
            userName = "Guest";
        }

        JOptionPane.showMessageDialog(
                null,
                "Hello, " + userName + "! Welcome to Ravens Nation!"
        );

        String[] menuOptions = {
                "Players",
                "Coaches",
                "Support Staff",
                "Export Team Data",
                "Exit"
        };

        int selection;

        do {
            selection = JOptionPane.showOptionDialog(
                    null,
                    "What would you like to explore?",
                    "Baltimore Ravens 2026 Main Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    menuOptions,
                    menuOptions[0]
            );

            switch (selection) {
                case 0:
                    showCSVData(PLAYERS_FILE, "Baltimore Ravens Players");
                    break;
                case 1:
                    showCSVData(COACHES_FILE, "Baltimore Ravens Coaches");
                    break;
                case 2:
                    showCSVData(
                            SUPPORT_STAFF_FILE,
                            "Baltimore Ravens Support Staff"
                    );
                    break;
                case 3:
                    writeCSVFile(EXPORT_FILE);
                    break;
                case 4:
                    JOptionPane.showMessageDialog(
                            null,
                            "Thank you for visiting Ravens Nation!"
                    );
                    break;
                default:
                    break;
            }
        } while (selection != 4 && selection != JOptionPane.CLOSED_OPTION);
    }

    // Individual contribution by Shameerah Dixon:
    // Reads Ravens information from a CSV file.
    public static String readCSVFile(String fileName) {
        StringBuilder list = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                line = line.replace("\"", "");
                String[] data = line.split(",", 2);

                if (data.length == 2) {
                    list.append(data[0].trim())
                        .append(" - ")
                        .append(data[1].trim())
                        .append("\n");
                }
            }
        } catch (IOException e) {
            return "Error reading file: " + fileName
                    + "\nPlease make sure the csv_files folder is in the same "
                    + "project folder as BaltimoreRavensApp.java.";
        }

        if (list.length() == 0) {
            return "No data was found in " + fileName;
        }

        return list.toString();
    }

    private static void showCSVData(String fileName, String title) {
        JOptionPane.showMessageDialog(
                null,
                readCSVFile(fileName),
                title,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // Individual contribution by Shameerah Dixon:
    // Writes all Ravens data to one CSV file.
    public static void writeCSVFile(String fileName) {
        String[][] files = {
                {"Players", PLAYERS_FILE},
                {"Coaches", COACHES_FILE},
                {"Support Staff", SUPPORT_STAFF_FILE}
        };

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("Category,Name,Position");

            for (String[] file : files) {
                writeCategoryToExport(writer, file[0], file[1]);
            }

            JOptionPane.showMessageDialog(
                    null,
                    "Team data was successfully exported to:\n" + fileName
            );
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error writing file: " + fileName
            );
        }
    }

    private static void writeCategoryToExport(
            PrintWriter writer,
            String category,
            String sourceFile
    ) throws IOException {
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(sourceFile))) {

            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                line = line.replace("\"", "");
                String[] data = line.split(",", 2);

                if (data.length == 2) {
                    writer.println(
                            category + ","
                            + data[0].trim() + ","
                            + data[1].trim()
                    );
                }
            }
        }
    }
}
