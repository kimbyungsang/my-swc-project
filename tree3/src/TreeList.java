class Node{
    char data;
    Node child = null;
    Node sibling = null;

    Node(char data){
        this.data = data;

    }

}

class Tree{
    static void add(Node parent, Node child){
        if(parent.child == null)
            parent.child = child;
        else{
            Node temp = parent.child;
            while(temp.sibling != null)
                temp = temp.sibling;
            temp.sibling = child;
        }

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
            System.out.print(" ");

        System.out.println(node.data);
        if(node.child != null)
            printTree(node.child, depth +1);
        if(node.sibling != null)
            printTree(node.sibling, depth);

    }
}



public class TreeList {

    public static void main(String[] args) throws Exception {
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

    }
}
