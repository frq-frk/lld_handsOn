package org.practice.lld.filesystem.model;

import javax.naming.OperationNotSupportedException;

public interface FileSystemUnit{
    public void ls() throws OperationNotSupportedException;
    public String getName();
}