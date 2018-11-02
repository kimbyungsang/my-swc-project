package org.swc.database;

class ListNode {
    int idx;
    int field;
    ListNode prev;
    ListNode next;

    public ListNode()
    {
        idx = -1;
        field = -1;
        prev = this;
        next = this;
    }

    public static ListNode appendListNode(ListNode head, int idx, int field)
    {
        ListNode node = new ListNode();
        node.idx = idx;
        node.field = field;
        if (head == null)
        {
            head = node;
        }
        else
        {
            ListNode last = head.prev;
            last.next = node;
            head.prev = node;
            node.prev = last;
            node.next = head;
        }
        return head;
    }

    public static ListNode removeListNode(ListNode head, ListNode node)
    {
        if (head == head.next)
        {
            return null;
        }
        else
        {
            ListNode prevNode = node.prev;
            ListNode nextNode = node.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
            return (head == node) ? nextNode : head;
        }

    }
}

class Hashtable
{
    class Hash {
        //        String key;
        long hKey;
        ListNode head;
    }

    int capacity;
    Hash tb[];

    public Hashtable(int capacity){
        this.capacity = capacity;
        tb = new Hash[capacity];
        for (int i = 0; i < capacity; i++){
            tb[i] = new Hash();
            tb[i].head = null;
        }
    }

    private long hash(String str)
    {
        long hash = 5381;

        for (int i = 0; i < str.length(); i++)
        {
            int c = (int)str.charAt(i);
            hash = ((hash << 5) + hash) + c;
        }
        if (hash < 0) hash *= -1;
        //return hash % capacity;
        return hash;
    }

    public ListNode find(String key){
        long hKey = hash(key);
        int h = (int)(hKey % capacity);
        int cnt = capacity;
        ListNode head = null;
        while(tb[h].hKey != 0 && (--cnt) != 0)
        {
            if (tb[h].hKey == hKey){
                return  tb[h].head;
            }
            h = (h + 1) % capacity;
        }

        head = new ListNode();

        return head;
    }

/*    public boolean change(int field, String key, int cField, String cKey){
    	ListNode head = find(key);
    	ListNode node;
    	ListNode last;


    	node = head;
    	last = head.prev;
    	while(node.next != last){
    		if(node.field == field){
    			adb2[]
    		}
    	}



    	return true;
    }*/

    public boolean delete(String key, int idx, int field){
        long hKey = hash(key);
        int h = (int)(hKey % capacity);
//        int h = hash(key);
        int cnt = capacity;
        ListNode head;
        ListNode node;
        ListNode last;
        while(tb[h].hKey != 0 && (--cnt) != 0)
        {
            if (tb[h].hKey == hKey){
                head =  tb[h].head;
                node = head;
                last = head.prev;


                while(node != last){
                    if(node.field == field && node.idx == idx){
                        tb[h].head = ListNode.removeListNode(head, node);
//                        head = tb[h].head;
                        return true;
                    }
                    node = node.next;
                }

                if(node.field == field && node.idx == idx){
                    tb[h].head = ListNode.removeListNode(head, node);
                    return true;
                }

            }
            h = (h + 1) % capacity;
        }

        return false;

    }

    boolean add(String key, int idx, int field)
    {
//        key.charAt(0)
        long hKey = hash(key);
        int h = (int)(hKey % capacity);
        while(tb[h].hKey != 0)
        {
            if (tb[h].hKey == hKey){
                tb[h].head = ListNode.appendListNode(tb[h].head, idx, field);
                return true;
            }
            h = (h + 1) % capacity;
        }
        tb[h].hKey = hKey;
        tb[h].head = ListNode.appendListNode(tb[h].head, idx, field);
//        System.out.println("ADD:" + h + " "+ key + " "+field+ " " + idx);
        return true;
    }
}


class UserSolution {


    int dbPos = 0;
    Hashtable tb = null;

    String[][] adb2;

    void InitDB()
    {
        adb2 = new String[50000][5];
        dbPos = 0;
        tb = new Hashtable(150000);
    }

    void Add(String name, String number, String birthday, String email, String memo)
    {



        adb2[dbPos][0] = name;
        adb2[dbPos][1] = number;
        adb2[dbPos][2] = birthday;
        adb2[dbPos][3] = email;
        adb2[dbPos][4] = memo;




/*    	if(birthday.equals("19520624")){
    		System.out.println("Get..!");
    	}*/
//    	System.out.println("dbPos: " + dbPos + " " + name +" " + number + " " + birthday + " " + email + " " + memo);

        tb.add(name, dbPos, 0);
        tb.add(number, dbPos, 1);
        tb.add(birthday, dbPos, 2);
        tb.add(email, dbPos, 3);
        tb.add(memo, dbPos, 4);

        dbPos++;



    }


    int Delete(int field, String str)
    {
        int delCount = 0;
        ListNode head = tb.find(str);
        if(head == null)
            return delCount;

        ListNode last = head.prev;

        while(head != last){
            if(head.field == field){
                for(int i=0; i < 5; i++){
                    tb.delete(adb2[head.idx][i], head.idx, i);
                }
                head = head.next;
                delCount++;

            }
        }


        if(head.field == field){
            for(int i=0; i < 5; i++){
                tb.delete(adb2[head.idx][i], head.idx, i);
            }
            delCount++;

        }



        return delCount;
    }

    int Change(int field, String str, int changefield, String changestr)
    {
/*    	if(changestr.equals("19520624")){
    		System.out.println("Get..!");
    	}*/


        ListNode head = tb.find(str);
        ListNode node;
        ListNode last;

        int chgCount = 0;

        if(head == null)
            return chgCount;

        node = head;
        last = head.prev;
        while(head != last){
            if(head.field == field){
                tb.delete(adb2[head.idx][changefield], head.idx, changefield);
                adb2[head.idx][changefield] = changestr;
                //   			tb.delete(str, head.idx, head.field);
                tb.add(changestr, head.idx, changefield);
                chgCount++;
            }
            head = head.next;
        }

        if(head.field == field){
            tb.delete(adb2[head.idx][changefield], head.idx, changefield);
            adb2[head.idx][changefield] = changestr;
//   			tb.delete(str, head.idx, head.field);
            tb.add(changestr, head.idx, changefield);
            chgCount++;

        }

        return chgCount;
    }

    Solution.Result Search(int field, String str, int returnfield)
    {
        /*
        if(str.equals("ptnnmzjgldlur@f.com")){
            System.out.println("Get..!");
            ListNode head = tb.find(check);
            System.out.println("");
        }
        */
        Solution.Result result = new Solution.Result();
        result.count = 0;
        result.str = "NULL";


        ListNode head = tb.find(str);
        if(head == null)
            return result;

        ListNode last = head.prev;



        while(head != last){
            if(head.field == field){
                result.count++;
                result.str = adb2[head.idx][returnfield];
            }
            head = head.next;
        }

        if(head.field == field){
            result.count++;
            result.str = adb2[head.idx][returnfield];
        }

        return result;
    }
}