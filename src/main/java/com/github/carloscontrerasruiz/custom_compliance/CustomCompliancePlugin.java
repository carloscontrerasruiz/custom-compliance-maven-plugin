package com.github.carloscontrerasruiz.custom_compliance;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import javax.inject.Inject;
import java.io.File;

/**
 * plugin para verificar el compliance de ciertos campos
 */
@Mojo(name = "customCompliance", defaultPhase = LifecyclePhase.COMPILE)
public class CustomCompliancePlugin extends AbstractMojo {

    /**
     * Version de java esperada
     */
    @Parameter(defaultValue = "11")
    private String javaVersion;

    /**
     * Nombre paquete esperado
     */
    @Parameter(required = true)
    private String groupId;

    @Parameter(defaultValue = "${project}")
    private MavenProject project;

    @Parameter(defaultValue = "${project.build.directory}", readonly = true)
    private File target;

    @Inject
    private ICheckMetadata checkMetadata;

    //MojoExecution build error
    //MojoFailure build fail
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("customCompliance plugin started");

        checkMetadata.checkMetadata(project, getLog(), groupId, javaVersion);

        getLog().info(target.toString());
        FileOperations.createDefaultFile(target, project.getArtifactId() + ".txt", getLog(), project.getVersion());


        getLog().info("customCompliance plugin ended");
    }

    /**
     *
     *<plugin>
     *                 <groupId>com.github.carloscontrerasruiz</groupId>
     *                 <artifactId>custom-compliance-maven-plugin</artifactId>
     *                 <version>1.0.0</version>
     *                 <configuration>
     *                     <packageName>test</packageName>
     *                     <javaVersion>11</javaVersion>
     *                 </configuration>
     *                 <executions>
     *                     <execution>
     *                         <goals>
     *                             <goal>customCompliance</goal>
     *                         </goals>
     *                     </execution>
     *                 </executions>
     *             </plugin>
     */

    /*
     * mvn clean install
     *  mvn -D packageName=algo com.github.carloscontrerasruiz:custom-compliance-maven-plugin:customCompliance
     *
     * */

}
