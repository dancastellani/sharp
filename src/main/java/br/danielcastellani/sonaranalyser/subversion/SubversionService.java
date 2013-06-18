/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.sonaranalyser.subversion;

import br.danielcastellani.sonaranalyser.Main;
import br.danielcastellani.sonaranalyser.util.CliUtils;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author DanCastellani
 */
public class SubversionService {

    public static void checkout(Properties props, int revisionNumber) {
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

    public static void update(Properties props, int revisionNumber) {
        final String svnHome = props.getProperty(Main.SVN_HOME);
        final String svnUsername = props.getProperty(Main.SVN_USERNAME);
        final String svnPassword = props.getProperty(Main.SVN_PASSWORD);

        String command = svnHome + "\\bin\\svn update -r" + revisionNumber;

        if (svnUsername != null && svnPassword != null) {
            command += " --username " + svnUsername + " --password " + svnPassword;
        }

        CliUtils.exec(command, true);
    }

    public static List<Integer> getRevisionsInRange(Integer initialRevision, Integer finalRevision, Properties props) {

        final String svnHome = props.getProperty(Main.SVN_HOME);
        final String svnUsername = props.getProperty(Main.SVN_USERNAME);
        final String svnPassword = props.getProperty(Main.SVN_PASSWORD);
        final String projectUrl = props.getProperty(Main.PROJECT_URL);


        StringBuilder command = new StringBuilder(svnHome + "\\bin\\svn log -q ");

        if (initialRevision != null || finalRevision != null) {
            final String strFinalRevision = finalRevision != null ? finalRevision.toString() : "HEAD";
            command.append("-r").append(initialRevision).append(":").append(strFinalRevision);
        }

        command.append(" ").append(projectUrl);
        if (svnUsername != null && svnPassword != null) {
            command.append(" --username ").append(svnUsername).append(" --password ").append(svnPassword);
        }

        String svnLogOutput = CliUtils.exec(command.toString(), false);
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
