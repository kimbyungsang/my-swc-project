import java.util.Scanner;

class Node{
    String data;
    Node parent = null;
    Node child = null;
    Node rs = null;
    Node ls = null;

    Node(String data){
        this.data = data;

    }

}

class Tree{
    static boolean strcmp(String first, String second){
        if(first.length() != second.length())
            return false;

        for(int i=0;i < first.length(); i++){
            if(first.charAt(i) != second.charAt(i))
                return false;
        }
        return true;
    }

    static boolean isLarge(String first, String second){
        int length;
        if(first.length() <= second.length()){
            length = first.length();
            for(int i=0; i < length;i++) {
                if (first.charAt(i) < second.charAt(i)) {
                    return true;
                } else if (first.charAt(i) > second.charAt(i)) {
                    return false;
                }
            }

            return true;
        }else{
            length = second.length();
            for(int i=0; i < length;i++){
                if(first.charAt(i) < second.charAt(i)){
                    return true;
                }else if(first.charAt(i) > second.charAt(i)){
                    return false;
                }
            }
            return false;
        }
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

    static Node addSorted(Node parent, Node newNode){
        if(parent == null)
            return newNode;

        if(parent.child == null)
            parent.child = newNode;
        else{
            if(isSmall(newNode.data, parent.child.data)){  // first
                newNode.rs = parent.child;
                parent.child.ls = newNode;
                parent.child = newNode;


            }
            else {
                Node temp = parent.child;
                while (temp.rs != null) {
                    if (isSmall(newNode.data, temp.data)) {

                        newNode.ls = temp.ls;
                        newNode.rs = temp;
                        newNode.parent = parent;
                        temp.ls.rs = newNode;
                        temp.ls = newNode;
                        break;
                    }
                    temp = temp.rs;

                }
                temp.rs = newNode;
                newNode.ls = temp;
                newNode.parent = parent;

            }
        }


        return parent;
    }



    static Node add(Node parent, Node child){
        if(parent.child == null) {
            parent.child = child;
            parent.child.parent = parent;
        }

        else{
            Node temp = parent.child;
            while(temp.rs != null)
                temp = temp.rs;
            temp.rs = child;
            child.ls = temp;
            temp.parent = parent;
        }

        return parent;

    }



    static Node delete(Node current, String path){
        Node child = current.child;
        Node pChild;

        if(child == null)
            return null;

        if(strcmp(child.data, path)){
            current.child = child.rs;
            return current;
        }else{
            pChild = child;
            child = child.rs;

            while(child != null){
                if(child.data.equals(path)){
                    pChild.rs = child.rs;
                    return current;

                }
                pChild = child;
                child = child.rs;

            }

        }
        return null;
    }

    static void printLevel(Node node, int level){
        int depth = 0;
        Node tempChild = node;
        Node tempParent = node;

        while(depth <= level){
            if(depth == level){
                while(tempChild != null){
                    System.out.print(tempChild.data + " ");
                    tempChild = tempChild.rs;
                }

                if(tempParent.rs != null){
                    tempParent = tempParent.rs;
                    tempChild = tempParent.child;
                }else{
                    break;
                }
            }else{
                tempParent = tempChild;
                tempChild = tempChild.child;
                depth++;
            }
        }

    }

    static void printTree(Node node, int depth){
        for(int i=0; i < depth; i++)
            System.out.print("  ");

        System.out.println(node.data);
        if(node.child != null)
            printTree(node.child, depth +1);
        if(node.rs != null)
            printTree(node.rs, depth);

    }

    static void printChild(Node parent){
        Node child = parent.child;
        while(child != null) {
            System.out.print(child.data + " ");
            child = child.rs;
        }
        System.out.println();

    }
}



public class TreeList {

    static int count = 0;

    static boolean strcmp(String first, String second){
        if(first.length() != second.length())
            return false;

        for(int i=0;i < first.length(); i++){
            if(first.charAt(i) != second.charAt(i))
                return false;
        }
        return true;
    }



    static void cmd_init() {
    }

    static Node cmd_mkdir(Node current, String name){
        System.out.println(count++ + " mkdir " + name);
        Node child = new Node(name);
        current = Tree.add(current, child);
//        current = Tree.addSorted(current, child);


        //       Tree.printTree(current, 0);
        return current;
    }

    static Node cmd_cd(Node current, String path) {
        System.out.println(count++ + " cd " + path);
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
        }

        return null;

    }

    static int cmd_ls(Node current, String path) {
        System.out.println(count++ + " ls " + path);
        if(path.equals("0"))
            Tree.printChild(current);

        return -1;

    }

    static Node cmd_rm(Node current, String path) {

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

        }
        return null;

    }



    public static void main(String[] args) throws Exception {

       /*
        Node root = new Node('A');
        Node nodeB = new Node('B');
        Node nodeC = new Node('C');
        Node nodeD = new Node('D');
        Node nodeE = new Node('E');


        Tree.add(root, nodeB);
        Tree.add(root, nodeC);
        Tree.add(nodeB, nodeD);
        Tree.add(nodeD, nodeE);

        Tree.printTree(root, 0);

        for(int i=0; i<4; i++){
            System.out.println("\nLevel : " +i);
            Tree.printLevel(root,i);
        }

        */

        System.setIn(new java.io.FileInputStream("tree/sample_input.txt"));
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        int seed = sc.nextInt();
        int trials = sc.nextInt();
        cmd_init();
        Node current;
        Node root = new Node("/");
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
                        Node cdNode = cmd_cd(current, value);
                        if(cdNode == null){
                            System.out.println("This is root.");
                            break;
                        }
                        current = cdNode;
                        break;
                    case 3:
                        cmd_rm(current, value);
                        break;
                    case 4:
                        cmd_ls(current, value);
                        break;
                    default:
                        break;
                }
                Tree.printTree(root, 0);

            }
            Tree.printLevel(root, 1);

        }

        sc.close();
    }


}
