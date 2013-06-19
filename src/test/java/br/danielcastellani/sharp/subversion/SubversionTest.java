package br.danielcastellani.sharp.subversion;

import br.danielcastellani.sharp.Main;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class SubversionTest extends TestCase {

    public void testGetRevisionNumberFromSvnLogOutputLine() {
        final String anOutputLine = "r106 | beto | 2009-09-10 18:50:36 -0300 (qui, 10 set 2009)";
        int response = SubversionService.getRevisionNumberFromSvnLogOutputLine(anOutputLine);
        Assert.assertEquals(106, response);
    }

    public void testGetRevisionDateFromSvnLogOutputLine() {
        final String anOutputLine = "r1 | (no author) | 2013-03-18 22:34:37 -0300 (seg, 18 mar 2013) | 1 line";
        Calendar response = SubversionService.getRevisionDateFromSvnLogOutputLine(anOutputLine);
        Assert.assertEquals(new GregorianCalendar(2013, 02, 18), response);
    }

    public void testGetFormatedDateFromRevision() {
        Revision revision = new Revision();
        revision.date = new GregorianCalendar(2013, 02, 18);
        System.out.println(revision.getFormatedDate());
        Assert.assertEquals("2013-03-18", revision.getFormatedDate());
    }

    public void testGetRevisionsInRange() {
        Properties props = new Properties();
        props.put(Main.PROJECT_URL, "https://sonar-history-analyses-project.googlecode.com/svn/trunk/");
        props.put(Main.SVN_HOME, "D:/svn-win32-1.6.6");
        int initialRevision = 1;
        int finalRevision = 10;

        List<Revision> response = SubversionService.getRevisionsInRange(initialRevision, finalRevision, props);
        Assert.assertTrue("Empty revisions range", !response.isEmpty());
    }
}
