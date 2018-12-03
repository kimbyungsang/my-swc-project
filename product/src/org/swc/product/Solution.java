package org.swc.product;


import java.util.Scanner;

class Product{
    int id  = 1000001;
    String name = "dummy";
    String fac = "dummy";
    int date = 21;
    long nkey;
    long fkey;

    Product end = null;
    Product next = null;
    Product prev = null;

    Product(){
        id = 1000001;
        date = 21;
    }

    Product(int id, String name, String fac, int date){
        this.id = id;
        this.date = date;
        this.name = name;
        this.fac = fac;
    }
}

public class Solution {
    static void reset(){
        goods = new Product[MAX_ITEM];
        for(int i=0; i < MAX_ITEM; i++){
            goods[i] = new Product();
            goods[i].end = new Product();
            goods[i].next = goods[i].end;
            goods[i].end.prev = goods[i];
        }
    }

    static long hashing(String name){
        long hash = 5381;
        for(int i=0; i < name.length(); i++){
            int c = (int)name.charAt(i);
            hash = ((hash << 5) + hash) + c;
        }
        if(hash < 0) hash *= -1;
        return hash;
    }

    static int getKey(long nkey){
        return (int) nkey % MAX_ITEM;
    }

    static void printProduct(){
/*        System.out.println();
        for(int i = 0; i < MAX_ITEM; i++){
            if(goods[i].next != goods[i].end){
                System.out.print(i + "\t");
                for(Product iter = goods[i].next; iter != goods[i].end; iter = iter.next)
                    System.out.print(iter.id+"/"+iter.name+"/"+iter.fac+"/"+iter.date+" ");
                System.out.println();
            }
        }*/
    }

    static void production(int id, String name, String fac, int date){
        Product node = new Product(id, name, fac, date);
        node.end = new Product();
        node.next = node.end;
        node.end.prev = node;

        node.nkey = hashing(name);
        node.fkey = hashing(fac);
        int key = getKey(hashing(name));

        node.next = goods[key].next;
        node.prev = goods[key];
        node.next.prev = node;
        node.prev.next = node;

        printProduct();
    }

    static int sale(int n, String[] buy){
        Product sold = new Product();

        int key = 0;
        for(int i =0; i < n; i++){
            key = getKey(hashing(buy[i]));
            for(Product iter = goods[key].next; iter != goods[key].end; iter = iter.next){
                if(iter.date < sold.date && iter.date >= 0){
                    sold = iter;
                }else if (iter.date == sold.date && iter.id < sold.id){
                    sold = iter;
                }
            }
        }
        if(sold.id == 1000001)
            return -1;

        sold.next.prev = sold.prev;
        sold.prev.next = sold.next;

        printProduct();
        return sold.id;
    }

    static int removeOverDate(){
        int count = 0;
        for(int i =0; i < MAX_ITEM; i++){
            for(Product iter = goods[i].next; iter != goods[i].end; iter = iter.next){
                if(iter.date < 0){
                    iter.next.prev = iter.prev;
                    iter.prev.next = iter.next;
                    count++;
                }
            }
        }
        printProduct();
        return count;
    }

    static int findFacStock(String fac){
        long fkey = hashing(fac);
        int count = 0;
        for(int i = 0; i < MAX_ITEM; i++){
            for(Product iter = goods[i].next; iter != goods[i].end; iter = iter.next){
                iter.date -= 1;
                if(iter.fkey == fkey)
                    count++;
            }
        }

        printProduct();

        return count;
    }


    static final int MAX_ITEM = 107;
    static Product[] goods;

    public static void main(String[] args) throws Exception{
        Scanner sc;
        System.setIn(new java.io.FileInputStream("product/input.txt"));
        sc = new Scanner(System.in);

        int T, order;
        T = sc.nextInt();
        int n;
        String[] buy = new String[MAX_ITEM];

        for(int i = 0; i < T; i++){
            reset();
            while(true){
                order = sc.nextInt();
                if(order == 1){
                    production(sc.nextInt(), sc.next(), sc.next(), sc.nextInt());
                }else if(order == 2){
                    n = sc.nextInt();
                    for(int j = 0; j < n; j++)
                        buy[j] = sc.next();
                    System.out.println(sale(n, buy));
                }else if(order == 3){
                    System.out.println(removeOverDate());
                }else if(order ==4){
                    System.out.println(findFacStock(sc.next()));
                }else if(order == 5){
                    break;
                }
            }
        }

        sc.close();
    }
}
