package com.example.wordladder.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@RestController
public class WordLadderController {

    @RequestMapping(value = "/login", method= RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/auth")
    public String Auth(String username,String password){


        if(username.equals("")){
            return "Please input your username";
        }

        if(password.equals("")){
            return "Password can't be null";
        }

        initUser();
        if(!info.containsKey(username)){
            return "Invalid username or password";
        }

        if(info.get(username).equals(password)){
            // Once a new user login, other users token should be clear
            tokenTable.clear();
            return "<p>Login successfully!</p>" +
                    "<p>Your token is " +
                    createToken(username) + "</p>" +
                    "<p>Now you can visit /wl with tokenId</p>";
        }
        else
            return "Invalid username or password";

    }



    @RequestMapping(value = "/wl")
    public String init(String source,String target,String tokenId) throws IOException {

        if(!tokenTable.containsKey(tokenId)){
            return "<p>Invalid tokenId</P>" +
                    "<p>Please visit /Auth to login</p>";
        }

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

    private static Stack<String> wordladder(String wordTwo, Queue<Stack<String>> ladders, Set<String> words, String alphabet) {
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

    private static Map<String,String> info = new HashMap<>();

    private static void initUser(){

        info.put("user01","100001");
        info.put("user02","200002");
        info.put("user03","300003");
        info.put("user04","400004");
        info.put("user05","500005");
        info.put("user06","600006");
        info.put("user07","700007");
        info.put("user08","800008");
        info.put("user09","900009");

    }

    private static Map<String,String> tokenTable = new HashMap<>();

    /*
     * create a random string as token
     */
    private static String createToken(String username){
        String str="zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        Random random=new Random();
        StringBuffer temp = new StringBuffer();
        for(int i=0; i < 4; ++i){
            int number=random.nextInt(62);
            temp.append(str.charAt(number));
        }
        tokenTable.put(temp.toString(),username);
        return temp.toString();
    }
}