package br.danielcastellani.sonaranalyser;

import br.danielcastellani.sonaranalyser.exception.ShapException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {

    public static final String COMENTS = "#";
    public static final String CURRENT_PROJECT_VERSION = "shap.current.project.version";
    public static final String USE_VCS_VERSION = "shap.use.vcs.version";
    public static final String FR = "fr";
    public static final String IR = "ir";
    public static final String PROJECT_URL = "project.url";
    public static final String PROPERTIES_FILE = "properties.file";
    public static final String SONAR_RUNNER = "sonar.runner";
    public static final String SVN_HOME = "svn.home";
    public static final String SVN_USERNAME = "svn.username";
    public static final String SVN_PASSWORD = "svn.password";
    public static final String DESTINATION_FOLDER = "destination.folder";
    //project information parameters
    public static final String SONAR_PROJECT_KEY = "sonar.projectKey";
    public static final String SONAR_PROJECT_NAME = "sonar.projectName";
    public static final String SONAR_PROJECT_DESCRIPTION = "sonar.projectDescription";
    public static final String SONAR_PROJECT_VERSION = "sonar.projectVersion";
    public static final String SONAR_SOURCES = "sonar.sources";
    public static final String SONAR_TESTS = "sonar.tests";
    public static final String SONAR_LANGUAGE = "sonar.language";

    public static void main(String[] args) {

        if (args.length == 0) {
            printUsage();
        }
        Properties props = parseArguments(args);
        props.put(DESTINATION_FOLDER, "temp-revisions");

        int initialRevision = Integer.parseInt(props.getProperty(IR));
        int finalRevision = Integer.parseInt(props.getProperty(FR));
        verifyInitialAndFinalRevisionNumbers(initialRevision, finalRevision);

        boolean firstRevision = true;
        for (int revisionNumber = initialRevision; revisionNumber <= finalRevision; revisionNumber++) {
            props.put(CURRENT_PROJECT_VERSION, "r" + revisionNumber);
            if (firstRevision) {
                SvnUtils.checkout(props, revisionNumber);
                firstRevision = false;
            } else {
                SvnUtils.update(props, revisionNumber);
            }
            SonarRunnerUtils.analyse(props);
        }

    }

    private static void loadProperty(Properties props, String arg) {
        String key = arg.substring(2).split("=")[0];
        String value = arg.split("=")[1];
        if (PROPERTIES_FILE.equals(key)
                || IR.equals(key)
                || FR.equals(key)
                || SONAR_RUNNER.equals(key)
                || SVN_HOME.equals(key)
                || SVN_PASSWORD.equals(key)
                || SVN_USERNAME.equals(key)
                || USE_VCS_VERSION.equals(key)
                || SONAR_LANGUAGE.equals(key)
                || SONAR_PROJECT_DESCRIPTION.equals(key)
                || SONAR_PROJECT_KEY.equals(key)
                || SONAR_PROJECT_NAME.equals(key)
                || SONAR_PROJECT_VERSION.equals(key)
                || SONAR_RUNNER.equals(key)
                || SONAR_SOURCES.equals(key)
                || SONAR_TESTS.equals(key)
                || PROJECT_URL.equals(key)) {
            props.setProperty(key, value);
            System.out.println("Loading property: " + key + "=" + value);
        } else {
            System.out.println("Unrecognized option: " + arg + ". For help use -h");
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
            } else if (arg.startsWith("-F")) {
                if (args.length != 2) {
                    System.out.println("Ivalid number of arguments: " + args.length + ". When using -F, just it can be used.");
                    System.exit(0);
                }
                String filePath = args[1];
                return loadPropertiesFromFile(filePath);
            } else if (arg.startsWith("-D")) {

                if (!arg.contains("=")) {
                    System.out.println("The properties defined with -D must be with like key=value. Example: -Di=10");
                    System.exit(0);
                }
                loadProperty(props, arg);
            } else {
                System.out.println("Unrecognized option: " + arg + ". For help use -h");
            }
        }
        return props;
    }

    //TODO: continue with the SonarProjectInformation properties.
    private static void printUsage() {
        System.out.println("----------- Sonar History Analyses Project ---------");
        System.out.println("usage: [options]");
        System.out.println("");
        System.out.println("Options:");
        System.out.println(" -h,--help          Display (this) help information");
        System.out.println(" -D key=value       Define property 'key' with the value 'value'.");
        System.out.println(" -F <arg>           Define configuration file to use, which must contain the properties below.");
        System.out.println("\nAcceptable properties are:");
        System.out.println("    " + PROPERTIES_FILE + "     (*) Defines where the project is.");
        System.out.println("    " + IR + "                  (*) Defines the Inicial Revision to be considered.");
        System.out.println("    " + FR + "                  (*) Defines the Final Revision to be considered.");
        System.out.println("    " + SONAR_RUNNER + "        (*) Defines the Sonar Runner folder.");
        System.out.println("    " + SVN_HOME + "            (*) Defines the SVN folder.");
        System.out.println("    " + SVN_USERNAME + "        (*) Defines the SVN username to be user.");
        System.out.println("    " + SVN_PASSWORD + "        (*) Defines the SVN password to be used.");
        System.out.println("    " + PROJECT_URL + "         (*) Defines the url of the project's SVN.");
        System.out.println("                   ---> (*) Indicates a obligated property. ");
        System.out.println("\nThe only suported version control system is SVN.");
        System.exit(0);
    }

    private static Properties loadPropertiesFromFile(String filePath) {
        BufferedReader bf = null;
        try {
            Properties props = new Properties();
            File configurationFile = new File(filePath);
            bf = new BufferedReader(new FileReader(configurationFile));
            String line;
            while ((line = bf.readLine()) != null) {
                if (line.startsWith(COMENTS)) {
                    System.out.println("Ignoring comented line: " + line);
                    continue;
                }
                loadProperty(props, line);
            }
            return props;

        } catch (IOException ex) {
            throw new ShapException(ex);
        } finally {
            try {
                bf.close();
            } catch (IOException ex) {
                throw new ShapException(ex);
            }
        }
    }
}
