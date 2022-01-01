/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.hcmus.student.sv19127505.SlangDictionary;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Admin
 */
public class Highscore {

    ArrayList<Integer> hs;

    public Highscore() {
        hs = new ArrayList<>();
    }

    public void add(int score) {
        hs.add(score);
        Collections.sort(hs);
        Collections.reverse(hs);
        if(hs.size() == 6)
            hs.remove(5);
    }

    public String save(String filepath) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filepath)));
            oos.writeObject(hs);

            oos.close();
        } catch (IOException e) {
            return e.getMessage();
        }

        return "";
    }

    public String load(String filepath) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filepath)));

            hs = (ArrayList<Integer>) ois.readObject();

            ois.close();

        } catch (IOException | ClassNotFoundException e) {
            return e.getMessage();
        }

        return "";
    }
}
