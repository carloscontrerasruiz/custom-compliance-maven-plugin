package com.github.carloscontrerasruiz.custom_compliance;

import org.apache.maven.model.Organization;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public class CheckMetadataImpl implements ICheckMetadata {

    @Override
    public void checkMetadata(MavenProject project, Log log, String groupId, String javaVersion) throws MojoFailureException {
        log.info("Inicia elcompliance check");
        //Check packaging
        if (isNullOrEmpty(project.getPackaging()) || !project.getPackaging().equalsIgnoreCase("jar"))
            throw new MojoFailureException("The packaging must be jar");

        //Check groupId
        if (isNullOrEmpty(project.getGroupId()) || !project.getGroupId().equalsIgnoreCase(groupId))
            throw new MojoFailureException("The groupId does not meet the compliance");

        //Check description
        if (isNullOrEmpty(project.getDescription()) || project.getDescription().length() < 5)
            throw new MojoFailureException("The description is a must and should be grater than 5 characters");

        //Check version
        if (isNullOrEmpty(project.getVersion()) || project.getVersion().contains("SNAPSHOT"))
            throw new MojoFailureException("The version is a must and should not contains the SNAPSHOT word");

        //Check artifactId
        if (isNullOrEmpty(project.getArtifactId()))
            throw new MojoFailureException("The artifactId is a must");

        //Check java version
        if (project.getProperties() == null || !project.getProperties().getProperty("java.version").equalsIgnoreCase(javaVersion))
            throw new MojoFailureException("The java version must be " + javaVersion);

        //Check organization
        if (!isOrganizationDataComplete(project.getOrganization()))
            throw new MojoFailureException("The organization data don't meet the compliance rules");

        //Solo si el proyecto no es Spring boot
        //log.info(project.getProperties().getProperty("maven.compiler.target"));
    }

    private boolean isNullOrEmpty(String property) {
        return property == null || property.isEmpty();
    }

    private boolean isOrganizationDataComplete(Organization organization) {
        if (organization == null) return false;
        if (!organization.getName().equalsIgnoreCase("The organization")) return false;
        if (!organization.getUrl().equalsIgnoreCase("The url")) return false;
        return true;
    }
}
