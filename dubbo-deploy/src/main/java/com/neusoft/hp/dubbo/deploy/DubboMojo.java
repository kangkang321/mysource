package com.neusoft.hp.dubbo.deploy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Hello world!
 */
@Mojo(name = "dubbo")
public class DubboMojo extends AbstractMojo {

    @Parameter(property = "target", defaultValue = "${project.build.directory}")
    String target;

    @SuppressWarnings("resource")
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        // String cmdPath = "";
        try {
            Iterator<File> ites = FileUtils.iterateFiles(FileUtils.getFile(target), new String[] { "tar.gz" }, false);
            File tar = ites.next();
            TarArchiveInputStream input = new TarArchiveInputStream(new GZIPInputStream(FileUtils.openInputStream(tar)));
            ArchiveEntry entity = null;
            while ((entity = input.getNextEntry()) != null) {
                if (entity.isDirectory()) {
                    FileUtils.forceMkdir(FileUtils.getFile(new File(target), entity.getName()));
                } else {
                    File file = FileUtils.getFile(FileUtils.getFile(target), entity.getName());
                    if (!file.getParentFile().exists()) {
                        FileUtils.forceMkdir(file.getParentFile());
                    }
                    // FIXME window系统下
                    // if (entity.getName().endsWith("start.bat")) {
                    // cmdPath = file.getParent();
                    // }
                    byte[] bytes = new byte[1024];
                    int len;
                    OutputStream out = new FileOutputStream(file);
                    while ((len = input.read(bytes)) > 0) {
                        out.write(bytes, 0, len);
                    }
                    out.flush();
                    out.close();
                }
            }
            // Runtime.getRuntime().exec("cmd.exe /C start cmd /k \"cd " + cmdPath + "&&start.bat debug\"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
