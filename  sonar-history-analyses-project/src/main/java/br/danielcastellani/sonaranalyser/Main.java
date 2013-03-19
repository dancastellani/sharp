package br.danielcastellani.sonaranalyser;

import br.danielcastellani.sonaranalyser.analysers.SonarProjectAnalyser;
import br.danielcastellani.sonaranalyser.analysers.SonarProjectInformation;
import br.danielcastellani.sonaranalyser.analysers.SonarRunner.SonarProjectAnalyserWithSonarRunner;
import br.danielcastellani.sonaranalyser.analysers.configuration.SonarConfiguration;
import br.danielcastellani.sonaranalyser.analysers.configuration.SonarDataBaseConfiguration;
import java.io.File;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {


    }

    public static void otherMain(String[] args) {

        Properties prop = new Properties();

        String projectPath = null;

        if (args.length == 0) {
            System.out.println("Using the default value");
            projectPath = "E:\\DanCastellani\\Documents\\NetBeansProjects\\SonarAnalyser";
        } else if (args.length >= 1) {
            projectPath = args[0];
        }

        File projectFolder = new File(projectPath);
        //TODO: implement verification if the path is a folder.
        //TODO: implement verification if the folder contains a POM file.


        //TODO: refactor to exclude hardcoded configuration.
        SonarProjectInformation projectInformation = SonarProjectInformation.createMavenProjectInformation("br.com.danielcastellani", "SonarRunnerTest", "1.0-snapshot", projectFolder);


        SonarConfiguration configuration = new SonarDataBaseConfiguration("localhost", "5432", "postgres", "postgres", "sonar");
        SonarProjectAnalyser sonarProjectAnalyser = new SonarProjectAnalyserWithSonarRunner(projectInformation, configuration);
        sonarProjectAnalyser.analyseWithoutConfiguration();

    }

    Properties parseArguments(String[] args) {
        Properties props = new Properties();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if ("-h".equals(arg) || "--help".equals(arg)) {
                printUsage();
            } else {
                System.out.println("Unrecognized option: " + arg);
            }
        }
        return props;
    }

    private void printUsage() {
        System.out.println("");
        System.out.println("usage: [options]");
        System.out.println("");
        System.out.println("Options:");
        System.out.println(" -h,--help                  Display (this) help information");
        System.out.println(" -p,--project.home          Defines where the project is.");
        System.out.println(" --vcs                      Defines the Version Control System used to get the code. Use one of the options: SVN");
        System.out.println(" -i, --initial-revision     Defines the inicial revision to be considered.");
        System.out.println(" -f, --final-revision       Defines the final revision to be considered.");
        System.exit(0); // NOSONAR
    }
}
