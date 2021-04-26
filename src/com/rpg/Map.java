package com.rpg;

public class Map
{
	//Get the number of treasures in it
	public static int getTreasureNum(int map[][])
	{
		int num = 0 ;

		for(int i = 0 ; i<map.length;i++)
		{
			for(int j=0; j< map[0].length;j++)
			{
				if(map[i][j]==3)
				{
					num++;
				}

			}
		}
		return num;
	}
}
