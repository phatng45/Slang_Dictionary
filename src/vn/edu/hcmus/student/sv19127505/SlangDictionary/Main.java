package vn.edu.hcmus.student.sv19127505.SlangDictionary;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
//        Dictionary dict = new Dictionary();
//        System.out.println(dict.reset("slang.txt"));
//
//        System.out.println(dict.save("data\\updatedDict.ser"));
//
        Dictionary dict = new Dictionary();
//        dict.load("data\\updatedDict.ser");

        dict.reset("slang.txt");
        System.out.println(dict.words.size());
    }
}
