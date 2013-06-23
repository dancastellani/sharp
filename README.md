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
  # check file definition on http://docs.codehaus.org/display/SONAR/Analyzing+with+Sonar+Runner
  # required metadata
  sonar.projectKey=br.com.danielcastellani
  sonar.projectName=SonarAnalyser-Test
  sonar.projectVersion=1.0-snapshot
  
  # optional description
  sonar.projectDescription=
  sonar.sources=src/main/java
  
  # path to test source directories (optional)
  sonar.tests=src/test/java
  
  # The value of the property must be the key of the language.
  sonar.language=java
</pre>
  
Need help? or It doesnt work properly?
---

Send me and email! =D
  
