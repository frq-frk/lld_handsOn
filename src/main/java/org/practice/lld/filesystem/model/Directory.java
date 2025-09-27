package org.practice.lld.filesystem.model;

import java.util.ArrayList;
import java.util.List;

public class Directory implements FileSystemUnit{

    String name;

    public Directory(String name) {
        this.name = name;
    }

    List<FileSystemUnit> childeren = new ArrayList<>();

    @Override
    public void ls() {
        for(FileSystemUnit f : childeren){
            System.out.print(f.getName()+"  ");
        }
    }

    @Override
    public String getName() {
        return (name);
    }

    public Directory findByName(String name){
        for(FileSystemUnit f : childeren){
            if(f.getName().equals(name) && f instanceof Directory) return (Directory) f;
        }
        return null;
    }

    public void addDirectory(String name){
        childeren.add(new Directory(name));
    }

    public void touchFile(String name){
        childeren.add(new File(name));
    }
}
