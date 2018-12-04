package org.swc.painter;

import java.util.Scanner;

public class Solution {

    static void init(){

        image = new char[4096][4096];
        for(int i = 0; i < 4096; i++){
            for(int j = 0; j < 4096; j++){
                image[i][j] = 0;
            }
        }


        int x = sc.nextInt();  //10
        int y = sc.nextInt();  //5

        for(int i = 0; i < y; i++){
            String tmp = sc.next();
            for(int j =0; j < x; j++){
                image[i][j] = tmp.charAt(j);
            }
        }

        System.out.println("init: ");
        printImage();


    }

    static void printImage(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 10; j++){
                if(image[i][j] != 0)
                    System.out.print(image[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void resize(int scale){
        System.out.println("resize: " + scale);
    }

    static void rotate(int angle){
        System.out.println("rotate: " + angle);

    }

    static void flip(int direction){
        System.out.println("flip: " + direction);


    }

    static void fill(int x, int y, int color){
        System.out.println("flip: " + x + " "  + y +" " + color);

    }

    static void undo(){
        System.out.println("undo: ");


    }

    static char[][] image;
    static Scanner sc;

    public static void main(String args[]) throws Exception{
        System.setIn(new java.io.FileInputStream("painter/input.txt"));
        sc = new Scanner(System.in);



        init();
        int tc = sc.nextInt();

        for(int i = 0; i < tc; i++){
            int cmd = sc.nextInt();

            switch(cmd){
                case 1:
                    resize(sc.nextInt());
                    break;
                case 2:
                    rotate(sc.nextInt());
                    break;
                case 3:
                    flip(sc.nextInt());
                    break;
                case 4:
                    fill(sc.nextInt(), sc.nextInt(), sc.next().charAt(0));
                    break;
                case 5:
                    undo();
                    break;

            }

        }

    }
}
