package com.github.carloscontrerasruiz.custom_compliance;

import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

public interface ICheckMetadata {
    void checkMetadata(MavenProject project, Log log, String groupId, String javaVersion) throws MojoFailureException;
}
