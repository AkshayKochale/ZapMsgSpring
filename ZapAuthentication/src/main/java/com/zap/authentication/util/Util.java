package com.zap.authentication.util;

import java.util.Random;

public class Util 
{
	static String DRAGONBALL_CHARACTERS[]= {"Goku","Vegeta","Trunk","Berrus","Gohan","JIREN","FREEZA","CHAMPA","BORLY","CELL"};
	
	public static String RandomPasswordGenerator() 
	{
		char arr[]=new char[8];
		 Random random = new Random();
		
		for(int i=0;i<8;i++)
		{
			int randomIndex = random.nextInt(DRAGONBALL_CHARACTERS.length);
            String animeCharacter = DRAGONBALL_CHARACTERS[randomIndex];

            int randomCharIndex = random.nextInt(animeCharacter.length());
            arr[i] = animeCharacter.charAt(randomCharIndex);
		}
		
		
		return new String(arr);
	}

}
