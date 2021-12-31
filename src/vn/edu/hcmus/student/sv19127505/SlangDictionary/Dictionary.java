package vn.edu.hcmus.student.sv19127505.SlangDictionary;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
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

    private HashSet<String> removeWord(String word) {
        HashSet<String> definitionsToRemove = words.remove(word);
        for (String definition : definitionsToRemove)
            for (String partial : definition.toLowerCase().split(" "))
                partials.get(partial).remove(word);

        isSaved = false;
        return definitionsToRemove;
    }

    public void remove(String word, String definition) {
        String[] partialsToRemove = definition.toLowerCase().split(" ");

        for (String partial : partialsToRemove) {
            partials.get(partial).remove(word);
        }

        this.getDefinitions(word).remove(definition);
        if (this.getDefinitions(word).isEmpty()) {
            words.remove(word);
        }

        isSaved = false;
    }

    public void overwrite(String word, String newDefinition) {
        removeWord(word);
        put(word, newDefinition);
    }

    public void replace(String word, String oldDef, String newDef) {
        words.get(word).add(newDef);
        words.remove(word, oldDef);

        isSaved = false;
    }

//    public void overwrite(String word, String[] newDefinitions) {
//        removeWord(word);
//        put(word, newDefinitions);
//    
    /**
     *
     * @return an array, in which: 1st ~ 4th: 4 definitions (randomized) 5th:
     * the word 6th: the index of the correct definition to the word
     */
    public ArrayList<Object> randomWord() {
        // init Random
        Random r = new Random();
        // REWORKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK
        // REWORKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK
        // REWORKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK
        // REWORKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK
        // REWORKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK
        // REWORKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK
        // REWORKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK
        // REWORKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK
        // REWORKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK
        
        // get the center index
        int center = r.nextInt(partials.size());

        HashSet<Integer> indexes = new HashSet<>();

        // get a set of indexes with +- 15 around the center
        while (indexes.size() < 5) {
            indexes.add(center + (r.nextBoolean() ? 1 : -1) * r.nextInt(5));
        }

        // get the iterator
        Iterator<String> it = partials.keySet().iterator();
        ArrayList<Object> res = new ArrayList<>();

        // loop through the partials keySet
        for (int i = 0; i < Collections.max(indexes); i++)
            // if the current i matches one of the indexes
            if (indexes.contains(i)) {
                // add a random word gotten from the partials
                Object[] ws = partials.get(it.next()).toArray();
                res.add(ws[r.nextInt(ws.length)]);
            } else
                it.next();

        // get a random index as the answer
        int answerIndex = r.nextInt(res.size());
        
        // add a copy of answer and the index
        res.add(res.get(answerIndex));
        res.add(answerIndex);

        // loop through the first 4 results
        for (int i = 0; i < 4; ++i) {
            // transform each word to one of its definitions
            Object[] defs = getDefinitions((String) res.get(i)).toArray();
            res.set(i, defs[r.nextInt(defs.length)]);
        }
        
        return res;
    }

    public String randomDefinitions() {
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
            for (String definition : this.getDefinitions(word)) {
                a[i][0] = word;
                a[i++][1] = definition;
            }
        return a;
    }
}
