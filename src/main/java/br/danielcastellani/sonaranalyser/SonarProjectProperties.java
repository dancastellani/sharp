///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package br.danielcastellani.sonaranalyser.analysers.SonarRunner;
//
//import br.danielcastellani.sonaranalyser.analysers.SonarProjectInformation;
//import br.danielcastellani.sonaranalyser.exception.SonarAnalyserException;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.FileWriter;
//import java.io.IOException;
//
///**
// *
// * @author DanCastellani
// */
//public class SonarProjectProperties {
//
//    private static final String SONAR_PROJECT_PROPERTIES_FILE_NAME = "sonar-project.properties";
//    private static final String BREAK_LINE = "\n";
//
//    /**
//     *
//     * @param projectInformation
//     */
//    static void createDeafaulSonarProjectPropertiesFile(SonarProjectInformation projectInformation) {
//        File propertiesFile = new File(projectInformation.projectFolder.getAbsolutePath() + File.separator + SONAR_PROJECT_PROPERTIES_FILE_NAME);
//        if (!propertiesFile.exists()) {
//            createNewPropertiesFile(propertiesFile, projectInformation);
//        }
//    }
//
//    /**
//     *
//     * @param propertiesFile
//     * @param projectInformation
//     */
//    private static void createNewPropertiesFile(File propertiesFile, SonarProjectInformation projectInformation) {
//        try {
//            propertiesFile.createNewFile();
//
//            StringBuffer content = new StringBuffer();
//            content.append("# check file definition at http://docs.codehaus.org/display/SONAR/Analyzing+with+Sonar+Runner").append(BREAK_LINE);
//            content.append("# required metadata").append(BREAK_LINE);
//            content.append("sonar.projectKey=").append(projectInformation.projectKey).append(BREAK_LINE);
//            content.append("sonar.projectName=").append(projectInformation.projectName).append(BREAK_LINE);
//            content.append("sonar.projectVersion=").append(projectInformation.projectVersion).append(BREAK_LINE);
//            content.append(BREAK_LINE);
//            content.append("# optional description").append(BREAK_LINE);
//            content.append("sonar.projectDescription=").append(projectInformation.projectDescription).append(BREAK_LINE);
//            content.append("sonar.sources=").append(projectInformation.sources).append(BREAK_LINE);
//            content.append(BREAK_LINE);
//            content.append("# path to test source directories (optional)").append(BREAK_LINE);
//            content.append("sonar.tests=").append(projectInformation.tests).append(BREAK_LINE);
//            content.append(BREAK_LINE);
//            content.append("# path to project binaries (optional), for example directory of Java bytecode").append(BREAK_LINE);
//            content.append("sonar.binaries=").append(projectInformation.binaries).append(BREAK_LINE);
//            content.append(BREAK_LINE);
//            content.append("# optional comma-separated list of paths to libraries. Only path to JAR file is supported.").append(BREAK_LINE);
//            content.append("sonar.libraries=").append(projectInformation.libraries).append(BREAK_LINE);
//            content.append(BREAK_LINE);
//            content.append("# The value of the property must be the key of the language.").append(BREAK_LINE);
//            content.append("sonar.language=").append(projectInformation.language).append(BREAK_LINE);
//
//            FileWriter fw = new FileWriter(propertiesFile);
//            fw.write(content.toString());
//
//            fw.close();
//        } catch (IOException ex) {
//            throw new SonarAnalyserException(ex);
//        }
//    }
//}