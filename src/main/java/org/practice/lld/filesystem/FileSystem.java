package org.practice.lld.filesystem;

import org.practice.lld.filesystem.model.Directory;

public class FileSystem {

    Directory currentWorkingDirectory;

    Directory rootDirectory;

    public FileSystem(Directory currentWorkingDirectory) {
        this.currentWorkingDirectory = currentWorkingDirectory;
        this.rootDirectory = currentWorkingDirectory;
    }

    public void changeDirectory(String path){
        String [] dir = path.split("/");
        Directory temp = currentWorkingDirectory;

        for(String d : dir){
            Directory nextDir = temp.findByName(d);
            if(nextDir != null){
                temp = nextDir;
            }else {
                System.out.println("invalid directory path, please check");
                return;
            }
        }
        currentWorkingDirectory = temp;
    }

    public void makeDirectory(String name){
        currentWorkingDirectory.addDirectory(name);
    }

    public void touchFile(String name){
        currentWorkingDirectory.touchFile(name);
    }

    public void resetDirectory(){
        currentWorkingDirectory = rootDirectory;
    }

    public void ls(){
        System.out.println("Files in current working directory "+currentWorkingDirectory.getName()+" : ");
        currentWorkingDirectory.ls();
        System.out.println();
    }

}
