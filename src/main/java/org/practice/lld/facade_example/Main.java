package org.practice.lld.facade_example;

// Client Code
public class Main {
    public static void main(String[] args) {
        DVDPlayer dvdPlayer = new DVDPlayer();
        Amplifier amplifier = new Amplifier();
        Projector projector = new Projector();

        HomeTheaterFacade homeTheater = new HomeTheaterFacade(dvdPlayer, amplifier, projector);

        homeTheater.watchMovie("The Matrix");
        homeTheater.endMovie();
    }
}

// Subsystem Component 1: DVD Player
class DVDPlayer {
    public void on() {
        System.out.println("DVD Player is ON");
    }

    public void play(String movie) {
        System.out.println("Playing movie: " + movie);
    }

    public void off() {
        System.out.println("DVD Player is OFF");
    }
}

// Subsystem Component 2: Amplifier
class Amplifier {
    public void on() {
        System.out.println("Amplifier is ON");
    }

    public void setVolume(int level) {
        System.out.println("Setting amplifier volume to " + level);
    }

    public void off() {
        System.out.println("Amplifier is OFF");
    }
}

// Subsystem Component 3: Projector
class Projector {
    public void on() {
        System.out.println("Projector is ON");
    }

    public void wideScreenMode() {
        System.out.println("Projector in widescreen mode");
    }

    public void off() {
        System.out.println("Projector is OFF");
    }
}

// Facade Class: HomeTheaterFacade
class HomeTheaterFacade {
    private DVDPlayer dvdPlayer;
    private Amplifier amplifier;
    private Projector projector;

    public HomeTheaterFacade(DVDPlayer dvdPlayer, Amplifier amplifier, Projector projector) {
        this.dvdPlayer = dvdPlayer;
        this.amplifier = amplifier;
        this.projector = projector;
    }

    public void watchMovie(String movie) {
        System.out.println("\nGet ready to watch a movie...");
        projector.on();
        projector.wideScreenMode();
        amplifier.on();
        amplifier.setVolume(7);
        dvdPlayer.on();
        dvdPlayer.play(movie);
    }

    public void endMovie() {
        System.out.println("\nShutting down home theater...");
        dvdPlayer.off();
        amplifier.off();
        projector.off();
    }
}
