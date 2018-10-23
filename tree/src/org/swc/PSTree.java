package org.swc;


import java.util.Scanner;

class Tree {

    class TreeNode{
        int parent;
        String value;
        ListNode head;

        public TreeNode (int parent, String value){
            this.parent = parent;
            this.value = value;
            head = null;
        }
    }

    TreeNode[] treenode;
    int nodeNum;
    int index = 0;

    public Tree(int nodeNum){
        this.nodeNum = nodeNum;
        index = 0;
        treenode = new TreeNode[nodeNum + 1];
        for(int i=0; i <= nodeNum; i++){
            treenode[i] = new TreeNode(-1, "/");
        }
    }

    public void printChild(ListNode head){
        while(head != null){
            System.out.print(head.childValue + " ");
            head = head.next;
        }
        System.out.println();
    }

    public int addChild(int parent, int child_index, String child_value){
        ListNode head = treenode[parent].head;
        if(head == null){
            ListNode node = new ListNode(child_index, child_value);
            treenode[parent].head = node;
            treenode[child_index].parent = parent;
            treenode[child_index].value = child_value;
            printChild(treenode[parent].head);
            return parent;
        }
        ListNode temp = head;
        while(temp != null){
            if(temp.childValue.equals(child_value)){
                printChild(treenode[parent].head);
                return -2;
            }
            temp = temp.next;
        }
        treenode[parent].head = head.append(head, child_index, child_value);
        treenode[child_index].parent = parent;
        treenode[child_index].value = child_value;
        printChild(treenode[parent].head);
        return parent;

    }
/*
    public int getChild(int parent, String child){
        ListNode head = treenode[parent].node;

    }

    public int getRoot(){
        for(int i=0; i < nodeNum; i++){
            if(treenode[i].parent == -1){
                return i;
            }
        }

        return -1;
    }

    public void preOrder(int root){
        System.out.println("root: " + root);

        for(int i=0; i < MAX_CHILD_NUM; i++){
            int child = treenode[root].child[i];
            if(child != -1)
            {
                preOrder(child);
            }
        }

    }

    */
}

class PSTree {
    public static void main(String arg[]) throws Exception {
  /*
        System.setIn(new java.io.FileInputStream("tree/tree_input.txt"));
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();

        for (int test_case = 1; test_case <= T; ++test_case)
        {
            int node = sc.nextInt();
            int edge = sc.nextInt();

            Tree tree = new Tree(node);

            for (int i = 0; i < edge; i++)
            {
                int parent = sc.nextInt();
                int child = sc.nextInt();
                tree.addChild(parent, child);
            }

            int root = tree.getRoot();
            System.out.printf("#%d ", test_case);
            tree.preOrder(root);
            System.out.printf("\n");

        }

        sc.close();
        */
    }
}
