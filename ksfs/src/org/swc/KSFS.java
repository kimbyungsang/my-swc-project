package org.swc;

import java.util.Scanner;


class Tree{
    String name;

    Tree parent = null;
    Tree start = null;
    Tree end = null;
    Tree next = null;
    Tree prev = null;

    Tree(String name){
        this.name = name;
    }
}


public class KSFS {

    static int count = 0;
    static Tree root;

    static boolean strcmp(String first, String second){
        if(first.length() != second.length())
            return false;

        for(int i=0;i < first.length(); i++){
            if(first.charAt(i) != second.charAt(i))
                return false;
        }
        return true;
    }

    static boolean isSmall(char[] first, char[] second){
        if(first.length <= second.length){
            for(int i =0; i < first.length; i++){
                if(first[i] > second[i])
                    return false;
                else if (first[i] < second[i])
                    return true;
            }
            return true;
        }if(first.length > second.length){
            for(int i = 0 ; i < second.length; i++){
                if(first[i] > second[i])
                    return false;
                else if (first[i] < second[i])
                    return true;
            }
            return false;
        }

        return true;

    }

    static void printTree(Tree node, int depth){
        for(Tree iter = node.start.next; iter != node.end; iter = iter.next){
            printTree(iter, depth++);
        }
        System.out.println(node.parent.name +"\t" + node.name);
    }

    static void printDir(Tree node){
        System.out.print(node.name +":\t" );
        for(Tree iter = node.start.next; iter != node.end; iter = iter.next){
            System.out.print(iter.name + " ");
        }
        System.out.println();
    }




    static void cmd_init() {
        root = new Tree("/");
        root.parent = new Tree(".");
        root.start = new Tree(".");
        root.end = new Tree(".");
        root.start.next = root.end;
        root.end.prev = root.start;
    }

    static Tree createNode(String name){
        Tree node = new Tree(name);
        node.start = new Tree(".");
        node.end = new Tree(".");
        node.start.next = node.end;
        node.end.prev = node.start;

        return node;

    }



    static Tree cmd_mkdir(Tree current, String name){
        System.out.println("\n"+ count++ + " mkdir " + name);

        Tree node = createNode(name);

        node.parent = current;

        for(Tree iter = node.parent.start.next; iter != node.parent.end; iter = iter.next){
            if(isSmall(iter.name.toCharArray(), name.toCharArray())){
                node.next = iter;
                node.prev = iter.prev;
                node.prev.next = node;
                node.next.prev = node;
                return current;

            }
        }

        node.next = node.parent.end;
        node.prev = node.parent.end.prev;
        node.next.prev = node;
        node.prev.next = node;


        return current;
    }

    static Tree cmd_cd(Tree current, String path) {
        System.out.println(count++ + " cd " + path);

        if(path.equals(".."))
            return current.parent;

        for(Tree iter = current.start.next; iter != current.end; iter = iter.next){
            if(strcmp(iter.name, path)){
                return iter;
            }
        }

/*        System.out.println(count++ + " cd " + path);
        if(strcmp(path, "..")){
            if(current.parent != null)
                return current.parent;
            else return null;
        }
        Node cdNode = current.child;
        while(cdNode != null){
            if(strcmp(cdNode.data, path))
                return cdNode;
            cdNode = cdNode.rs;
        }*/



        return null;

    }

    static Tree cmd_ls(Tree current, String path) {
       System.out.println("\n"+ count++ + " ls " + path);

        if(path.equals("0")){
            for(Tree iter = current.start.next; iter != current.end; iter = iter.next){
                System.out.print(iter.name + " ");
            }
            System.out.println();
        }else{
            for(Tree iter = current.start.next; iter != current.end; iter = iter.next){
                boolean isSub = true;
                for(int i = 0; i < path.length(); i++){
                    if(iter.name.charAt(i) != path.charAt(i)) {
                        isSub = false;
                        break;
                    }
                }

                if(isSub)
                    System.out.print(iter.name + " ");
            }
            System.out.println();
        }


        return current;

    }

    static Tree cmd_rm(Tree current, String path) {
        System.out.println("\n"+ count++ + " rm " + path);

        for(Tree iter = current.start.next; iter != current.end; iter = iter.next){
            if(strcmp(iter.name, path)){
                iter.next.prev = iter.prev;
                iter.prev.next = iter.next;

            }
        }
/*
        if(current.child == null)
            return null;

        if(strcmp(current.child.data, path)){
            current.child = current.child.rs;
            current.child.ls = null;
            return current;
        }else{
            Node temp = current.child.rs;

            while(temp != null){
                if(strcmp(temp.data, path)){
                    if(temp.rs != null) {
                        temp.ls.rs = temp.rs;
                        temp.rs.ls = temp.ls;
                        return current;
                    }else{
                        temp.ls = null;
                    }

                }
                temp = temp.rs;

            }

        }*/
        return current;

    }



    public static void main(String[] args) throws Exception {


        System.setIn(new java.io.FileInputStream("tree/sample_input.txt"));
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        int seed = sc.nextInt();
        int trials = sc.nextInt();
        Tree current;
        cmd_init();
        for (int test_case = 1; test_case <= T; ++test_case)
        {
            count = 1;
            current = root;
            for(int i = 0; i < trials; i++){
                int cmd = sc.nextInt();
                String value = sc.next();

                switch (cmd) {
                    case 1:
                        current = cmd_mkdir(current, value);
                        break;
                    case 2:
                        Tree cdNode = cmd_cd(current, value);
                        if(cdNode == null){
                            System.out.println("This is root.");
                            break;
                        }
                        current = cdNode;
                        break;
                    case 3:
                        current = cmd_rm(current, value);
                        break;
                    case 4:
                        current = cmd_ls(current, value);
                        break;
                    default:
                        break;
                }
                printDir(current);

            }
            //printLevel(root, 1);

        }

        sc.close();
    }


}

