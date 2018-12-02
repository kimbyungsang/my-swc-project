package org.swc;

import java.util.Random;


/*
    srand(3); // 3 will be changed

    for (int tc = 1; tc <= 10; tc++)
    {
        for (int c = 0; c < MAX; c++)
        {
            int base = 1 + (rand() % 6);
			for (int y = 0; y < 4; y++)
			{
				for (int x = 0; x < 4; x++)
				{
					module[c][y][x] = base + (rand() % 3);
				}
			}
        }
		cout << "#" << tc << " " << makeBlock(module) << endl;
    }
 */
class Hash{

}
public class block {
    static final int MAX_ITEM=3;
    static int module[][][];

    public static void main(String args[]){
        Random rand = new Random(3);
        module = new int[MAX_ITEM][4][4];

        for(int tc = 1; tc <= 10; tc++){
            for(int c = 0; c < MAX_ITEM; c++){
                int base = 1 + (rand.nextInt() % 6);
                for(int y =0;  y < 4; y++){
                    for (int x = 0; x < 4; x++){
                        module[c][y][x] = base + (rand.nextInt() % 3);
                    }
                }
            }
        }

        System.out.println("Test for java");
        int three [] = new int[16];
        int pattern[] = {2,1,1,1,0,1,1,2,0,2,0,1,1,2,0};

        for(int i=0; i < 16; i++){
            int k = 3;
            for(int j = 0; j < i; j++){
                k *= 3;
            }
            three[i] = k;
        }
        for(int i = 0; i < 15; i++){
            System.out.println(three[i]);
        }
    }
}
