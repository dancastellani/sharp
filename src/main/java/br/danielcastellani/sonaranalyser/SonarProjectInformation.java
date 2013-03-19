/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.sonaranalyser;

import java.io.File;

/**
 *
 * @author DanCastellani
 */
public class SonarProjectInformation {

    public File projectFolder;
    //Sonar properties(http://docs.codehaus.org/display/SONAR/Analyzing+with+Sonar+Runner):
    public String projectKey;
    public String projectName;
    public String projectVersion;
    public String projectDescription;
    public String sources;
    public String tests;
    public String binaries;
    public String libraries;
    public String language = "java";

    /**
     *
     * @param projectKey
     * @param projectName
     * @param projectVersion
     */
    public SonarProjectInformation(String projectKey, String projectName, String projectVersion) {
        this.projectKey = projectKey;
        this.projectName = projectName;
        this.projectVersion = projectVersion;
    }

    /**
     *
     * @param projectKey
     * @param projectName
     * @param projectVersion
     * @param projectDescription
     * @return
     */
    public static SonarProjectInformation createMavenProjectInformation(String projectKey, String projectName, String projectVersion, String projectDescription) {
        SonarProjectInformation projectInformation = new SonarProjectInformation(projectKey, projectName, projectVersion);

        projectInformation.sources = "src/main/java";
        projectInformation.tests = "src/test/java";
        projectInformation.projectDescription = projectDescription;

        return projectInformation;
    }

    /**
     *
     * @param projectKey
     * @param projectName
     * @param projectVersion
     * @return
     */
    public static SonarProjectInformation createMavenProjectInformation(String projectKey, String projectName, String projectVersion) {
        return createMavenProjectInformation(projectKey, projectName, projectVersion, "");
    }

    /**
     *
     * @param projectKey
     * @param projectName
     * @param projectVersion
     * @param projectFolder
     * @return
     */
    public static SonarProjectInformation createMavenProjectInformation(String projectKey, String projectName, String projectVersion, File projectFolder) {
        SonarProjectInformation projectInformation = createMavenProjectInformation(projectKey, projectName, projectVersion);
        projectInformation.projectFolder = projectFolder;
        return projectInformation;
    }
}