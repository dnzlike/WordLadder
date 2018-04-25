package com.example.demo;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@RestController
public class WordLadderController {

    @RequestMapping(value = "/Auth")
    public String Auth(String username,String password){
        if(username.equals("")){
            return "Please input your username";
        }
        if(password.equals("")){
            return "Please input your password";
        }

        return "welcome " + username;
    }



    @RequestMapping(value = "/wl")
    public String init(String source,String target) throws IOException {


        String fileName = "dictionary.txt";
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String wordOne = source;
        if (wordOne.equals("")) {
            return "Have a nice day.";
        }
        String wordTwo = target;
        if (wordTwo.equals("")) {
            return "Have a nice day.";
        }

        int lenOne = wordOne.length();
        int lenTwo = wordTwo.length();
        if (wordOne.equals(wordTwo)) {
            return "The two words must be different.";
        }

        if (lenOne != lenTwo) {
            return "The two words must be the same length.";
        }

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        Set<String> words = new HashSet();
        String str = null;

        while ((str = br.readLine()) != null) {
            if (str.length() == lenOne) {
                words.add(str);
            }
        }

        Queue<Stack<String>> ladders = new LinkedList();
        Stack<String> first = new Stack();
        first.push(wordOne);
        ladders.offer(first);
        Stack<String> result = wordladder(wordTwo, ladders, words, alphabet);
        if (((String) result.peek()).equals("no ladder")) {
            return "No word ladder found from " + wordTwo + " back to " + wordOne;
        } else {
            String output = "A ladder from " + wordTwo + " back to " + wordOne + " : ";

            while (!result.empty()) {
                output = output + (String)result.peek() + " ";
                result.pop();
            }
            return output;
        }


    }

    public static Stack<String> wordladder(String wordTwo, Queue<Stack<String>> ladders, Set<String> words, String alphabet) {
        int size = ladders.size();
        Stack test;
        if (size == 0) {
            test = new Stack();
            test.push("no ladder");
            return test;
        } else {
            test = (Stack)ladders.peek();

            for(int i = 0; i < size; ++i) {
                Stack<String> tmp = (Stack)ladders.peek();
                String top = (String)tmp.peek();

                for(int j = 0; j < top.length(); ++j) {
                    for(int k = 0; k < 26; ++k) {
                        StringBuilder alpha = new StringBuilder(alphabet);
                        String letter = alpha.substring(k, k + 1);
                        StringBuilder newWord = new StringBuilder(top);
                        newWord = newWord.replace(j, j + 1, letter);
                        top = newWord.toString();
                        if (!top.equals(tmp.peek())) {
                            if (top.equals(wordTwo)) {
                                tmp.push(top);
                                return tmp;
                            }

                            if (words.contains(top)) {
                                Stack<String> newStack = (Stack)tmp.clone();
                                newStack.push(top);
                                words.remove(top);
                                ladders.offer(newStack);
                            }

                            tmp = (Stack)ladders.peek();
                            top = (String)tmp.peek();
                        }
                    }
                }

                ladders.poll();
            }

            return wordladder(wordTwo, ladders, words, alphabet);
        }
    }
}