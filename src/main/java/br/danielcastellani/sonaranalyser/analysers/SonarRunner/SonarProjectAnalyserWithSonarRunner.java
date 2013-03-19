/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.sonaranalyser.analysers.SonarRunner;

import br.danielcastellani.sonaranalyser.analysers.SonarProjectAnalyser;
import br.danielcastellani.sonaranalyser.analysers.SonarProjectInformation;
import br.danielcastellani.sonaranalyser.analysers.configuration.SonarConfiguration;
import java.io.File;
import org.sonar.runner.Main;
import org.sonar.runner.Runner;

/**
 *
 * @author DanCastellani
 */
public class SonarProjectAnalyserWithSonarRunner extends SonarProjectAnalyser {

    /**
     * 
     * @param projectInformation
     * @param configuration
     */
    public SonarProjectAnalyserWithSonarRunner(SonarProjectInformation projectInformation, SonarConfiguration configuration) {
        super(projectInformation, configuration);
    }

    /**
     * This method creates the configuration needed to run the Sonar Runner.
     */
    protected void configureSonarOnProject(SonarConfiguration sonarConfiguration) {
        SonarProjectProperties.createDeafaulSonarProjectPropertiesFile(projectInformation);
    }

    /**
     * This method calls the Sonar Runner to analyse the project.
     */
    protected void analyseWithSonar() {
    }
}
