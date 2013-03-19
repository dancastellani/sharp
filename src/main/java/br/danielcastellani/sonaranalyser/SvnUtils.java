/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.sonaranalyser;

import java.util.Properties;

/**
 *
 * @author DanCastellani
 */
public class SvnUtils {

    static void checkout(Properties props, int revisionNumber) {
        final String svnHome = props.getProperty(Main.SVN_HOME);
        final String svnUsername = props.getProperty(Main.SVN_USERNAME);
        final String svnPassword = props.getProperty(Main.SVN_PASSWORD);
        final String projectUrl = props.getProperty(Main.PROJECT_URL);
        final String destinationFolder = props.getProperty(Main.DESTINATION_FOLDER);

        String command = svnHome + "\\bin\\svn checkout -r" + revisionNumber + " " + projectUrl + " " + destinationFolder;

        if (svnUsername != null && svnPassword != null) {
            command += " --username " + svnUsername + " --password " + svnPassword;
        }

        CliUtils.exec(command);
    }

    static void update(Properties props, int revisionNumber) {
        final String svnHome = props.getProperty(Main.SVN_HOME);
        final String svnUsername = props.getProperty(Main.SVN_USERNAME);
        final String svnPassword = props.getProperty(Main.SVN_PASSWORD);
        final String projectUrl = props.getProperty(Main.PROJECT_URL);
        final String destinationFolder = props.getProperty(Main.DESTINATION_FOLDER);

        String command = svnHome + "\\bin\\svn update -r" + revisionNumber;

        if (svnUsername != null && svnPassword != null) {
            command += " --username " + svnUsername + " --password " + svnPassword;
        }

        CliUtils.exec(command);
    }
}
