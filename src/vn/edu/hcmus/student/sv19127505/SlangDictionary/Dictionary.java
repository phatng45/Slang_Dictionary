package vn.edu.hcmus.student.sv19127505.SlangDictionary;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class Dictionary {
    HashMap<String, HashSet<String>> words    = new HashMap<>();
    HashMap<String, HashSet<String>> partials = new HashMap<>();
    boolean                          isSaved  = true;

    public void put(String newWord, String newDefinition) {
        if (!words.containsKey(newWord))
            words.put(newWord, new HashSet<>());
        words.get(newWord).add(newDefinition);

        for (String newPartial : newDefinition.split(" ")) {
            if (!partials.containsKey(newPartial))
                partials.put(newPartial, new HashSet<>());
            partials.get(newPartial).add(newWord);
        }

        isSaved = false;
    }

    public void put(String newWord, String[] newDefinitions) {
        for (String d : newDefinitions)
            put(newWord, d);
    }

    public HashSet<String> getDefinition(String word) {
        return words.get(word);
    }

    public HashSet<String> getWord(String definition) {
        String[]        partialsToGet = definition.split(" ");
        HashSet<String> wordsToGet    = new HashSet<>(partials.get(partialsToGet[0]));

        for (int i = 1; i < partialsToGet.length && wordsToGet.size() > 0; ++i)
            wordsToGet.retainAll(partials.get(partialsToGet[i]));

        return wordsToGet;
    }

    public HashSet<String> removeWord(String word) {
        HashSet<String> definitionsToRemove = words.remove(word);
        for (String definition : definitionsToRemove)
            for (String partial : definition.split(" "))
                partials.get(partial).remove(word);

        isSaved = false;
        return definitionsToRemove;
    }

    public HashSet<String> removeDefinition(String definition) {
        String[]        partialsToRemove = definition.split(" ");
        HashSet<String> wordsToRemove    = new HashSet<>(partials.get(partialsToRemove[0]));

        for (int i = 1; i < partialsToRemove.length; ++i) {
            wordsToRemove.addAll(partials.get(partialsToRemove[i]));
        }

        for (String word : wordsToRemove)
            words.get(definition).remove(word);

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
        int              index = (new Random()).nextInt(words.size());
        Iterator<String> it    = words.keySet().iterator();

        for (int i = 0; i < index - 1; i++)
            it.next();

        return it.next();
    }

    public String randomDefinition() {
        HashSet<String>  word  = words.get(randomWord());
        int              index = (new Random()).nextInt(word.size());
        Iterator<String> it    = word.iterator();

        for (int i = 0; i < index - 1; i++)
            it.next();

        return it.next();
    }

    public String reset(String filepath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));

            words.clear();
            partials.clear();

            String[] data;

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                data = line.split("`");
                if (data.length <= 1) continue;
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
            FileOutputStream     fos = new FileOutputStream(filepath);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream   oos = new ObjectOutputStream(bos);
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
            FileInputStream     fis = new FileInputStream(filepath);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream   ois = new ObjectInputStream(bis);

            words = (HashMap<String, HashSet<String>>) ois.readObject();
            partials = (HashMap<String, HashSet<String>>) ois.readObject();

            ois.close();

        } catch (IOException | ClassNotFoundException e) {
            return e.getMessage();
        }

        isSaved = true;
        return "";
    }

    public void printWords() {
        for (String next : words.keySet()) {
            System.out.println(next);
            for (String val : words.get(next)) {
                System.out.println("    " + val);
            }
        }
    }

    public void printPartials() {
        for (String next : partials.keySet()) {
            System.out.println(next);
            for (String val : partials.get(next)) {
                System.out.println("    " + val);
            }
        }
    }
}