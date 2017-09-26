## Fragment Maven Plugin

You can sparate the pom.xml in other xml for eaxh plugin taht use in your project.

The fragment-maven-plugin execute of configuration declared in other xml and only apply plugin configuration like at project>build>plugins>plugin

### Configuration

Include de fragment-maven-plugin in your project and declare all xml fragment that you execute
### pom.xml
```
	<build>
    ...
		<plugins>
      ...
			<plugin>
				<groupId>dev.yracnet.maven</groupId>
				<artifactId>fragment-maven-plugin</artifactId>
				<version>0.1.1-SNAPSHOT</version>
				<executions>
					<execution>
						<phase>process-resources</phase>
						<goals>
							<goal>process</goal>							
						</goals>
					</execution>
				</executions>
				<configuration>
					<skip>false</skip>
					<fragments>
						<fragment>${basedir}/plugin/formatter.xml</fragment>
						.... More xml
					</fragments>
				</configuration>
			</plugin>
      ...
		</plugins>
    ...
	</build>
```

### plugin/formatter.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<plugin>
	<groupId>net.revelc.code</groupId>
	<artifactId>formatter-maven-plugin</artifactId>
	<version>0.5.2</version>
	<executions>
		<execution>
			<goals>
				<goal>format</goal>
			</goals>
		</execution>
	</executions>
	<configuration>
		<lineEnding>CRLF</lineEnding>
		<encoding>UTF-8</encoding>
	</configuration>
</plugin>
```


### Contact

If yout have any cuestion, send a email to yracnet@gmail.com.
