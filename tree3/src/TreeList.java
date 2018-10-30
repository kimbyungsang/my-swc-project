import java.util.Scanner;

class Node{
    String data;
    Node parent = null;
    Node child = null;
    Node sibling = null;

    Node(String data){
        this.data = data;

    }

}

class Tree{
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

    //TODO
    static void addSorted(Node parent, Node child){
        if(parent.child == null){
            parent.child = child;
            parent.child.parent = child;
        }else{
            Node temp = parent.child;
            if(isLarge(temp.data, child.data)) {
                child.sibling = parent.child.sibling;
                parent.child.sibling = child;


            }else{
                parent.child = child;
                child.sibling = temp;
            }

        }
    }

    static void add(Node parent, Node child){
        if(parent.child == null) {
            parent.child = child;
            parent.child.parent = parent;
        }

        else{
            Node temp = parent.child;
            while(temp.sibling != null)
                temp = temp.sibling;
            temp.sibling = child;
            temp.parent = parent;
        }

    }



    static Node delete(Node current, String path){
        Node child = current.child;
        Node pChild;

        if(child == null)
            return null;

        if(child.data.equals(path)){
            current.child = child.sibling;
            return current;
        }else{
            pChild = child;
            child = child.sibling;

            while(child != null){
                if(child.data.equals(path)){
                    pChild.sibling = child.sibling;
                    return current;

                }
                pChild = child;
                child = child.sibling;

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
                    tempChild = tempChild.sibling;
                }

                if(tempParent.sibling != null){
                    tempParent = tempParent.sibling;
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
        if(node.sibling != null)
            printTree(node.sibling, depth);

    }

    static void printChild(Node parent){
        Node child = parent.child;
        while(child != null) {
            System.out.print(child.data + " ");
            child = child.sibling;
        }
        System.out.println();

    }
}



public class TreeList {

    static int count = 0;

    static boolean isEqualString(String first, String second){
        if(first.length() != second.length())
            return false;
        for(int i=0; i < first.length();i++){
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
        Tree.add(current, child);

 //       Tree.printTree(current, 0);
        return current;
    }

    static Node cmd_cd(Node current, String path) {
        System.out.println(count++ + " cd " + path);
        if(path.equals("..")){
            if(current.parent != null)
                return current.parent;
            else return null;
        }
        Node cdNode = current.child;
        while(cdNode != null){
            if(cdNode.data.equals(path))
                return cdNode;
            cdNode = cdNode.sibling;
        }

        return null;

    }

    static int cmd_ls(Node current, String path) {
        if(path.equals("0"))
            Tree.printChild(current);

        return -1;

    }

    static Node cmd_rm(Node current, String path) {
        Node child = current.child;
        Node pChild;

        if(child == null)
            return null;

        if(child.data.equals(path)){
            current.child = child.sibling;
            return current;
        }else{
            pChild = child;
            child = child.sibling;

            while(child != null){
                if(child.data.equals(path)){
                    pChild.sibling = child.sibling;
                    return current;

                }
                pChild = child;
                child = child.sibling;

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
                        if(cdNode != null)
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

        }

        sc.close();
    }


}
