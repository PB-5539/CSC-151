/*
Parker Behagg
10-28-26
CSC-151
Concrete Pad Estimator
*/
//UI
import javax.swing.JOptionPane;
//File reading/writing
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
//Exception handilng
import java.io.IOException;

//Main Code
public class M5IndivProj_BehaggParker {
    private static final String CSV_FILE = "ConcretePadEstimates.csv";

    public static void main(String[] args) {
        //welcome screen and name get.
        JOptionPane.showMessageDialog(null, "Welcome to the Concrete Pad Estimating Application!");

        String estimatorName = JOptionPane.showInputDialog("Please enter your name:");
        if (estimatorName == null) {
            estimatorName = "Estimator";
        } else if (estimatorName.trim().isEmpty()) {
            estimatorName = "Estimator";
        }

        //main menu loop
        boolean running = true;
        while (running) {
            String[] options = {"Residential Pad", "Warehouse Slab", "Load from File", "Custom Estimate", "Exit"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Choose the type of estimate to prepare:\n\n" +
                            "- Residential Pad\n" +
                            "- Warehouse Slab\n" +
                            "- Load from File\n" +
                            "- Custom Estimate",
                    "Concrete Pad Estimator",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == JOptionPane.CLOSED_OPTION) {
                continue; //Skips the rest of the loop back to the top.
            }

            if (choice == 4) {
                JOptionPane.showMessageDialog(null, "Thank you for using the Concrete Pad Estimator.");
                running = false;
                continue;
            }

            double width = 0;
            double length = 0;
            double thickness = 0;
            double wastePercent = 0;
            double discountPercent = 0;
            String projectName = "";
            String location = "";

            switch (choice) {
                case 0:
                    projectName = "Residential Pad";
                    location = "Residential Site";
                    width = 20;
                    length = 30;
                    thickness = 4;
                    wastePercent = 8;
                case 1:
                    projectName = "Warehouse Slab";
                    location = "Industrial Site";
                    width = 80;
                    length = 140;
                    thickness = 6;
                    wastePercent = 10;
                case 2:
                    viewSavedEstimates();
                    continue;
                case 3:
                    projectName = JOptionPane.showInputDialog("Enter the project name:");
                    if (projectName == null || projectName.trim().isEmpty()) {
                        projectName = "Custom Project";
                    }

                    location = JOptionPane.showInputDialog("Enter the project location:");
                    if (location == null || location.trim().isEmpty()) {
                        location = "Not Provided";
                    }

                    String widthInput = JOptionPane.showInputDialog("Enter the pad width in feet:");
                    String lengthInput = JOptionPane.showInputDialog("Enter the pad length in feet:");
                    String thicknessInput = JOptionPane.showInputDialog("Enter the slab thickness in inches:");
                    String wasteInput = JOptionPane.showInputDialog("Enter waste percentage (example: 10 for 10%):");

                    try {
                        width = Double.parseDouble(widthInput);
                        length = Double.parseDouble(lengthInput);
                        thickness = Double.parseDouble(thicknessInput);
                        wastePercent = Double.parseDouble(wasteInput);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid numeric input. Please enter valid values.");
                        continue;
                    }
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "No option selected. Returning to menu.");
                    continue;
            }

            if (choice == 0 || choice == 1) {
                projectName = JOptionPane.showInputDialog("Project name:", projectName);
                if (projectName == null || projectName.trim().isEmpty()) {
                    projectName = "Unnamed Project";
                }

                location = JOptionPane.showInputDialog("Project location:", location);
                if (location == null || location.trim().isEmpty()) {
                    location = "Not Provided";
                }
            }

            double laborRate = promptDouble("Enter the hourly labor rate for workers ($/hour):", 35.0);
            int numberOfEmployees = (int) promptDouble("Enter the total number of employees on the job:", 3.0);
            double hoursPerEmployee = promptDouble("Enter the average work hours per employee:", 8.0);
            double concreteCostPerCubicYard = promptDouble("Enter the concrete cost per cubic yard:", 120.0);
            double discountPercentInput = promptDouble("Enter a discount percentage (0 for none):", 0.0);

            if (numberOfEmployees < 1) {
                JOptionPane.showMessageDialog(null, "Employee count must be at least 1. Please try again.");
                continue;
            }
            if (hoursPerEmployee < 0) {
                JOptionPane.showMessageDialog(null, "Work hours cannot be negative. Please try again.");
                continue;
            }
            if (width <= 0 || length <= 0 || thickness <= 0) {
                JOptionPane.showMessageDialog(null, "Width, length, and thickness must all be greater than zero.");
                continue;
            }

            discountPercent = discountPercentInput;

            Estimate estimate = calculateEstimate(projectName, location, width, length, thickness, wastePercent,
                    laborRate, numberOfEmployees, hoursPerEmployee, concreteCostPerCubicYard, discountPercent);

            JOptionPane.showMessageDialog(null, estimate.toString(), "Concrete Pad Estimate", JOptionPane.INFORMATION_MESSAGE);
            saveEstimate(estimate, estimatorName);

            String[] returnChoices = {"OK", "Exit"};
            int returnChoice = JOptionPane.showOptionDialog(
                    null,
                    "Press OK to return to the menu or Exit to close the program.",
                    "Return to Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    returnChoices,
                    returnChoices[0]
            );

            if (returnChoice == 1) {
                JOptionPane.showMessageDialog(null, "Thank you for using the Concrete Pad Estimator.");
                return;
            }
        }
    }

    private static double promptDouble(String message, double defaultValue) {
        while (true) {
            String input = JOptionPane.showInputDialog(message, defaultValue);
            if (input == null || input.trim().isEmpty()) {
                return defaultValue;
            }
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid number. Please try again.");
            }
        }
    }

    private static Estimate calculateEstimate(String projectName, String location, double width, double length,
                                            double thicknessInches, double wastePercent, double laborRate,
                                            int numberOfEmployees, double hoursPerEmployee,
                                            double concreteCostPerCubicYard, double discountPercent) {

        double areaSqFt = width * length;
        double thicknessFeet = thicknessInches / 12.0;
        double concreteVolumeCubicFt = areaSqFt * thicknessFeet;
        double concreteVolumeCubicYards = concreteVolumeCubicFt / 27.0;

        double wasteMultiplier = 1 + (wastePercent / 100.0);
        double adjustedVolumeCubicYards = concreteVolumeCubicYards * wasteMultiplier;

        double materialCost = adjustedVolumeCubicYards * concreteCostPerCubicYard;
        double totalLaborHours = numberOfEmployees * hoursPerEmployee;
        double laborCost = totalLaborHours * laborRate;

        double subtotal = materialCost + laborCost;
        double discountAmount = subtotal * (discountPercent / 100.0);
        double totalCost = subtotal - discountAmount;

        return new Estimate(projectName, location, width, length, thicknessInches, areaSqFt, concreteVolumeCubicYards,
                adjustedVolumeCubicYards, totalLaborHours, materialCost, laborCost, totalCost,
                discountPercent, discountAmount, numberOfEmployees, hoursPerEmployee);
    }

    private static void saveEstimate(Estimate estimate, String estimatorName) {
        File file = new File(CSV_FILE);

        try (FileWriter writer = new FileWriter(file, true)) {
            if (file.length() == 0) {
                writer.append("EstimatorName,ProjectName,Location,WidthFt,LengthFt,ThicknessIn,AreaSqFt,ConcreteYards,WasteAdjustedYards,Employees,HoursPerEmployee,TotalLaborHours,MaterialCost,LaborCost,DiscountPercent,DiscountAmount,TotalCost\n");
            }

            writer.append(escapeCSV(estimatorName)).append(",")
                    .append(escapeCSV(estimate.projectName())).append(",")
                    .append(escapeCSV(estimate.location())).append(",")
                    .append(String.valueOf(estimate.width())).append(",")
                    .append(String.valueOf(estimate.length())).append(",")
                    .append(String.valueOf(estimate.thicknessInches())).append(",")
                    .append(String.valueOf(estimate.areaSqFt())).append(",")
                    .append(String.valueOf(estimate.concreteYards())).append(",")
                    .append(String.valueOf(estimate.adjustedConcreteYards())).append(",")
                    .append(String.valueOf(estimate.numberOfEmployees())).append(",")
                    .append(String.valueOf(estimate.hoursPerEmployee())).append(",")
                    .append(String.valueOf(estimate.totalLaborHours())).append(",")
                    .append(String.valueOf(estimate.materialCost())).append(",")
                    .append(String.valueOf(estimate.laborCost())).append(",")
                    .append(String.valueOf(estimate.discountPercent())).append(",")
                    .append(String.valueOf(estimate.discountAmount())).append(",")
                    .append(String.valueOf(estimate.totalCost())).append("\n");

            JOptionPane.showMessageDialog(null, "Estimate saved to " + CSV_FILE + " successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving estimate: " + e.getMessage());
        }
    }

    private static void viewSavedEstimates() {
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "No saved estimates were found in " + CSV_FILE + ".");
            return;
        }

        StringBuilder display = new StringBuilder();
        display.append("Saved Concrete Pad Estimates\n\n");
        display.append(String.format("%-18s %-18s %-10s %-10s %-12s %-12s %-10s %-12s%n",
                "Project", "Location", "Employees", "Hours", "Material", "Labor", "Discount", "Total"));
        display.append("--------------------------------------------------------------------------------------------------------\n");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean headerRead = false;
            boolean hasData = false;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                if (!headerRead) {
                    headerRead = true;
                    continue;
                }

                String[] values = parseCsvLine(line);
                if (values.length < 17) {
                    continue;
                }

                try {
                    String projectName = values[1].trim();
                    String location = values[2].trim();
                    String employeesValue = values[9].trim();
                    String totalHoursValue = values[11].trim();
                    String materialValue = values[12].trim();
                    String laborValue = values[13].trim();
                    String discountValue = values[14].trim();
                    String totalValue = values[16].trim();

                    display.append(String.format("%-18s %-18s %-10s %-10s $%-11.2f $%-11.2f %-9s $%-11.2f%n",
                            projectName,
                            location,
                            employeesValue,
                            totalHoursValue,
                            Double.parseDouble(materialValue),
                            Double.parseDouble(laborValue),
                            discountValue + "%",
                            Double.parseDouble(totalValue)));
                    hasData = true;
                } catch (NumberFormatException e) {
                    // Skip malformed rows without crashing the viewer.
                }
            }

            if (!hasData) {
                JOptionPane.showMessageDialog(null, "The file exists, but it does not contain any saved estimates.");
                return;
            }

            JOptionPane.showMessageDialog(null, display.toString(), "Saved Estimates", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading saved estimates: " + e.getMessage());
        }
    }

    private static String[] parseCsvLine(String line) {
        java.util.List<String> values = new java.util.ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                values.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }

        values.add(current.toString());
        return values.toArray(new String[0]);
    }

    private static String escapeCSV(String value) {
        if (value == null) {
            return "";
        }
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    private record Estimate(String projectName, String location, double width, double length, double thicknessInches,
                           double areaSqFt, double concreteYards, double adjustedConcreteYards, double totalLaborHours,
                           double materialCost, double laborCost, double totalCost, double discountPercent,
                           double discountAmount, int numberOfEmployees, double hoursPerEmployee) {

        @Override
        public String toString() {
            return "Concrete Pad Estimate\n\n" +
                    "Project Name: " + projectName + "\n" +
                    "Location: " + location + "\n" +
                    "Dimensions: " + width + " ft x " + length + " ft\n" +
                    "Thickness: " + thicknessInches + " in\n" +
                    "Area: " + String.format("%.2f", areaSqFt) + " sq ft\n" +
                    "Concrete Required: " + String.format("%.2f", adjustedConcreteYards) + " cubic yards\n" +
                    "Labor Hours: " + String.format("%.2f", totalLaborHours) + " hours\n" +
                    "Material Cost: $" + String.format("%.2f", materialCost) + "\n" +
                    "Labor Cost: $" + String.format("%.2f", laborCost) + "\n" +
                    "Discount: " + String.format("%.2f", discountPercent) + "% (-$" + String.format("%.2f", discountAmount) + ")\n" +
                    "Total Estimated Cost: $" + String.format("%.2f", totalCost);
        }
    }
}