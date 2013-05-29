/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.sonaranalyser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DanCastellani
 */
public class PropertiesFile {

    public static void generateDefaultPropertiesFile(String fileName) {
        FileWriter newPropertiesFile = null;
        try {
            if (fileName == null || fileName.isEmpty()) {
                newPropertiesFile = new FileWriter("default-shap.properties");
            } else {
                newPropertiesFile = new FileWriter(fileName);
            }
            newPropertiesFile.append("# ---------- ---------- Default Shap properties");
            newPropertiesFile.append("# ---------- SHAP Information (Mandatory Information)");
            newPropertiesFile.append("# Full path to SVN root");
            newPropertiesFile.append("-Dsvn.home=");
            newPropertiesFile.append("# Full path to Sonar Runner folder root");
            newPropertiesFile.append("-Dsonar.runner=");

            newPropertiesFile.append("# ---------- Project Information (Mandatory Information)");
            newPropertiesFile.append("# Indicates the inicial revision");
            newPropertiesFile.append("-Dir=");
            newPropertiesFile.append("# Indicates the final revision");
            newPropertiesFile.append("-Dfr=");
            newPropertiesFile.append("# Url to access the project's SVN.");
            newPropertiesFile.append("# Url to access the project's SVN.");
            newPropertiesFile.append("# SVN username");
            newPropertiesFile.append("-Dsvn.username=");
            newPropertiesFile.append("# SVN password");
            newPropertiesFile.append("-Dsvn.password= ");
            newPropertiesFile.append("-Dshap.use.vcs.version=true");

            newPropertiesFile.append("# ---------- sonar-project-properties");
            newPropertiesFile.append("# Needed information to Sonar");
            newPropertiesFile.append("# check file definition on http://docs.codehaus.org/display/SONAR/Analyzing+with+Sonar+Runner");
            newPropertiesFile.append("# Required metadata");
            newPropertiesFile.append("#Project identifier");
            newPropertiesFile.append("-Dsonar.projectKey=");
            newPropertiesFile.append("# Project name");
            newPropertiesFile.append("-Dsonar.projectName=");
            newPropertiesFile.append("#");
            newPropertiesFile.append("# The value of the property must be the key of the language.");
            newPropertiesFile.append("-Dsonar.language=java");
            newPropertiesFile.append("# ---------- Optional Properties ");
            newPropertiesFile.append("#-Dsonar.projectVersion=x");
            newPropertiesFile.append("# optional description");
            newPropertiesFile.append("#-Dsonar.projectDescription=");
            newPropertiesFile.append("# Keep this if is a Maven Project");
            newPropertiesFile.append("-Dsonar.sources=src/main/java");
            newPropertiesFile.append("#");
            newPropertiesFile.append("# path to test source directories (optional)");
            newPropertiesFile.append("# Keep this if is a Maven Project");
            newPropertiesFile.append("-Dsonar.tests=src/test/java");

        } catch (IOException ex) {
            System.out.println("Error generating default properties file: " + ex.getMessage());
            System.exit(0);
        } finally {
            try {
                if (newPropertiesFile != null) {
                    newPropertiesFile.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(PropertiesFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
