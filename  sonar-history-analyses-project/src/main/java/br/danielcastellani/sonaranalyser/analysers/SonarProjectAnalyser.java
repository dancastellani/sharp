/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.sonaranalyser.analysers;

import br.danielcastellani.sonaranalyser.analysers.configuration.SonarConfiguration;
import java.io.File;

/**
 * Abstract class to define the algorithm to analyse a project.
 * @author DanCastellani
 */
public abstract class SonarProjectAnalyser {

    protected SonarConfiguration sonarConfiguration;
    protected SonarProjectInformation projectInformation;

    /**
     *
     * @param projectInformation
     * @param sonarConfiguration
     */
    protected SonarProjectAnalyser(SonarProjectInformation projectInformation, SonarConfiguration sonarConfiguration) {
        this.sonarConfiguration = sonarConfiguration;
        this.projectInformation = projectInformation;
    }

    // Implemented with design pattern template method to facilitate segregates the algorithm and its implementation.
    // I chose to do this since now i'm going to implement with SonarRunner, but latter i think its best to implement with maven.
    public final void analyseWithoutConfiguration() {
        configureSonarOnProject(sonarConfiguration);
        analyseWithSonar();
    }

    /*
     *
     */
    abstract protected void configureSonarOnProject(SonarConfiguration sonarConfiguration);

    /**
     * 
     */
    abstract protected void analyseWithSonar();
}
