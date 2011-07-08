package com.anglefish.game.torus3dpro;

import android.os.Environment;

import java.io.*;
import java.util.ArrayList;

public class Best{
	ArrayList<Records> performances;
	String directory;
	
	Best()
	{
		initBestRecords();
	}
	
	class Records
	{
		ArrayList<Record> records;
		Records(ArrayList<Record> arr)
		{
			records = arr;
		}
	}	
	
	class Record
	{
		int score = 0;
		String name = "Empty";
	}
	
	ArrayList<Record> getRecords(int mode)
	{
		return performances.get(mode).records;		
	}
	
	void updateHighScoreFiles(int mode, ArrayList<Record> best)
	{
		String line;			
		File aFile = new File(directory + "/" + GlobalVars.highScoreFiles[mode]);
		try
		{
			PrintWriter pw = new PrintWriter(aFile);			
			for(int j = 0; j < 3; j++)
			{					
				line = String.valueOf(best.get(j).score);				
				pw.println(line);
				line =best.get(j).name;				
				pw.println(line);
			}
			pw.flush();
			pw.close();
		}catch(IOException e)
		{
			e.printStackTrace();
		}		
	}
	
	void initBestRecords()
	{
		ArrayList<Record> performance = null;
		Record record;		
		performances = new ArrayList<Records>(3);
		File dir;
		dir = new File(GlobalVars.SDCARD + GlobalVars.TORUSDIR);
		if(!dir.exists())
		{
			dir = new File(GlobalVars.DATADIR  + GlobalVars.TORUSDIR);
			if(!dir.exists())
			{
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {   
		    		directory = GlobalVars.SDCARD;
		        }else {   
		        	directory = GlobalVars.DATADIR;
		        }
				directory += GlobalVars.TORUSDIR;
				dir = new File(directory);
				dir.mkdir();
			}
			else
			{
				directory = GlobalVars.DATADIR + GlobalVars.TORUSDIR;
			}
		}
		else
		{
			directory = GlobalVars.SDCARD + GlobalVars.TORUSDIR;
		}		
		
		for(int m = 0; m < 3; m++)
		{			
			File aFile = new File(directory + "/" + GlobalVars.highScoreFiles[m]);			
			if(!aFile.exists())
			{				
				try{
					if(aFile.createNewFile())
					{
						//Log.d("Test", "score file create successfully.");
					}
					else
					{
						//Log.d("Test", "file create failure");
					}
				}catch(IOException e)
				{
					e.printStackTrace();
				}
				switch(m)
				{
				case 0:
				case 1:
					performance = new ArrayList<Record>(3);
					for(int i = 0; i < 3; i++)
					{	
						record = new Record();						
						performance.add(record);							
					}
					performances.add(new Records(performance));
					break;					
				case 2:
					performance = new ArrayList<Record>(3);
					for(int i = 0; i < 3; i++)
					{
						record = new Record();
						//record.score = 3599000;
						record.score = 3599;
						performance.add(record);															
					}						
					performances.add(new Records(performance));
					break;
				}
				updateHighScoreFiles(m, performance);
			}			
			else
			{
				//Log.d("Test", "score file exist");
				try{
					FileReader fr = new FileReader(aFile);
					BufferedReader br = new BufferedReader(fr);
					performance = new ArrayList<Record>();
					for(String line = br.readLine(); line != null;)
					{
						Record rec = new Record();
						rec.score = Integer.parseInt(line);
						rec.name = br.readLine();
						performance.add(rec);
						line = br.readLine();
					}
					if(performance.size() > 0)
					{
						performances.add(new Records(performance));
					}
					br.close();
					fr.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}		
	}
}
