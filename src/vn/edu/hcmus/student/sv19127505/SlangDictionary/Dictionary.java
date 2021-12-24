package vn.edu.hcmus.student.sv19127505.SlangDictionary;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class Dictionary {

    HashMap<String, HashSet<String>> words = new HashMap<>();
    HashMap<String, HashSet<String>> partials = new HashMap<>();
    boolean isSaved = true;

    public void put(String newWord, String newDefinition) {
        if (!words.containsKey(newWord)) {
            words.put(newWord, new HashSet<>());
        }
        words.get(newWord).add(newDefinition);

        for (String newPartial : newDefinition.toLowerCase().split(" ")) {
            if (!partials.containsKey(newPartial)) {
                partials.put(newPartial, new HashSet<>());
            }
            partials.get(newPartial).add(newWord);
        }

        isSaved = false;
    }

    public void put(String newWord, String[] newDefinitions) {
        for (String d : newDefinitions)
            put(newWord, d);
    }

    public HashSet<String> getDefinitions(String word) {
        return words.get(word);
    }

    public HashSet<String> getWords(String definition) {
        String[] partialsToGet = definition.toLowerCase().split(" ");
        HashSet<String> wordsToGet = new HashSet<>();

        if (partials.containsKey(partialsToGet[0])) {
            wordsToGet.addAll(partials.get(partialsToGet[0]));
        } else {
            return new HashSet<>();
        }

        for (int i = 1; i < partialsToGet.length; ++i)
            if (partials.containsKey(partialsToGet[i])) {
                wordsToGet.retainAll(partials.get(partialsToGet[i]));
            } else {
                return new HashSet<>();
            }

        return wordsToGet;
    }

    public HashSet<String> removeWord(String word) {
        HashSet<String> definitionsToRemove = words.remove(word);
        for (String definition : definitionsToRemove)
            for (String partial : definition.toLowerCase().split(" "))
                partials.get(partial).remove(word);

        isSaved = false;
        return definitionsToRemove;
    }

    public HashSet<String> removeDefinition(String definition) {
        String[] partialsToRemove = definition.toLowerCase().split(" ");
        HashSet<String> wordsToRemove = new HashSet<>();

        if (partials.containsKey(partialsToRemove[0])) {
            wordsToRemove.addAll(partials.get(partialsToRemove[0]));
        } else {
            return new HashSet<>();
        }

        for (int i = 1; i < partialsToRemove.length; ++i)
            if (partials.containsKey(partialsToRemove[i])) {
                wordsToRemove.retainAll(partials.get(partialsToRemove[i]));
            } else {
                return new HashSet<>();
            }

        for (String word : wordsToRemove)
            words.get(word).remove(definition);

        isSaved = false;
        return wordsToRemove;
    }

    public void overwrite(String word, String newDefinition) {
        removeWord(word);
        put(word, newDefinition);
    }

    public void overwrite(String word, String[] newDefinitions) {
        removeWord(word);
        put(word, newDefinitions);
    }

    public String randomWord() {
        int index = (new Random()).nextInt(words.size());
        Iterator<String> it = words.keySet().iterator();

        for (int i = 0; i < index - 1; i++)
            it.next();

        return it.next();
    }

    public String randomDefinition() {
        HashSet<String> word = words.get(randomWord());
        int index = (new Random()).nextInt(word.size());
        Iterator<String> it = word.iterator();

        for (int i = 0; i < index - 1; i++)
            it.next();

        return it.next();
    }

    public String reset(String filepath) {
        try {
            BufferedReader br;
            br = new BufferedReader(new FileReader(filepath));

            words.clear();
            partials.clear();

            String[] data;

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                data = line.split("`");
                if (data.length <= 1) {
                    continue;
                }
                put(data[0], data[1].split("\\s*\\|\\s*"));
            }

            br.close();

        } catch (IOException e) {
            return e.getMessage();
        }

        isSaved = true;
        return "";
    }

    public String save(String filepath) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filepath)));
            oos.writeObject(words);
            oos.writeObject(partials);

            oos.close();

        } catch (IOException e) {
            return e.getMessage();
        }

        isSaved = true;
        return "";
    }

    @SuppressWarnings("unchecked")
    public String load(String filepath) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filepath)));

            words = (HashMap<String, HashSet<String>>) ois.readObject();
            partials = (HashMap<String, HashSet<String>>) ois.readObject();

            ois.close();

        } catch (IOException | ClassNotFoundException e) {
            return e.getMessage();
        }

        isSaved = true;
        return "";
    }

//    public void printWords() {
//        for (String next : words.keySet()) {
//            System.out.println(next);
//            for (String val : words.get(next))
//                System.out.println("    " + val);
//        }
//    }
//
//    public void printPartials() {
//        for (String next : partials.keySet()) {
//            System.out.println(next);
//            for (String val : partials.get(next))
//                System.out.println("    " + val);
//        }
//    }
    public String[][] to2DArray() {
        String[][] a = new String[words.values().stream().mapToInt(HashSet::size).sum()][2];
        int i = 0;
        for (String word : words.keySet())
            for (String definition : words.get(word)) {
                a[i][0] = word;
                a[i++][1] = definition;
            }
        return a;
    }
}
