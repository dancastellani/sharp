/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.sharp.subversion;

import br.danielcastellani.sharp.Main;
import br.danielcastellani.sharp.util.CliUtils;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

    public static List<Revision> getRevisionsInRange(Integer initialRevision, Integer finalRevision, Properties props) {

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
        List<Revision> revisions = new LinkedList<Revision>();
        for (String line : svnLogOutput.split("\n")) {
            if (line.startsWith("r")) {
                Revision oneRevision = new Revision();
                oneRevision.number = getRevisionNumberFromSvnLogOutputLine(line);
                oneRevision.date = getRevisionDateFromSvnLogOutputLine(line);
                revisions.add(oneRevision);
            }
        }
        return revisions;
    }

    public static Integer getRevisionNumberFromSvnLogOutputLine(String line) {
        return Integer.parseInt(line.split(" ")[0].substring(1));
    }

    public static Calendar getRevisionDateFromSvnLogOutputLine(String line) {
        //r1 | (no author) | 2013-03-18 22:34:37 -0300 (seg, 18 mar 2013) | 1 line
        String dateInformation = line.split("\\|")[2].trim();
        //2013-03-18 22:34:37 -0300 (seg, 18 mar 2013) 
        dateInformation = dateInformation.split(" ")[0];
        final int year = Integer.parseInt(dateInformation.split("-")[0]);
        final int month = Integer.parseInt(dateInformation.split("-")[1])-1;
        final int dayOfMonth = Integer.parseInt(dateInformation.split("-")[2]);

        return new GregorianCalendar(year, month, dayOfMonth);
    }
}
