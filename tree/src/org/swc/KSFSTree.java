package org.swc;

import java.util.Scanner;

public class KSFSTree {

    private static int count = 0;
//    public ListNode[];
    public static Tree tree;

    public static int pos = 0;
    public static int current = 0;
    private static void cmd_init(){
        tree = new Tree(2000);

    }

    private static int cmd_mkdir(String name){
        System.out.println(count++ + " mkdir " + name);
        int code = tree.addChild(current, pos++, name);
        if(code == -2)
            System.out.println("duplicated.!");

        return current;
    }

    private static int cmd_cd(String path){
        System.out.println(count++ + " cd " + path);
        ListNode temp = tree.treenode[current].head;
        if(path.equals("0")){
            while(temp != null){
                System.out.print(temp.childValue + " ");
                temp = temp.next;
            }
        }


        return -1;
    }

    private static int cmd_rm(String path){
        System.out.println(count++ + " rm " + path);

        return -1;
    }

    private static int cmd_ls(String path){
        System.out.println(count++ + " ls " + path);

        return -1;
    }

    public static void main(String arg[]) throws Exception {
        System.setIn(new java.io.FileInputStream("tree/sample_input.txt"));
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        int seed = sc.nextInt();
        int trials = sc.nextInt();
        cmd_init();

        for (int test_case = 1; test_case <= T; ++test_case)
        {
            count = 1;
            for(int i = 0; i < trials; i++){
                int cmd = sc.nextInt();
                String value = sc.next();

                switch (cmd) {
                    case 1:
                        cmd_mkdir(value);
                        break;
                    case 2:
                        cmd_cd(value);
                        break;
                    case 3:
                        cmd_rm(value);
                        break;
                    case 4:
                        cmd_ls(value);
                        break;
                    default:
                        break;
                }
            }

        }

        sc.close();
    }
}
