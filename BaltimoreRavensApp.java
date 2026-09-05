

// Individual contribution by Tannequa Whitehead:
// Created the JOptionPane welcome screen, name input,
// personalized greeting, main navigation menu, and repeating menu loop.

import javax.swing.JOptionPane;

public class BaltimoreRavensApp {

    public static void main(String[] args) {

        // Welcome screen
        JOptionPane.showMessageDialog(
                null,
                "Welcome to the Baltimore Ravens 2026 Team Application!"
        );

        // Get user's name
        String userName = JOptionPane.showInputDialog(
                null,
                "Please enter your name:"
        );

        if (userName == null || userName.trim().isEmpty()) {
            userName = "Guest";
        }

        // Personalized greeting
        JOptionPane.showMessageDialog(
                null,
                "Hello, " + userName + "! Welcome to Ravens Nation!"
        );

        // Main menu options
        String[] menuOptions = {
                "Players",
                "Coaches",
                "Support Staff",
                "Exit"
        };

        int selection;

        // Repeat menu until the user selects Exit
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

            if (selection == 0) {

                // Players
                JOptionPane.showMessageDialog(
                        null,
                        RavensPlayers.getList()
                );

            } else if (selection == 1) {

                // Coaches
                JOptionPane.showMessageDialog(
                        null,
                        RavensCoaches.getList()
                );

            } else if (selection == 2) {

                // Support Staff
                JOptionPane.showMessageDialog(
                        null,
                        RavensSupportStaff.getList()
                );

            } else if (selection == 3) {

                // Exit
                JOptionPane.showMessageDialog(
                        null,
                        "Thank you for visiting Ravens Nation!"
                );
            }

        } while (selection != 3 && selection != JOptionPane.CLOSED_OPTION);
    }
}


// New class added by Pokolo Andrewson:
// Holds Baltimore Ravens coaching staff data.
class RavensCoaches {

    public static final String[] COACHES = {
            "John Harbaugh - Head Coach",
            "Declan Doyle - Offensive Coordinator",
            "Anthony Weaver - Defensive Coordinator",
            "Anthony Levine Sr. - Special Teams Coordinator",
            "Harland Bower - Outside Linebackers Coach",
            "Marcus Brady - Passing Game Coordinator",
            "Randy Brown - Senior Special Teams Coach",
            "Kenan Clarke - Coaching Fellowship",
            "Eliana Detata - Coaching Fellowship",
            "Ben Davis - Coaching Research Engineer",
            "Christina Deruyter - Chief of Staff to the Head Coach",
            "Keary Colbert - Wide Receivers Coach",
            "Eddie Faulkner - Running Backs Coach",
            "Lou Esposito - Defensive Line Coach",
            "Shawn Flaherty - Assistant Offensive Line Coach",
            "Charlie Gelman - Game Management Coordinator/Defensive Assistant",
            "Prentice Gill - Assistant Wide Receivers Coach",
            "Greg Goines - Assistant Strength and Conditioning Coach",
            "Zack Grossi - Tight Ends Coach",
            "Travis Hawkins - Coaching Fellow",
            "Ben Kotwica - Senior Assistant Special Teams Coach",
            "Patrick Kramer - Offensive Quality Control Coach",
            "Dwayne Ledford - Run Game Coordinator/Offensive Line Coach",
            "Joe Lombardi - Senior Offensive Assistant Coach",
            "Megan McLaughlin - Senior Director of Football Information",
            "Tony Michalek - Officiating Liaison",
            "Mike Mickens - Pass Game Coordinator/Secondary",
            "Rick Minter - Football Analyst",
            "Matt O'Donnell - Defensive Assistant",
            "Alex Officer - Coaching Fellow",
            "Andrew Rogan - Defensive Quality Control Coach",
            "Tyler Santucci - Inside Linebackers Coach",
            "Miles Taylor - Assistant Defensive Backs Coach",
            "Opetaia Tuiava - Coaching Fellow",
            "PJ Volker - Safeties Coach",
            "Israel Woolfork - Quarterbacks Coach"
    };

    // Returns the coaching staff as a formatted list.
    public static String getList() {
        return "BALTIMORE RAVENS COACHES (2026)\n\n"
                + String.join("\n", COACHES);
    }
}


// Individual contribution by Shameerah Dixon:
// Holds Baltimore Ravens support staff data.
class RavensSupportStaff {

    public static final String[] SUPPORT_STAFF = {
            "Jenn Hoffman — Chief of Staff to the General Manager",
            "Joey Cleary — Director of College Scouting",
            "Corey Frazier — Director of Pro Personnel",
            "Derrick Yam — Director of Data & Decision Science",
            "Jameel McClain — Director of Player Engagement",
            "Dr. Nic Gill — VP of Health and Performance",
            "Adrian Dixon — Head Certified Athletic Trainer",
            "Chris Marroquin — Director of Player Rehabilitation",
            "Kenico Hines — Head Equipment Manager",
            "Mark Bienvenu — VP of Football Video Operations",
            "Collin Ferguson — Director of Football Video Operations",
            "Spencer Krock — Football Communications Coordinator"
    };

    // Returns the support staff as a formatted list.
    public static String getList() {
        return "BALTIMORE RAVENS SUPPORT STAFF (2026)\n\n"
                + String.join("\n", SUPPORT_STAFF);
    }
}
// Individual contribution by Parker Behagg:
// Holds Baltimore Ravens Player data.
class RavensPlayers {

    public static final String[] PLAYERS = {
                "Lamar Jackson - Quarterback",
                "Derrick Henry - Running Back",
                "Justice Hill - Running Back",
                "Zay Flowers - Wide Receiver",
                "Rashod Bateman - Wide Receiver",
                "Ja'Kobi Lane - Wide Receiver",
                "Devontez Walker - Wide Receiver",
                "Elijah Sarratt - Wide Receiver",
                "LaJohntay Wester - Wide Receiver",
                "Mark Andrews - Tight End",
                "Durham Smythe - Tight End",
                "Ronnie Stanley - Offensive Tackle",
                "John Simpson - Guard",
                "Ethan Pocic - Center",
                "Vega Ioane - Guard",
                "Roger Rosengarten - Offensive Tackle",
                "Andrew Vorhees - Guard",
                "Emery Jones Jr. - Offensive Tackle",
                "Nnamdi Madubuike - Defensive Tackle",
                "Travis Jones - Defensive Tackle",
                "Calais Campbell - Defensive End",
                "Broderick Washington Jr. - Defensive Tackle",
                "John Jenkins - Defensive Tackle",
                "Trey Hendrickson - Linebacker",
                "Tavius Robinson - Linebacker",
                "Mike Green - Linebacker",
                "Roquan Smith - Linebacker",
                "Trenton Simpson - Linebacker",
                "Teddye Buchanan - Linebacker",
                "Jay Higgins IV - Linebacker",
                "Nate Wiggins - Cornerback",
                "Marlon Humphrey - Cornerback",
                "Chidobe Awuzie - Cornerback",
                "T.J. Tampa - Cornerback",
                "Chandler Rivers - Cornerback",
                "Kyle Hamilton - Safety",
                "Malaki Starks - Safety",
                "Tyler Loop - Placekicker",
                "Ryan Eckley - Punter"

    };

    // Returns the players as a formatted list.
    public static String getList() {
        return "BALTIMORE RAVENS PLAYERS (2026)\n\n"
                + String.join("\n", PLAYERS);
    }
}
