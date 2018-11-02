package org.swc;

class Node4{

    String data;
    Node4 parent = null;
    Node4 lSibling = null;
    Node4 rSibling = null;
    Node4 child = null;

    Node4(String data){
        this.data = data;

    }

    static boolean strcmp(String first, String second){
        if(first.length() != second.length())
            return false;

        for(int i=0;i < first.length(); i++){
            if(first.charAt(i) != second.charAt(i))
                return false;
        }
        return true;
    }

    static boolean isSmall(String newNode, String node){
        if(newNode.length() >= node.length()){
            for(int i =0;i < node.length(); i++){
                if(newNode.charAt(i) > node.charAt(i))
                    return false;
                else if(newNode.charAt(i) < node.charAt(i))
                    return true;
            }
            return false;
        }else {
            for(int i=0; i < newNode.length(); i++){
                if(newNode.charAt(i) > node.charAt(i))
                    return false;
                else if (newNode.charAt(i) < node.charAt(i))
                    return true;
            }
            return true;
        }
    }

    static Node4 addSorted(Node4 parent, Node4 child){
        if(parent == null)
            return child;

        if(parent.child == null)
            parent.child = child;
        else{
            Node4 head = parent.child;
            Node4 last = head.lSibling;

            if(isSmall(child.data, head.data)){  // first
                last = head.lSibling;
                head.lSibling = child;
//                last.rSibling = child;
                child.rSibling = head;
//                child.lSibling = last;

                parent.child = child;

            }/*
            else if(!isSmall(child.data, head.lSibling.data)){ //last
                head.lSibling = child;
                last.rSibling = child;
//                child.rSibling = head;
                child.lSibling = last;

                parent.child = head;
            } */
            else{
                last = head.rSibling;
                while(last != last.rSibling){
                    if(isSmall(child.data, last.data)){
                        child.lSibling = last.lSibling;
                        child.rSibling = last;
                        last.lSibling.rSibling = child;
                        last.lSibling = child;

                        parent.child = head;
                        break;
                    }

                }
            }


        }


        return parent;
    }

    static Node4 add(Node4 parent, Node4 child){

        if(parent.child == null) {
            parent.child  = child;
        }
        else{
            Node4 temp = parent.child;
            while(temp.rSibling != null){
                temp = temp.rSibling;
            }
 //           Node4 last = head.lSibling;
            temp.rSibling = child;
 //           last.rSibling = child;
 //           child.lSibling = last;
            child.lSibling = temp;

        }

        return parent;
    }

    static void printTree(Node4 node, int depth){
        for(int i=0; i < depth; i++)
            System.out.print("  ");

        System.out.println(node.data);
        if(node.child != null)
            printTree(node.child, depth+1);
        if(node.rSibling != null){
 /*           for(int i=0; i < depth; i++)
                System.out.print("  ");
            System.out.println(sibling.rSibling.data);
            sibling = sibling.rSibling;
            */
            printTree(node.rSibling, depth);
        }


    }

}

public class TreeNode4 {

    public static void main(String[] args) throws Exception {


        Node4 root = new Node4("/");

        Node4 nodeB = new Node4("abc");
        Node4 nodeC = new Node4("aab");
        Node4 nodeD = new Node4("aba");

        Node4 nodeE = new Node4("kkk");
        Node4 nodeF = new Node4("jk");

        Node4 nodeG = new Node4("kk2");




/*
        Node4.add(root, nodeB);
        Node4.add(root, nodeC);
        Node4.add(root, nodeD);
        Node4.add(nodeB, nodeE);
        Node4.add(nodeB, nodeF);
        Node4.add(nodeE, nodeG);
*/

        Node4.addSorted(root, nodeB);
        Node4.addSorted(root, nodeC);
        Node4.addSorted(root, nodeD);
        Node4.addSorted(nodeB, nodeE);
        Node4.addSorted(nodeB, nodeF);
        Node4.addSorted(nodeE, nodeG);

        Node4.printTree(root, 0);
    }

}
