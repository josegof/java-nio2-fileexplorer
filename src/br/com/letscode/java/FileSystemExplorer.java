package br.com.letscode.java;

import static java.nio.file.Files.newDirectoryStream;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Arrays;
import java.util.Comparator;

public class FileSystemExplorer {

    private final Path path;

    public FileSystemExplorer(Path path){
        this.path = path;
        this.init();
    }

    private void init(){
        System.out.println("Analisando o caminho: " + path.toAbsolutePath().toString());
        System.out.println("Contido em: " + path.getParent().toAbsolutePath().toString());
    }

    public void printDirectoryContent() {
        //try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.path)) {
        try (var stream = newDirectoryStream(this.path)) {
            for (Path file : stream) {
                System.out.println(file.getFileName());
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void filterFilesWithExtension(String extension) {
        this.filterFilesWithExtension(this.path, extension);
    }

    private void filterFilesWithExtension(Path path, String extension) {
        File file = path.toFile();
        File[] matchingFiles = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                File f = Paths.get(dir.getPath()+File.separator+name).toFile();
                if (f.isDirectory()) {
                    filterFilesWithExtension(Paths.get(f.getPath()), extension);
                }
                return name.endsWith(extension);
            }
        });
        if (matchingFiles == null) {
            System.out.println("Nenhum resultado encontrado");
            return;
        }
        for (File f : matchingFiles){
            System.out.println(f.getParent()+": "+f.getName());
        }
    }

    public void listFilesOrderByModificationData() {
        File directory = this.path.toFile();
        File[] directoryFileContent = directory.listFiles();
        if (directoryFileContent == null) {
            System.out.println("Nenhum resultado encontrado");
            return;
        }

        //Arrays.sort(directoryFileContent, new Comparator<File>() {
        //    @Override
        //    public int compare(File f1, File f2){
        //        return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
        //    }
        //});

        Arrays.sort(directoryFileContent, Comparator.comparingLong(File::lastModified);

        for (File file : directoryFileContent) {
            if (file.isHidden()) {
                continue;
            }
            String fileName = file.getName();
            FileTime lastModified = FileTime.fromMillis(file.lastModified());
            System.out.printf("[%s] %s\n", lastModified.toString(), fileName);
        }
    }
}