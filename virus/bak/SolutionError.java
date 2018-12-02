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
 * 0. init
 * 1) create hash size of 10007
 * 2) create dummy hash
 * 3) create root
 * 4) create dummy child
 * 5) parent null
 * 6) add root to hash
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

class Tree{
    int id = -1;
    int type = 0;
    int fs = 0;
    int bk = 0;
    int fc = 0;

    Tree parent = null;
    Tree child = null;
    Tree next = null;
    Tree prev = null;
    Tree hash_next = null;
    Tree hash_prev = null;

    Tree(int id){
        this.id = id;
    }
}

public class Solution {

    private static final int MAX_HASHSIZE = 10007;
    private static Scanner sc;
    private static File file;
    private static FileWriter fw;


    private static Tree[] hash;
    private static Tree root;

    static int hashing(int id){
        return id % MAX_HASHSIZE;
    }

    static void printTree(Tree root, int depth){
        for(Tree iter = root.child.next; iter != null; iter = iter.next){
            printTree(iter, depth++);
        }
        System.out.println("depth: " + root.parent.id + "\t" + root.id + "\t" + root.type + "\t" + root.fs + "\t" + root.bk + "\t" + root.fc);
    }


    static void init (){

        // 0. init
        // 1) create hash size of 10007
        // 2) create dummy hash
        hash = new Tree[MAX_HASHSIZE];
        for(int i = 0; i < MAX_HASHSIZE; i++){
            hash[i] = new Tree(-1);
        }

        // 3) create root
        // 4) create dummy child
        root = new Tree(1000);
        root.child = new Tree(-1);

        // 5) parent dummy
        root.parent = new Tree(-1);

        // 6) add root to hash
        hash[hashing(1000)].hash_next = root;
        root.prev = hash[hashing(1000)].hash_next;

        printTree(root, 0);

    }

    static Tree getNode(int pid){
        Tree node = hash[hashing(pid)];
        for(Tree iter = node.hash_next; iter != null; iter = iter.hash_next){
            if(iter.id == pid){
                return iter;
            }
        }

        return node;
    }
    static int add(int id, int pid, int fileSize) throws Exception{
        System.out.println("\nadd: "+ id + " " +pid + " " + fileSize);
        /*
         * 1. add:
         * 1) create node
         * 2) get parent from hash
         * 3) link node to parent
         * 4) add node to parent's child
         * 5) if file, increase fs, bk, fc to all parents
         * 6) add node to hash
         *
         */

        // create node and set fs, bk, fc
        Tree node = new Tree(id);
        node.child = new Tree(-1);

        if(fileSize == 0){
            node.type = 0;
        }else{
            node.type = 1;
            node.fs = fileSize;
            node.bk = fileSize;
            node.fc = 1;
        }

        // get parent from hash
        Tree parent = getNode(pid);

        // link node to parent
        node.parent = parent;

        // add node to parent's child
        node.prev = parent.child;
        node.next = parent.child.next;
        node.prev.next = node;
        if(node.next != null)
            node.next.prev = node;


        // if node is file, increase fs, bk, fc to all parents;
        if(node.type == 1)
            for(Tree iter = node.parent; iter.id > -1; iter = iter.parent){
                iter.fs += fileSize;
                iter.bk += fileSize;
                iter.fc += 1;
            }

        // add node to hash
        node.hash_prev = hash[hashing(id)];
        node.hash_next = hash[hashing(id)].hash_next;
        node.hash_prev.hash_next = node;
        if(node.hash_next != null)
            node.hash_next.hash_prev = node;


        printTree(root, 0);
        fw.write(parent.fs +"\n");
        return parent.fs;
    }

    static int move(int id, int pid) throws Exception{
        System.out.println("move: " + id + " " + pid);
/**
 *  * 2. move:
 * 1) get child from hash
 * 2) get old parent from hash
 * 3) remove child from old parent
 * 4) if fs > 0 decrease fs, bk, fc from all old parents
 * 5) get new parent from hash
 * 6) add child to new parent
 * 7) if fs > 0, increase fs, bk, fc from all new parents
 */

        // get child from hash
        Tree node = getNode(id);
        Tree old_parent = node.parent;


        //remove child from old parent
//        for(Tree iter = old_parent.child.next; iter != null; iter = iter.next){
//            if(iter.id == id){
//                iter.prev.next = iter.next;
//                if(iter.next != null)
//                    iter.next.prev = iter.prev;
//                break;
//            }
 //       }


        // decrease fs, bk, fc from all parent
        for(Tree iter = old_parent; iter.id > -1; iter = iter.parent){
            iter.fs -= node.fs;
            iter.bk -= node.bk;
            iter.fc -= node.fc;
        }

        node.prev.next = node.next;
        if(node.next != null)
        node.next.prev = node.prev;

        // get new parent from hash
        Tree parent = getNode(pid);
        node.parent = parent;   //**************//

        //add child
        node.prev = parent.child;
        node.next = parent.child.next;
        node.prev.next = node;
        if(node.next != null)
            node.next.prev = node;



        //increase fs, bk, fc

        for(Tree iter = parent; iter.id > -1; iter = iter.parent){
            iter.fs += node.fs;
            iter.bk += node.bk;
            iter.fc += node.fc;
        }

        printTree(root, 0);
        fw.write(parent.fs +"\n");
        return parent.fs;
    }



