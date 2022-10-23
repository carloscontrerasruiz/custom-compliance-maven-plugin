package com.github.carloscontrerasruiz.custom_compliance;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class FileOperations {

    public static void createDefaultFile(File path, String filename, Log log, String version) throws MojoExecutionException {
        log.info("Inicia la creacion de el archivo");
        File newFile = new File(path, filename);
        try {
            newFile.createNewFile();
            version = version + System.lineSeparator();
            Files.write(newFile.toPath(), version.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            log.info("File generated");
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new MojoExecutionException(e.getMessage());
        }
    }
}
