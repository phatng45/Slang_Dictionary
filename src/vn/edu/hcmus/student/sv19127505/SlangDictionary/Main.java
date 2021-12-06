package vn.edu.hcmus.student.sv19127505.SlangDictionary;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Dictionary dict = new Dictionary();
        System.out.println(dict.reset("slang.txt"));

        System.out.println(dict.save("data\\updatedDict.ser"));

        System.out.println(dict.load("data\\updatedDict.ser"));

        //dict.printWords();
        //dict.printPartials();
    }
}
