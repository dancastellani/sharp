package br.danielcastellani.sonaranalyser;

import java.util.List;
import java.util.Properties;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class Test extends TestCase {

//    public void testExecute() {
//        List<String> props = new ArrayList<String>();
//        props.add("-Dir=1");
//        props.add("-Dfr=2");
//        props.add("-Dsvn.home=D:\\svn-win32-1.6.6");
//        props.add("-Dproject.url=https://sonar-history-analyses-project.googlecode.com/svn/trunk/");
//
//        props.add("-Dsonar.runner=E:\\teste-sonar-iduff\\sonar-runner-2.0");
//        props.add("-Dproperties.file=E:\\DanCastellani\\Documents\\NetBeansProjects\\SonarAnalyser\\sonar-project.properties");
//
////        System.out.println("props = " + props);
//        Main.main(props.toArray(new String[0]));
//    }
    public void testGetRevisionNumberFromSvnLogOutputLine() {
        final String anOutputLine = "r106 | beto | 2009-09-10 18:50:36 -0300 (qui, 10 set 2009)";
        int response = SvnUtils.getRevisionNumberFromSvnLogOutputLine(anOutputLine);
        Assert.assertEquals(106, response);
    }

    public void testGetRevisionsInRange() {
        Properties props = new Properties();
        props.put(Main.PROJECT_URL, "https://sonar-history-analyses-project.googlecode.com/svn/trunk/");
        props.put(Main.SVN_HOME, "D:/svn-win32-1.6.6");
        int initialRevision = 1;
        int finalRevision = 10;

        List<Integer> response = SvnUtils.getRevisionsInRange(initialRevision, finalRevision, props);
        Assert.assertEquals(10, response.size());
    }
//    public void testExecuteFromFile() {
//        List<String> props = new ArrayList<String>();
//        props.add("-F");
//        props.add("E:/DanCastellani/Documents/My Dropbox/Projetos/Sonar Hystory Analysis Project/publico-core.configuration");
//
//        Main.main(props.toArray(new String[0]));
//    }
}
