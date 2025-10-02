package org.practice.lld.doc_access_proxy;

import javax.print.Doc;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        Document pd1 = new ProxyDocument(new RealDocument("Doc1"));

        Document pd2 = new ProxyDocument(new RealDocument("Doc2"));

        Role r1 = new Role(Arrays.asList(new Permission(pd1, PermisionType.READ), new Permission(pd2, PermisionType.WRITE)));

        Role r2 = new Role(Arrays.asList(new Permission(pd2, PermisionType.READ), new Permission(pd1, PermisionType.WRITE)));


        User u1 = new User("user1", r1);

        User u2 = new User("user2", r2);

        pd1.read(u1);
        pd2.read(u2);

        pd1.write(u1);
        pd2.write(u2);

    }
}

enum PermisionType{
    READ, WRITE, MODIFY;
}

class User{
    String name;
    Role role;

    public User(String name, Role role) {
        this.name = name;
        this.role = role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

class Role{
    List<Permission> pList;

    public Role(List<Permission> pList) {
        this.pList = pList;
    }

    public void addPermission(Permission permission) {
        this.pList.add(permission);
    }

    public void removePermission(Permission permission){
        this.pList.remove(permission);
    }

    public Optional<Permission> getPermission(Document document){
        return pList.stream().filter(p -> p.doc.equals(document)).findFirst();
    }
}

class Permission{
    Document doc;
    PermisionType permisionType;

    public Permission(Document doc, PermisionType permisionType) {
        this.doc = doc;
        this.permisionType = permisionType;
    }

    public Document getDoc() {
        return doc;
    }

    public PermisionType getPermisionType() {
        return permisionType;
    }
}

interface Document{
    void read(User user);
    void write(User user);
}

class RealDocument implements Document{

    String name;

    public RealDocument(String name) {
        this.name = name;
    }

    @Override
    public void read(User user) {
        System.out.println("This is the document content of "+name);
    }

    @Override
    public void write(User user) {
        System.out.println("writing the document content of "+name);
    }

}

class ProxyDocument implements Document{

    Document realDocument;

    public ProxyDocument(Document realDocument) {
        this.realDocument = realDocument;
    }

    @Override
    public void read(User user) {
        if(Util.checkAccess(user, PermisionType.READ, this)){
            realDocument.read(user);
            return;
        }
    }

    @Override
    public void write(User user) {
        if(Util.checkAccess(user, PermisionType.WRITE, this)){
            realDocument.write(user);
            return;
        }
    }
}

class Util{
    static boolean checkAccess(User user, PermisionType permissionType, Document document){
        Optional<Permission> permission = user.role.getPermission(document);

        if(permission.isEmpty()) {
            System.out.println("Sorry You don't have an access");
            return false;
        }
        if(permission.get().getPermisionType().equals(permissionType))
            return  true;

        System.out.println("Sorry You don't have an access");
        return false;
    }
}
