package br.danielcastellani.sharp;

import br.danielcastellani.sharp.sonarrunner.SonarRunnerUtils;
import br.danielcastellani.sharp.util.FileUtils;
import br.danielcastellani.sharp.subversion.SubversionService;
import br.danielcastellani.sharp.exception.ShapException;
import br.danielcastellani.sharp.subversion.Revision;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
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
    public static final String SONAR_PROJECT_DATE = "sonar.projectDate";

    //TODO: Change the output from sout to a more elegant way
    public static void main(String[] args) {

        if (args.length == 0) {
            printUsage();
        }
        Properties props = parseArguments(args);
        props.put(DESTINATION_FOLDER, "temp-revisions");

        Integer initialRevision = props.getProperty(IR) != null ? Integer.parseInt(props.getProperty(IR)) : null;;
        Integer finalRevision = props.getProperty(FR) != null ? Integer.parseInt(props.getProperty(FR)) : null;;
        verifyInitialAndFinalRevisionNumbers(initialRevision, finalRevision);

        List<Revision> validRevisions = SubversionService.getRevisionsInRange(initialRevision, finalRevision, props);
        Collections.sort(validRevisions);
        System.out.println("Valid Revisions in rage: " + validRevisions.toString());

//        boolean firstRevision = true;
        for (Revision revision : validRevisions) {
            System.out.println("------------------------------ Processing Revision " + revision);
            props.put(CURRENT_PROJECT_VERSION, "r" + revision.number);
            props.put(SONAR_PROJECT_DATE, revision.getFormatedDate());

            props.put(DESTINATION_FOLDER, "temp-revisions-r" + revision.number);
            SubversionService.checkout(props, revision.number);

            System.out.println("");
            SonarRunnerUtils.analyse(props);
            System.out.println("\n\n");
            File temporaryFolder = new File(props.getProperty(DESTINATION_FOLDER));
            System.out.println("Deleting temporary folder " + temporaryFolder.getPath() + ": " + FileUtils.delete(temporaryFolder));
//            break;
        }
        System.out.println("------ Finished ---------------------------");

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

    private static void verifyInitialAndFinalRevisionNumbers(Integer initialRevision, Integer finalRevision) {
        if (initialRevision == null && finalRevision == null) {
            return;
        }
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
//            } else if ("-g".equals(arg) || "--generate".equals(arg)) {
//                //TODO: get the file name if is passed
////                if (args.length > i) {
////                    
////                }
//                PropertiesFile.generateDefaultPropertiesFile(null);
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
            if (!configurationFile.exists()) {
                throw new RuntimeException("File <" + configurationFile.getAbsolutePath() + "> do not exists.");
            }
            bf = new BufferedReader(new FileReader(configurationFile));
            String line;
            System.out.println("Processing file:    ");
            while ((line = bf.readLine()) != null) {
                System.out.print("> " + line + "... ");
                if (line.startsWith(COMENTS)) {
                    System.out.println(" Ignoring comented line. ");
                    continue;
                } else {
                    loadProperty(props, line);
                }

            }
            System.out.println("Configurations loaded from file!");
            return props;

        } catch (IOException ex) {
            throw new ShapException(ex);
        } finally {
            try {
                if (bf != null) {
                    bf.close();
                }
            } catch (IOException ex) {
                throw new ShapException(ex);
            }
        }
    }
}
