package org.swc;

import java.util.Scanner;

class ListNode{
    int childIndex;
    String childValue;
    ListNode prev;
    ListNode next;

    public ListNode(int index, String value){
        childIndex = index;
        childValue = value;
        prev = null;
        next = null;
    }


    public ListNode append(ListNode head, int childIndex, String childValue){
        if(head == null) {
            ListNode node = new ListNode(childIndex, childValue);
            head = node;
        }else if(isHead(head.childValue, childValue)){
            ListNode node = new ListNode(childIndex, childValue);
            node.next = head;
            head.prev = node;
            head = node;
        }else {
            ListNode temp = find(head, childValue);
        }
        return head;
    }

    public ListNode find(ListNode head, String value){
        while(head != null){
            head.childValue
        }

    }

    public boolean isHead(String first, String second){
        for(int i = 0; i < first.length();i++){
            if(first.charAt(i) > second.charAt(i))
                return true;
            else if(first.charAt(i) < second.charAt(i)){
                return false;
            }
        }
        return false;
    }




/*
    public static ListNode findLoc(ListNode head, String data){
        while(head.next != null){
            for(int i=0; i < head.data.length(); i++){
                if(head.data.charAt(i) == data.charAt(i)){
                    if(i == head.data.length() -1) {
                        if (head.data.length() == data.length())
                            return null;
                        else {
                            head = head.next;
                            break;
                        }
                    }

                }else if (head.data.charAt(i) > data.charAt(i)){
                    return head.prev;
                }else if (head.data.charAt(i) < data.charAt(i)) {
                    head = head.next;
                    break;
                }
            }
        }
        return head;
    }

    public static ListNode findLocation(ListNode head, String data){
        for(int i = 0; i < head.data.length(); i++){
            if(head.data.charAt(i) == data.charAt(i)){
                if(i == head.data.length() -1 && head.data.length() == data.length())
                    return null;
            }else if (head.data.charAt(i) > data.charAt(i)){
                return head;
            }else if (head.data.charAt(i) < data.charAt(i)) {
                if(head.next != null)
                    return findLocation(head.next, data);
            }

        }
        return head;
    }
    public static int isHead(ListNode head, String data){
        for(int i = 0; i < head.data.length(); i++){
            if(head.data.charAt(i) == data.charAt(i)){
                if(i == head.data.length() -1 && head.data.length() == data.length())
                    return -1;
            }else if(head.data.charAt(i) > data.charAt(i)){
                return 1;
            }else {
                return 0;
            }
        }
        return 0;
    }

    public static ListNode insertListNode(ListNode head, String data){
        ListNode node = new ListNode();
        node.data = data;
        ListNode loc;

        if(head == null){
            head = node;
        }else{
            int unique = isHead(head, data);
            if(unique == 1) {
                node.next = head;
                head.prev = node;
                head = node;
            }else if(unique == -1){
                System.out.println("Duplicated..!");
            }else {
                loc = findLoc(head, data);
                if(loc == null) {
                    System.out.println("Duplicated..!");

                }else{
                    node.prev = loc;
                    node.next = loc.next;
                    loc.next = node;
                    loc.next.prev = node;
                }
            }
        }

        return head;

    }

    public static ListNode appendListNode(ListNode head, String data){
        ListNode node = new ListNode();
        node.data = data;
        if(head == null){
            head = node;
        }else{
            ListNode last = head.prev;
            last.next = node;
            head.prev = node;
            node.prev = last;
            node.next = head;
        }
        return head;
    }


    public static ListNode removeListNode(ListNode head, ListNode node){
        if(head == head.next){
            return null;
        }else{
            ListNode prevNode = node.prev;
            ListNode nextNode = node.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
            return (head == node) ? nextNode : head;
        }
    }

    public static void printNode(ListNode head){
        while(head.next != null){
            System.out.print(head.data + " ");
            head = head.next;
        }
        System.out.println(head.data);
    }
    */
}

public class PSLinkedList {
    public static void main(String args[]) throws Exception
    {
        /*
        System.setIn(new java.io.FileInputStream("tree/ll_input.txt"));
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        for (int test_case = 1; test_case <= T; test_case++)
        {
            ListNode head = null;
            int N = sc.nextInt();
            for (int i = 0; i < N; i++)
            {
                String data = sc.next();
                if(data.equals("abccccccc"))
                    System.out.println(data);
                head = ListNode.insertListNode(head, data);
                ListNode.printNode(head);
            }

            ListNode node = head;
//            while(head != head.next)
//            {
//                ListNode nextNode = node.next;
//                head = ListNode.removeListNode(head, node);
//                node = nextNode.next.next;
//            }
            System.out.println("#"+test_case + " " + head.data);
        }
        sc.close();
        */
    }
}
