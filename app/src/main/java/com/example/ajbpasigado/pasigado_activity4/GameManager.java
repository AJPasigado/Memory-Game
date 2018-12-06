package com.example.ajbpasigado.pasigado_activity4;

import java.util.Random;

public class GameManager {
    public int correct = 0;
    public int incorrect = 0;
    String[][] board = new String[][]{
            { "-", "-", "-", "-"},
            { "-", "-", "-", "-"},
            { "-", "-", "-", "-"},
            { "-", "-", "-", "-"},
    };

    public int getCorrect(){
        return correct;
    }

    public int getIncorrect(){
        return incorrect;
    }

    public String[][] initalizeBoard(){
        String[] choices = {"A", "B", "C", "D", "E", "F", "G", "H"};
        int[] used = {2,2,2,2,2,2,2,2};
        Random rand = new Random();

        for (int i = 0; i< 4; i++){
            for (int j = 0; j< 4; j++){
                int r = 0;
                int c = 0;
                int choice = 0;

                while(true){
                    r = rand.nextInt(4);
                    c = rand.nextInt(4);
                    if (board[r][c] == "-"){
                        break;
                    }
                }
                while(true){
                    choice = rand.nextInt(8);
                    if (used[choice] != 0){
                        board[r][c] = choices[choice];
                        used[choice] = used[choice] - 1;
                        break;
                    }
                }
            }
        }

        return board;
    }

    public String[][] defaultBoard() {
        String[][] multi = new String[][] {
                { "???", "???", "???", "???"},
                { "???", "???", "???", "???"},
                { "???", "???", "???", "???"},
                { "???", "???", "???", "???"},
        };
        return multi;
    }

    public boolean checker(int[] first, int[] second){
        return board[first[0]][first[1]] == board[second[0]][second[1]];
    }



}
