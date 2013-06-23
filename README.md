Sharp
=====

Sonar History AnalyseR Project - is a Project to analyse a software's history with Sonar.

Hot It Works
---

Sharp get each revision stored on the project's Version Control System and analyses it with Sonar-Runner.

To use it you need:
-------------------

1. Install SVN on your machine
2. Download Sonar-Runner (https://github.com/SonarSource/sonar-runner) and config it to Sonar's database.
3. Create a Sharp properties file to describe the project
4. Run Sharp with -f <properties-file-path> (created on 3)

A Sharp properties file 
-------------------

It should be as the one's below, used to analyse Sharp's early project on GoogleCode:

<pre>
  # ------------------- Sharp properties
  #-Dir=0
  #-Dfr=HEAD
  -Dsvn.home=D:\svn-win32-1.6.6
  -Dproject.url=https://sonar-history-analyses-project.googlecode.com/svn/trunk/
  -Dsonar.runner=E:\teste-sonar-iduff\sonar-runner-2.0
  #-Dproperties.file=E:\DanCastellani\Documents\My Dropbox\Projetos\Sonar Hystory Analysis Project\publico-core-sonar-project.properties
  #If login and password are not set, Shap will try anonymous access.
  #-Dsvn.username=
  #-Dsvn.password=
  -Dshap.use.vcs.version=true
  #---------------------- sonar-project-properties
  # check file definition on http://docs.codehaus.org/display/SONAR/Analyzing+with+Sonar+Runner
  # required metadata
  -Dsonar.projectKey=br.com.danielcastellani.Sonar-History-Analysis-Project
  -Dsonar.projectName=Sonar History Analysis Project
  #
  #-Dsonar.projectVersion=x
  # optional description
  #-Dsonar.projectDescription=
  -Dsonar.sources=src/main/java
  #
  # path to test source directories (optional)
  -Dsonar.tests=src/test/java
  #
  # The value of the property must be the key of the language.
  -Dsonar.language=java
</pre>
  
Quality
---

Codeship: https://www.codeship.io/projects/4498
  
Need help? or It doesnt work properly?
---

Send me and email! =D
  
