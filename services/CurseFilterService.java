/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.services;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ASUS
 */
public class CurseFilterService {

    static Map<String, String[]> words = new HashMap<>();

    static int largestWordLength = 0;
    public static String txt;

    public static void loadConfigs() {
        try {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(
                            new URL("https://docs.google.com/spreadsheets/d/1N71Pd5DHqTNvT-EWzrIkudrg5ejKkdNhYLeEKzZPDD4/edit#gid=0").openConnection().getInputStream()));

            String line = "";
            int counter = 0;
            while((line = reader.readLine()) != null) {
                counter++;
                String[] content = null;
                try {
                    content = line.split(",");
                    if(content.length == 0) {
                        continue;
                    }
                    String word = content[0];
                    String[] ignore_in_combination_with_words = new String[]{};
                    if(content.length > 1) {
                        ignore_in_combination_with_words = content[1].split("_");
                    }

                    if(word.length() > largestWordLength) {
                        largestWordLength = word.length();
                    }
                    words.put(word.replaceAll(" ", ""), ignore_in_combination_with_words);

                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
            System.out.println("Loaded " + counter + " words to filter out");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    public static ArrayList<String> badWordsFound(String input) {
        if(input == null) {
            return new ArrayList<>();
        }


        input = reformat(input);

        System.out.println(input);
        ArrayList<String> badWords = new ArrayList<>();
        input = input.toLowerCase().replaceAll("[^a-zA-Z]", "");

        // iterate over each letter in the word
        for(int start = 0; start < input.length(); start++) {
            // from each letter, keep going to find bad words until either the end of the sentence is reached, or the max word length is reached.
            for(int offset = 1; offset < (input.length()+1 - start) && offset < largestWordLength; offset++)  {
                String wordToCheck = input.substring(start, start + offset);
                if(words.containsKey(wordToCheck)) {
                    // for example, if you want to say the word bass, that should be possible.
                    String[] ignoreCheck = words.get(wordToCheck);
                    boolean ignore = false;
                    for(int s = 0; s < ignoreCheck.length; s++ ) {
                        if(input.contains(ignoreCheck[s])) {
                            ignore = true;
                            break;
                        }
                    }
                    if(!ignore) {
                        badWords.add(wordToCheck);
                    }
                }
            }
        }


        for(String s: badWords) {
            System.out.println(s + " qualified as a bad word in a username");
        }
        return badWords;

    }

    private static String reformat(String input) {
        input = input.replaceAll("1","i");
        input = input.replaceAll("!","i");
        input = input.replaceAll("3","e");
        input = input.replaceAll("4","a");
        input = input.replaceAll("@","a");
        input = input.replaceAll("5","s");
        input = input.replaceAll("7","t");
        input = input.replaceAll("0","o");
        input = input.replaceAll("9","g");
        input = input.replaceAll("\\$","s");
        return input;
    }

    public static String filterText(String input, String username) {
        ArrayList<String> badWords = badWordsFound(input);
        if(badWords.size() > 0) {
            return "This message was blocked because a bad word was found. If you believe this word should not be blocked, please message support.";
        }
        return input;
    }

    public static String cleanText(String text){
        loadConfigs();
        ArrayList<String > badwords =  badWordsFound(text);

        text = reformat(text);
        setTxt(text);
        badwords.forEach(bw -> {
            StringBuilder sb = new StringBuilder();
            for(int c = 0; c<bw.length(); c++){
                sb.append("*");
            }

            setTxt(getTxt().replace(bw, sb.toString()));
        });
        return getTxt();
    }

    public static String getTxt() {
        return txt;
    }

    public static void setTxt(String txt) {
        CurseFilterService.txt = txt;
    }
}

