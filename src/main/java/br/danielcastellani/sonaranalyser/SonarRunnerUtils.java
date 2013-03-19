/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.sonaranalyser;

import br.danielcastellani.sonaranalyser.exception.ShapException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
        if (!propertiesFile.exists()) {
            final String originalPropertiesFile = props.getProperty(Main.PROPERTIES_FILE);
            copyPropertiesFile(propertiesFile, originalPropertiesFile);
        }
    }

    private static void executeSonarRunner(Properties props) {
        final String destinationFolder = props.getProperty(Main.DESTINATION_FOLDER);
        final String sonarRunner = props.getProperty(Main.SONAR_RUNNER);

        final String command = sonarRunner + "/bin/sonar-runner.bat -D project.home=" + destinationFolder;
        CliUtils.exec(command);

    }

    private static void copyPropertiesFile(File propertiesFile, String originalPropertiesFile) {
        File originalFile = new File(originalPropertiesFile);
        FileUtils.copyFile(originalFile, propertiesFile);
        System.out.println("File copied.");

    }
}
