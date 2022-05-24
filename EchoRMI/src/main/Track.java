package main;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class Track implements Serializable {
    private String name;
    private String author;
    private String length;
    private List<String> bandPlayers;
    @Serial
    private static final long serialVersionUID = 20220523L;

    public Track(String name, String author, String length, List<String> bandPlayers) {
        this.name = name;
        this.author = author;
        this.length = length;
        this.bandPlayers = bandPlayers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public List<String> getBandPlayers() {
        return bandPlayers;
    }

    public void setBandPlayers(List<String> bandPlayers) {
        this.bandPlayers = bandPlayers;
    }

    @Override
    public String toString() {
        return "Track{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", length='" + length + '\'' +
                ", bandPlayers=" + bandPlayers +
                '}';
    }
}
