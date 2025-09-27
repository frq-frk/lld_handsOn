package org.practice.lld.filesystem;

import org.practice.lld.filesystem.model.Directory;
import org.practice.lld.filesystem.model.FileSystemUnit;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        FileSystemUnit root = new Directory("root");

        FileSystem fs = new FileSystem((Directory) root);

        fs.makeDirectory("home");

        fs.makeDirectory("var");

        fs.changeDirectory("home");

        fs.touchFile("File1.text");

        fs.resetDirectory();

        fs.ls();
        fs.changeDirectory("home");
        fs.ls();
    }
}