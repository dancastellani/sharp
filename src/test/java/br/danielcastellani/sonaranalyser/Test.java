package br.danielcastellani.sonaranalyser;

import java.util.ArrayList;
import java.util.List;
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

    public void testExecuteFromFile() {
        List<String> props = new ArrayList<String>();
        props.add("-F E:\\DanCastellani\\Documents\\My Dropbox\\Projetos\\Sonar Hystory Analysis Project\\publico-core.configuration");

        Main.main(props.toArray(new String[0]));
    }
}
