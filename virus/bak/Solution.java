package org.swc.virus;


import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * void (init)
 * int add(int id, int pid, int fileSize)
 * int move(int id, int pid)
 * int infect(int id)
 * int recover(int id)
 * int remove(int id)
 *
 * 1. add:
 * 1) create node
 * 2) get parent from hash
 * 3) link node to parent
 * 4) add node to parent's child
 * 5) if file, increase fs, bk, fc to all parents
 * 6) add node to hash
 *
 * 2. move:
 * 1) get child from hash
 * 2) get old parent from hash
 * 3) remove child from old parent
 * 4) if fs > 0 decrease fs, bk, fc from all old parents
 * 5) get new parent from hash
 * 6) add child to new parent
 * 7) if fs > 0, increase fs, bk, fc from all new parents
 *
 * 3. infect:
 * 1) get node from hash
 * 2) get infect size avg = fs/fc (if fc > 0)
 * 3) increase avg to all children as avg
 * 4) increase fs - bk to all parents
 *
 * 4. recover:
 * 1) get node from hash
 * 2) decrease fs - bk from all parents
 * 3) set fs to bk for all children including node
 *
 *
 * 5. remove
 * 1) get node from hash
 * 2) get parent from hash
 * 3) decrease fs,bk,fc from all parents
 * 4) remove node from parent's child
 * 5) remove node from hash
 */


public class Solution {


    private static Scanner sc;
    private static File file;
    private static FileWriter fw;


    static void init (){

    }
    static int add(int id, int pid, int fileSize){
        System.out.println("add: "+ id + " " +pid + " " + fileSize);

        return 0;
    }

    static int move(int id, int pid){
        System.out.println("move: " + id + " " + pid);


        return 0;
    }

    static int infect(int id){
        System.out.println("infect: " + id);

        return 0;
    }


    static int recover(int id){
        System.out.println("recover: " + id);

        return 0;
    }

    static int remove(int id){
        System.out.println("remove: " + id);

        return 0;
    }

    static void do_test(int tc) throws Exception{
        int n = sc.nextInt();
        int cmd;
        fw.write("\t n:" +n +"\n");

        init();
        for(int i=0; i < n; i++){
            cmd = sc.nextInt();
            if(cmd == 1){
                add(sc.nextInt(), sc.nextInt(), sc.nextInt());
            }else if(cmd == 2){
                move(sc.nextInt(), sc.nextInt());
            }else if(cmd == 3){
                infect(sc.nextInt());
            }else if(cmd == 4){
                recover(sc.nextInt());
            }else if(cmd == 5){
                remove(sc.nextInt());
            }
        }

    }

    public static void main(String[] args) throws Exception {

        System.out.println("Test for java..!");

        System.setIn(new java.io.FileInputStream("virus/input.txt"));
        file = new File("virus/output.txt");
        fw = new FileWriter(file);


        sc = new Scanner(System.in);

        int tc = sc.nextInt();

        for(int i=0; i < tc; i++){
            fw.write("tc: " + tc + " ");
            do_test(i);
        }

        fw.close();
    }


}