    static void infectDFS(Tree node){
    	if(root.fc != 0 && node.fc != 0){
    		for(Tree iter = node.child.next; iter != null; iter = iter.next){
    			infectDFS(iter);
    		}
    		node.fs += (root.fs/root.fc)*node.fc;
    		System.out.println("infected: " + node.id + " " + (root.fs/root.fc)*node.fc);
    	}

    }

    static int infect(int id) throws Exception {

    	Tree node = getNode(id);

    	if(node.fc != 0){
    		infectDFS(node);
    	}

    	for(Tree iter = node.parent; iter.id > -1; iter = iter.parent){
    		iter.fs += (root.fs/root.fc)*node.fc;
    	}

        System.out.println("infect: " + id + " " + (root.fs/root.fc)*node.fc);

        printTree(root, 0);
        fw.write(node.fs +"\n");
    	return node.fs;
    }




/*
    static void infectDFS(Tree node, int size){
        for(Tree iter = node.child.next; iter != null; iter = iter.next){
            infectDFS(iter, size);
        }

        if(node.type == 1){
            node.fs += size;
            for(Tree iter2 = node.parent; iter2.id > -1; iter2 = iter2.parent){
                iter2.fs += size;
            }
        }
    }



    static int infect(int id) throws Exception{

// *  3. infect:
// * 1) get node from hash
// * 2) get infect size avg = fs/fc (if fc > 0)
// * 3) increase avg to all children as avg
// * 4) increase fs - bk to all parents


        Tree node = getNode(id);
        int avg = 0;
        if(root.fc > 0)
            avg = root.fs/root.fc;

        // increase avg to all children and all parents
        infectDFS(node, avg);

        System.out.println("infect: " + id + " " + avg);

        printTree(root, 0);
        fw.write(node.fs +"\n");
        return node.fs;
    }

*/
    static void recoverDFS(Tree node){
        node.fs = node.bk;
        for(Tree iter = node.child.next; iter != null; iter = iter.next){
            recoverDFS(iter);
        }
    }

    static int recover(int id) throws Exception{
        System.out.println("\nrecover: " + id);

        Tree node = getNode(id);
        int size = node.fs - node.bk;

        recoverDFS(node);

        for(Tree iter = node.parent; iter.id > -1; iter = iter.parent){
            iter.fs -= size;
        }

        printTree(root, 0);
        fw.write(node.fs +"\n");
        return node.fs;
    }


/*
    static void recoverDFS(Tree node) throws Exception{
        for(Tree iter = node.child.next; iter != null; iter = iter.next){
        	recoverDFS(iter);
        }

        int size = node.fs - node.bk;
        node.fs = node.bk;
        for(Tree iter2 = node.parent; iter2.id > -1; iter2 = iter2.parent){
        	iter2.fs -= size;
        }
    }

    static int recover(int id) throws Exception{
        System.out.println("recover: " + id);

 //        *  * 4. recover:
 //* 1) get node from hash
 //* 2) decrease fs - bk from all parents
 //* 3) set fs to bk for all children including node


        Tree node = getNode(id);
        recoverDFS(node);

        printTree(root, 0);
        fw.write(node.fs +"\n");
        return node.fs;
    }

    */

    static void removeDFS(Tree node) {
        for(Tree iter = node.child.next; iter != null; iter = iter.next){
            removeDFS(iter);
        }

        for(Tree iter2 = hash[hashing(node.id)].next; iter2 != null; iter2 = iter2.next){
            if(iter2.id == node.id){
                iter2.prev.next = iter2.next;
                if(iter2.next != null)
                    iter2.next.prev = iter2.prev;
                break;

            }
        }
    }


    static int remove(int id) throws Exception {
        System.out.println("remove: " + id);

        /*
         *  * 5. remove
         * 1) get node from hash
         * 2) get parent from hash
         * 3) decrease fs,bk,fc from all parents
         * 4) remove node from parent's child
         * 5) remove node from hash
         */

        Tree node = getNode(id);
        Tree parent = node.parent;

        // decrease fs, bk, fc
        for(Tree iter = parent; iter.id > -1; iter = iter.parent){
            iter.fs -= node.fs;
            iter.bk -= node.bk;
            iter.fc -= node.fc;
        }

        //remove node from parent
        for(Tree iter = parent.child.next; iter != null; iter = iter.next){
            if(iter.id == id){
                iter.prev.next = iter.next;
                if(iter.next != null)
                    iter.next.prev = iter.prev;
                break;
            }
        }

        removeDFS(node);


        //remove node from hash

        printTree(root, 0);
        fw.write(node.fs +"\n");
        return 0;
    }

    static void do_test(int tc) throws Exception{
        int n = sc.nextInt();
        int cmd;
        //       fw.write("\t n:" +n +"\n");

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
//            fw.write("tc: " + tc + " ");
            do_test(i);
        }

        fw.close();
    }
}