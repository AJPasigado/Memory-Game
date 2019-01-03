package com.example.ajbpasigado.pasigado_activity4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameManager {
    public int correct = 0;
    public int incorrect = 0;
    public int board_size = 0;

    String[][] board;

    public int getCorrect(){
        return correct;
    }

    public int getIncorrect(){
        return incorrect;
    }

    public String[][] initalizeBoard(int mode, int board_size){
        this.board_size = board_size;

        String[][] multi = new String[board_size][board_size];
        for (int i = 0; i<board_size; i++){
            String[] def = new String[board_size];
            Arrays.fill(def, "-");
            multi[i] = def;
        }

        board = multi;

        int numberOfPairs = (board_size * board_size) / 2;

        String[] choices_alpha = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R"};
        String[] choices_num = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18"};
        String[] choices_symbol = {"!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "+", "[", "]", ">", "<", "~", "|", "="};
        String[] pre_choices = mode == 0 ? Arrays.copyOfRange(choices_alpha, 0, numberOfPairs) :
                (mode == 1 ? Arrays.copyOfRange(choices_num, 0, numberOfPairs) : Arrays.copyOfRange(choices_symbol, 0, numberOfPairs));

        List<String> choices = new ArrayList<String>(Arrays.asList(pre_choices));
        choices.addAll(Arrays.asList(pre_choices));

        int[] used = new int[numberOfPairs];
        Arrays.fill(used, 2);


        Random rand = new Random();

        for (int i = 0; i< board_size; i++){
            for (int j = 0; j< board_size; j++){
                if (!(board_size % 2 != 0 && (i == (board_size/2) && j == (board_size/2)))) {
                    int r = rand.nextInt(choices.size());
                    board[i][j] = choices.get(r);
                    choices.remove(r);
                }
            }
        }

        return board;
    }

    public String[][] defaultBoard() {
        String[] def = new String[board_size];
        Arrays.fill(def, "???");

        String[][] multi = new String[board_size][board_size];
        Arrays.fill(multi, def);

        return multi;
    }

    public boolean checker(int[] first, int[] second){
        return board[first[0]][first[1]] == board[second[0]][second[1]];
    }
}
