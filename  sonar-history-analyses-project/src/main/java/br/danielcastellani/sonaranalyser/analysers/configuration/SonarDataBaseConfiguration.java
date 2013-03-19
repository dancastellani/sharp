/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.sonaranalyser.analysers.configuration;

/**
 *
 * @author DanCastellani
 */
public class SonarDataBaseConfiguration implements SonarConfiguration {

    private String databaseIp;
    private String databasePort;
    private String databaseLogin;
    private String databasePassword;
    private String databaseDatabaseName;

    /**
     * 
     * @param databaseIp
     * @param databasePort
     * @param databaseLogin
     * @param databasePassword
     * @param databaseDatabaseName
     */
    public SonarDataBaseConfiguration(String databaseIp, String databasePort, String databaseLogin, String databasePassword, String databaseDatabaseName) {
        this.databaseIp = databaseIp;
        this.databasePort = databasePort;
        this.databaseLogin = databaseLogin;
        this.databasePassword = databasePassword;
        this.databaseDatabaseName = databaseDatabaseName;
    }
}
