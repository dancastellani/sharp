package br.danielcastellani.sonaranalyser;

import java.util.Properties;

public class Main {

    public static final String FR = "fr";
    public static final String IR = "ir";
    public static final String PROJECT_URL = "project.url";
    public static final String PROPERTIES_FILE = "properties.file";
    public static final String SONAR_RUNNER = "sonar.runner";
    public static final String SVN_HOME = "svn.home";
    public static final String DESTINATION_FOLDER = "destination.folder";

    public static void main(String[] args) {

        if (args.length == 0) {
            printUsage();
        }
        Properties props = parseArguments(args);
        props.put(DESTINATION_FOLDER, "temp-revisions");

        int initialRevision = Integer.parseInt(props.getProperty(IR));
        int finalRevision = Integer.parseInt(props.getProperty(FR));
        verifyInitialAndFinalRevisionNumbers(initialRevision, finalRevision);

        for (int revisionNumber = initialRevision; revisionNumber <= finalRevision; revisionNumber++) {
            SvnUtils.checkout(props, revisionNumber);
            SonarRunnerUtils.analyse(props);
        }
    }

    private static void verifyInitialAndFinalRevisionNumbers(int initialRevision, int finalRevision) {
        if (initialRevision > finalRevision) {
            System.out.println("Initial revision must be less than final revision.");
            System.out.println("Or equal if you want to analyse just one revision.");
            System.exit(0);
        }
    }

    private static Properties parseArguments(String[] args) {
        Properties props = new Properties();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            if ("-h".equals(arg) || "--help".equals(arg)) {
                printUsage();
            } else if (arg.startsWith("-D")) {

                if (!arg.contains("=")) {
                    System.out.println("The properties defined with -D must be with like key=value. Example: -Di=10");
                    System.exit(0);
                }
                String key = args[i].substring(2).split("=")[0];
                String value = args[i].split("=")[1];

                if (PROPERTIES_FILE.equals(key)
                        || IR.equals(key)
                        || FR.equals(key)
                        || SONAR_RUNNER.equals(key)
                        || SVN_HOME.equals(key)
                        || PROJECT_URL.equals(key)) {

                    props.setProperty(key, value);
                    System.out.println("Loading property: " + key + "=" + value);

                } else {
                    System.out.println("Unrecognized option: " + arg + ". For help use -h");
                }
            } else {
                System.out.println("Unrecognized option: " + arg + ". For help use -h");
            }
        }
        return props;
    }

    private static void printUsage() {
        System.out.println("----------- Sonar History Analyses Project ---------");
        System.out.println("usage: [options]");
        System.out.println("");
        System.out.println("Options:");
        System.out.println(" -h,--help          Display (this) help information");
        System.out.println(" -D key=value       Define property 'key' with the value 'value'.");
        System.out.println("\nAcceptable properties are:");
        System.out.println("    " + PROPERTIES_FILE + "     (*) Defines where the project is.");
        System.out.println("    " + IR + "                  (*) Defines the Inicial Revision to be considered.");
        System.out.println("    " + FR + "                  (*) Defines the Final Revision to be considered.");
        System.out.println("    " + SONAR_RUNNER + "        (*) Defines the Sonar Runner folder.");
        System.out.println("    " + SVN_HOME + "            (*) Defines the SVN folder.");
        System.out.println("    " + PROJECT_URL + "         (*) Defines the url of the project's SVN.");
        System.out.println("                   ---> (*) Indicates a obligated property. ");
        System.out.println("\nThe only suported version control system is SVN.");
        System.exit(0);
    }
}
