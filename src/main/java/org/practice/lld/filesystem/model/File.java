package org.practice.lld.filesystem.model;

import javax.naming.OperationNotSupportedException;

public class File implements FileSystemUnit{

    String name;

    public File(String name) {
        this.name = name;
    }

    @Override
    public void ls() throws OperationNotSupportedException {
        System.out.println(name);
    }

    @Override
    public String getName() {
        return name;
    }
}
