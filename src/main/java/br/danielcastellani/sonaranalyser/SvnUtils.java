/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.sonaranalyser;

import java.util.LinkedList;
import java.util.List;
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

        CliUtils.exec(command, true);
    }

    static void update(Properties props, int revisionNumber) {
        final String svnHome = props.getProperty(Main.SVN_HOME);
        final String svnUsername = props.getProperty(Main.SVN_USERNAME);
        final String svnPassword = props.getProperty(Main.SVN_PASSWORD);

        String command = svnHome + "\\bin\\svn update -r" + revisionNumber;

        if (svnUsername != null && svnPassword != null) {
            command += " --username " + svnUsername + " --password " + svnPassword;
        }

        CliUtils.exec(command, true);
    }

    public static List<Integer> getRevisionsInRange(int initialRevision, int finalRevision, Properties props) {

        final String svnHome = props.getProperty(Main.SVN_HOME);
        final String svnUsername = props.getProperty(Main.SVN_USERNAME);
        final String svnPassword = props.getProperty(Main.SVN_PASSWORD);
        final String projectUrl = props.getProperty(Main.PROJECT_URL);

        String command = svnHome + "\\bin\\svn log -q -r" + initialRevision + ":" + finalRevision + " " + projectUrl;

        if (svnUsername != null && svnPassword != null) {
            command += " --username " + svnUsername + " --password " + svnPassword;
        }

        String svnLogOutput = CliUtils.exec(command, false);
        List<Integer> revisions = new LinkedList<Integer>();
        for (String line : svnLogOutput.split("\n")) {
            if (line.startsWith("r")) {
                revisions.add(getRevisionNumberFromSvnLogOutputLine(line));
            }
        }
        return revisions;
    }

    public static Integer getRevisionNumberFromSvnLogOutputLine(String line) {
        return Integer.parseInt(line.split(" ")[0].substring(1));
    }
}
