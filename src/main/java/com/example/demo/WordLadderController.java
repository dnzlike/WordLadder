package com.example.demo;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@RestController
public class WordLadderController {

    String sourceWord = "hello";
    String targetWord;

    public String test(String source){
        return "hello";
    }

    @RequestMapping(value = "/wordladder")
    public String say() {

        return "<input placeholder=\"source\" id=\"source\"></input>"+
                "<input placeholder=\"target\" id=\"target\"></input>"+
                "<button onClick={javascript:alert(getElementById(\"source\").value)}>lookup</button>";
    }


    @RequestMapping(value = "/w")
    public static void init(String source,String target) throws IOException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Dictionary file name?");
        String fileName = sc.nextLine();

        while(true) {
            while(true) {
                String alphabet = "abcdefghijklmnopqrstuvwxyz";
                System.out.println("Word #1 (or Enter to quit): ");
                String wordOne = sc.nextLine();
                if (wordOne.equals("")) {
                    System.out.println("Have a nice day.");
                    return;
                }

                System.out.println("Word #2 (or Enter to quit): ");
                String wordTwo = sc.nextLine();
                if (wordTwo.equals("")) {
                    System.out.println("Have a nice day.");
                    return;
                }

                int lenOne = wordOne.length();
                int lenTwo = wordTwo.length();
                if (wordOne.equals(wordTwo)) {
                    System.out.println("The two words must be different.");
                    return;
                }

                if (lenOne != lenTwo) {
                    System.out.println("The two words must be the same length.");
                    return;
                }

                BufferedReader br = new BufferedReader(new FileReader(fileName));
                Set<String> words = new HashSet();
                String str = null;

                while((str = br.readLine()) != null) {
                    if (str.length() == lenOne) {
                        words.add(str);
                    }
                }

                Queue<Stack<String>> ladders = new LinkedList();
                Stack<String> first = new Stack();
                first.push(wordOne);
                ladders.offer(first);
                Stack<String> result = wordladder(wordTwo, ladders, words, alphabet);
                if (((String)result.peek()).equals("no ladder")) {
                    System.out.println("No word ladder found from " + wordTwo + " back to " + wordOne);
                } else {
                    System.out.println("A ladder from " + wordTwo + " back to " + wordOne + " : ");

                    while(!result.empty()) {
                        System.out.print((String)result.peek() + " ");
                        result.pop();
                    }

                    System.out.println("");
                }
            }
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