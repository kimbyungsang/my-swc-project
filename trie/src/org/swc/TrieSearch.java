package org.swc;


class NodeTree{

    public static int CHILD_SIZE = 26;

    char word;
    boolean end;
    NodeTree node[];

    NodeTree(int childLen){
        word = '0';
        end = false;
        node = new NodeTree[childLen];
        for(int i=0; i < childLen; i++){
            node[i] = null;
        }
    }

    void insert(NodeTree root, String str){
        NodeTree now = root;
        char token = '0';
        for(int i=0; i < str.length(); i++){
            NodeTree newNode = new NodeTree(CHILD_SIZE);
            token = str.charAt(i);
            newNode.word = token;
            if(now.node[token - 'a'] == null)
                now.node[token - 'a']  = newNode;
            now = now.node[token - 'a'];
        }

        now.word = token;
        now.end = true;
    }

    void showTree(NodeTree now, char[] str, int depth){
        if(now.end) {
            for (int i = 0; i < depth; i++)
                System.out.print(str[i]);
            System.out.println();
        }

        for(int i=0; i < 26; i++){
            if(now.node[i] != null){
                str[depth] = now.node[i].word;
                showTree(now.node[i], str, depth+1);
            }
        }

    }

    NodeTree findRoot(NodeTree root, String str){
        char token = '0';
        int index = 0;
        NodeTree now = root;
        for(int i = 0; i < str.length(); i++){
            token = str.charAt(i);
            index = token - 'a';
            if (now.node[index] == null){
                return null;
            }
            now = now.node[index];
        }
        return now;

    }

    boolean isIndexed(NodeTree root, String str){
        char token = '0';
        int index = 0;
        NodeTree now = root;
        for(int i =0; i < str.length();i++){
            token = str.charAt(i);
            index = token-'a';
            if(now.node[index] == null)
                return false;
        }

        return true;
    }
}

public class TrieSearch {

    public static void main(String arg[]) throws Exception{
        String[] test2 = {"below", "blue", "ban", "bzak", "bzu", "a", "im", "i"};

        NodeTree _root = new NodeTree(26);
        for(int i=0; i < test2.length; i++){
            _root.insert(_root, test2[i]);
        }

        char[] temp = new char[20];

        _root.showTree(_root, temp, 0);

        NodeTree sub = _root.findRoot(_root, "b");
        _root.showTree(sub, temp, 0);

        System.out.println(_root.isIndexed(_root, "ban"));

        System.out.println(_root.isIndexed(_root, "bak"));

    }

}
