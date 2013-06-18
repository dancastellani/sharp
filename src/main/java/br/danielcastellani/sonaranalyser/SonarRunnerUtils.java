/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.sonaranalyser;

import br.danielcastellani.sonaranalyser.util.FileUtils;
import br.danielcastellani.sonaranalyser.util.CliUtils;
import java.io.File;
import java.util.Properties;

/**
 *
 * @author DanCastellani
 */
public class SonarRunnerUtils {

    static void analyse(Properties props) {
        prepareProject(props);
        executeSonarRunner(props);
    }

    private static void prepareProject(Properties props) {
        final String destinationFolder = props.getProperty(Main.DESTINATION_FOLDER);
        File propertiesFile = new File(destinationFolder + "/sonar-project.properties");

        System.out.println("Checking if File " + propertiesFile.getAbsoluteFile() + " exists: " + propertiesFile.exists());

        final String useVcsVersion = props.getProperty(Main.USE_VCS_VERSION);
        System.out.println("useVcsVersion = " + useVcsVersion);
        boolean forcePropertiesFileCreation = useVcsVersion != null && "true".equalsIgnoreCase(useVcsVersion);
        System.out.println("forcePropertiesFileCreation = " + forcePropertiesFileCreation);

        if (forcePropertiesFileCreation) {
            System.out.println("Generating sonar-project.properties with version number of VCS.");
            final String projectKey = props.getProperty(Main.SONAR_PROJECT_KEY);
            final String projectName = props.getProperty(Main.SONAR_PROJECT_NAME);
            final String projectCurrentVersion = props.getProperty(Main.CURRENT_PROJECT_VERSION);
            final String projectPath = props.getProperty(Main.DESTINATION_FOLDER);

            SonarProjectInformation projectInformation = SonarProjectInformation.createMavenProjectInformation(projectKey, projectName, projectCurrentVersion, new File(projectPath));
            SonarProjectProperties.createDeafaulSonarProjectPropertiesFile(projectInformation, forcePropertiesFileCreation);

        } else if (!propertiesFile.exists()) {
            System.out.println("Generating sonar-project.properties");
            final String originalPropertiesFile = props.getProperty(Main.PROPERTIES_FILE);
            copyPropertiesFile(propertiesFile, originalPropertiesFile);
        } else {
            System.out.println("File exists...");
        }
    }

    private static void executeSonarRunner(Properties props) {
        final String destinationFolder = props.getProperty(Main.DESTINATION_FOLDER);
        final String sonarRunner = props.getProperty(Main.SONAR_RUNNER);

        final String command = sonarRunner + "/bin/sonar-runner.bat -D project.home=" + destinationFolder;
        CliUtils.exec(command, true);

    }

    private static void copyPropertiesFile(File propertiesFile, String originalPropertiesFile) {
        File originalFile = new File(originalPropertiesFile);
        FileUtils.copyFile(originalFile, propertiesFile);
        System.out.println("File copied.");

    }
}
