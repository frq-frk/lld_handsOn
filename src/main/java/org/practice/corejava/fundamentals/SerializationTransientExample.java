package org.practice.corejava.fundamentals;

import java.io.*;

public class SerializationTransientExample {
    public static void main(String[] args) {
        User user = new User("john_doe", "mySecretPassword123", 30);

        try {
            // Serialize the user object to a file
            FileOutputStream fileOut = new FileOutputStream("user.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(user);
            out.close();
            fileOut.close();
            System.out.println("Serialized user: " + user);

            // Deserialize the user object from the file
            FileInputStream fileIn = new FileInputStream("user.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            User deserializedUser = (User) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Deserialized user: " + deserializedUser);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

// The class must implement Serializable to be serializable
class User implements Serializable {
    private String username;
    private transient String password; // Marked as transient, will not be serialized
    private int age;

    public User(String username, String password, int age) {
        this.username = username;
        this.password = password;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{username='" + username + "', password='" + password + "', age=" + age + '}';
    }
}

