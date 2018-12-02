package org.swc.database;


class Hash{
    long hkey;
    int idx;
    int field;
//    boolean delete = false;
    Hash next = null;
    Hash prev = null;


    Hash(long hkey, int idx, int field){
        this.hkey = hkey;
        this.idx = idx;
        this.field = field;
    }
}
class UserSolution2 {

    String[][] dbTable;
    int dbPos = 0;
    Hash[] hashTable;

    private long hashing(String str){
        long hash = 5381;
        for(int i=0; i < str.length();i++){
            int c = (int)str.charAt(i);
            hash = ((hash << 5) + hash) + c;
        }
        if (hash < 0) hash *= -1;
        return hash;
    }


    void InitDB(){
        dbTable = new String[50000][5];
        dbPos = 0;
        hashTable = new Hash[250000];
        for(int i = 0; i < 250000; i++){
            hashTable[i] = new Hash(-1, -1, -1);

        }
    }

    void hashAdd(long hkey, int idx, int field){
        int key = (int)(hkey % 250000);
        Hash node = new Hash(hkey, idx, field);

        node.next = hashTable[key].next;
        node.prev = hashTable[key];
        node.next.prev = node;
        node.prev.next = node;

    }

    void Add(String name, String number, String birthday, String email, String memo){
        dbTable[dbPos][0] = name;
        dbTable[dbPos][1] = number;
        dbTable[dbPos][2] = birthday;
        dbTable[dbPos][3] = email;
        dbTable[dbPos][4] = memo;

        hashAdd(hashing(name), dbPos, 0);
        hashAdd(hashing(number), dbPos, 1);
        hashAdd(hashing(birthday), dbPos, 2);
        hashAdd(hashing(email), dbPos, 3);
        hashAdd(hashing(memo), dbPos, 4);

        dbPos++;

    }

    int Delete(int field, String str){
        int delCount = 0;

        long hkey = hashing(str);
        int key = (int)(hkey % 250000);

        for(Hash iter = hashTable[key].next; iter != null; iter = iter.next){
            if(iter.hkey == hkey && iter.field == field){
                iter.next.prev = iter.prev;
                iter.prev.next = iter.next;
                delCount++;
            }
        }
/*
        for(Hash node = hsTable[key].next; node != null; node = node.next){
            if(node.hkey == hkey && node.field == field && !node.delete){
                node.delete = true;
                delCount++;
            }
        }*/

        return delCount;
    }

    int Change(int field, String str, int changefield, String changestr){
        int changeCount = 0;
        long hkey = hashing(str);
        int key = (int)(hkey % 250000);

        for(Hash iter = hashTable[key].next; iter != null; iter = iter.next){
            if(iter.hkey == hkey && iter.field == field){
                String changedStr = dbTable[iter.idx][changefield];
                dbTable[iter.idx][changefield] = changestr;
                hashAdd(hashing(changestr), iter.idx, changefield);

                Delete(changefield, changedStr);

            }
            changeCount++;
        }

/*        for(Hash node = hsTable[key].next; node != null; node = node.next){
            if(node.hkey == hkey && node.field == field && !node.delete){
                String orgin = dbTable[node.idx][changefield];
                dbTable[node.idx][changefield] = changestr;
                hashAdd(hashing(changestr), node.idx, changefield);

                long hkey2 = hashing(orgin);
                int key2 = (int)(hkey2 % 250000);

                for(Hash node2 = hsTable[key2].next; node2 != null; node2 = node2.next){
                    if(node2.hkey == hkey2 && node2.field == changefield && !node2.delete){
                        node2.delete = true;
                    }
                }
            }
            changeCount++;
        }*/


        return changeCount;
    }

    Solution.Result Search(int field, String str, int returnfield){
        long hkey = hashing(str);
        int key = (int)(hkey % 250000);
        Solution.Result result = new Solution.Result();

        for(Hash iter = hashTable[key].next; iter != null; iter = iter.next){
            if(iter.hkey == hkey && iter.field == field){
                result.count++;
                result.str = dbTable[iter.idx][returnfield];
            }
        }


/*        for(Hash node = hsTable[key].next; node != null; node = node.next) {
            if(node.hkey == hkey && node.field == field && !node.delete){
                result.count++;
                result.str = dbTable[node.idx][returnfield];
            }

        }*/

            return result;
    }

}
