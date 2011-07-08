package com.anglefish.game.torus3d;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;

import java.util.ArrayList;

public class Game {
	public float x = -2.0f;
	public int mode = 1;	 	
	public int score = 0;	
	public long time = 0;	
	public int type;	
	public float posY;	
	public ArrayList<Coord> block = null;	
	public ArrayList<BlockData> blockData = null;	    	
	public CubicMotion viewPort = new CubicMotion(250);		    
	private float innerRadius = 40.0f;
    private int totalFrequency = 0;     
    public float pieceSpawnTime = 0.0f;		  
    public int field[][][];		         
    public int lines = 0;		        
    private boolean displayGold = false;   
    public int pieceCount;	 
	public float[] dropPositions = new float[4];
	float[] ghostPositions = new float[4];	
	public int nextType = 0;	//下一方块的类型
	private Paint linePaint = new Paint();
	
    public Game()
    {
    	linePaint.setStrokeCap(Paint.Cap.ROUND);
		linePaint.setStrokeJoin(Paint.Join.ROUND);			
		linePaint.setStrokeWidth(0.7f);
		linePaint.setStyle(Style.STROKE);
		linePaint.setColor(0xe2111111);
		linePaint.setAntiAlias(true);
    	init();
    	dropPositions[3] = -138;	//特殊处理，防止刚进入游戏界面时，提示框一闪而逝
    }
    
    private void init()
	{
		blockData = new ArrayList<BlockData>();
		
		ArrayList<Coord> co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(2.0f, 0.0f));
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(2.0f, 1.0f));
		ArrayList<Coords> coords = new ArrayList<Coords>();
		coords.add(new Coords(co));		
		blockData.add(new BlockData(0.08f, 0, 1.0f, coords));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(0.0f, 0.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(2.0f, 0.0f));
		co.add(new Coord(3.0f, 0.0f));
		coords = new ArrayList<Coords>();
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, -1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(1.0f, 2.0f));
		coords.add(new Coords(co));
		blockData.add(new BlockData(0.25f, 1, 1, coords));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(0.0f, 0.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(2.0f, 0.0f));
		coords = new ArrayList<Coords>();
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(2.0f, 0.0f));
		co.add(new Coord(1.0f, -1.0f));
		co.add(new Coord(1.0f, 0.0f));
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(2.0f, 0.0f));
		co.add(new Coord(1.0f, -1.0f));
		co.add(new Coord(0.0f, 0.0f));
		co.add(new Coord(1.0f, 0.0f));
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(0.0f, 0.0f));
		co.add(new Coord(1.0f, -1.0f));
		co.add(new Coord(1.0f, 0.0f));
		coords.add(new Coords(co));
		blockData.add(new BlockData(0.5f, 2, 1.0f, coords));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(0.0f, 0.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(2.0f, 0.0f));
		co.add(new Coord(0.0f, 1.0f));
		coords = new ArrayList<Coords>();
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, -1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(2.0f, 1.0f));
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(0.0f, 0.0f));
		co.add(new Coord(2.0f, 0.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(2.0f, -1.0f));
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, -1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(0.0f, -1.0f));
		coords.add(new Coords(co));
		blockData.add(new BlockData(0.5f, 3, 1.0f, coords));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(2.0f, 0.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(0.0f, 0.0f));
		co.add(new Coord(2.0f, 1.0f));
		coords = new ArrayList<Coords>();
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, -1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(2.0f, -1.0f));
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(0.0f, 0.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(2.0f, 0.0f));
		co.add(new Coord(0.0f, -1.0f));
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, -1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(0.0f, 1.0f));
		coords.add(new Coords(co));
		blockData.add(new BlockData(0.5f, 4, 1.0f, coords));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(2.0f, 1.0f));
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(0.0f, 0.0f));
		coords = new ArrayList<Coords>();
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(0.0f, 2.0f));
		co.add(new Coord(0.0f, 1.0f));
		coords.add(new Coords(co));
		blockData.add(new BlockData(0.5f, 5, 1.0f, coords));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(0.0f, 1.0f));
		co.add(new Coord(2.0f, 0.0f));
		co.add(new Coord(1.0f, 0.0f));
		coords = new ArrayList<Coords>();
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(2.0f, 2.0f));
		co.add(new Coord(2.0f, 1.0f));
		coords.add(new Coords(co));
		blockData.add(new BlockData(0.5f, 6, 1.0f, coords));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(2.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(2.0f, 0.0f));
		co.add(new Coord(3.0f, 0.0f));
		coords = new ArrayList<Coords>();
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(2.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(2.0f, 0.0f));
		co.add(new Coord(1.0f, -1.0f));
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(2.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(2.0f, 0.0f));
		co.add(new Coord(0.0f, 1.0f));
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(2.0f, 2.0f));
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(2.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(2.0f, 0.0f));
		coords.add(new Coords(co));
		blockData.add(new BlockData(-0.15f, 0, 0.025f, coords));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(2.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(2.0f, 0.0f));
		co.add(new Coord(0.0f, 0.0f));
		coords = new ArrayList<Coords>();
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 2.0f));
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(2.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(2.0f, 0.0f));
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(3.0f, 1.0f));
		co.add(new Coord(2.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(2.0f, 0.0f));
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(2.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(2.0f, 0.0f));
		co.add(new Coord(2.0f, -1.0f));
		coords.add(new Coords(co));
		blockData.add(new BlockData(0.15f, 0, 0.025f, coords));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(-1.0f, 0.0f));
		co.add(new Coord(0.0f, 0.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(2.0f, 0.0f));
		co.add(new Coord(3.0f, 0.0f));
		coords = new ArrayList<Coords>();
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, -2.0f));
		co.add(new Coord(1.0f, -1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(1.0f, 2.0f));
		coords.add(new Coords(co));
		blockData.add(new BlockData(0.5f, 1, 0.025f, coords));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(0.0f, 0.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(2.0f, 0.0f));
		coords = new ArrayList<Coords>();
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(1.0f, -1.0f));
		coords.add(new Coords(co));
		blockData.add(new BlockData(0.5f, 1, 0.025f, coords));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 2.0f));
		co.add(new Coord(0.0f, 1.0f));
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(2.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		coords = new ArrayList<Coords>();
		coords.add(new Coords(co));
		blockData.add(new BlockData(0.5f, 2, 0.05f, coords));

		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(2.0f, 0.0f));
		coords = new ArrayList<Coords>();
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(2.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(2.0f, 1.0f));
		co.add(new Coord(2.0f, 0.0f));
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(2.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(2.0f, 0.0f));
		coords.add(new Coords(co));
		blockData.add(new BlockData(0.25f, 3, 0.05f, coords));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(2.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(2.0f, 0.0f));
		coords = new ArrayList<Coords>();
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(2.0f, 0.0f));
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(2.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(2.0f, 1.0f));
		co.add(new Coord(2.0f, 0.0f));
		coords.add(new Coords(co));
		blockData.add(new BlockData(0.25f, 4, 0.05f, coords));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(2.0f, 2.0f));
		co.add(new Coord(1.0f, 2.0f));
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(0.0f, 0.0f));
		coords = new ArrayList<Coords>();
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(0.0f, 2.0f));
		co.add(new Coord(0.0f, 1.0f));
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(2.0f, 1.0f));
		co.add(new Coord(2.0f, 0.0f));
		coords.add(new Coords(co));
		blockData.add(new BlockData(0.5f, 5, 0.025f, coords));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(0.0f, 2.0f));
		co.add(new Coord(1.0f, 2.0f));
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(2.0f, 0.0f));
		coords = new ArrayList<Coords>();
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(2.0f, 2.0f));
		co.add(new Coord(2.0f, 1.0f));
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(0.0f, 1.0f));
		co.add(new Coord(0.0f, 0.0f));
		coords.add(new Coords(co));
		blockData.add(new BlockData(0.5f, 6, 0.025f, coords));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(0.0f, 1.0f));
		co.add(new Coord(2.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(0.0f, 0.0f));
		co.add(new Coord(2.0f, 0.0f));
		coords = new ArrayList<Coords>();
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(2.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(1.0f, -1.0f));
		co.add(new Coord(2.0f, -1.0f));
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(0.0f, 0.0f));
		co.add(new Coord(2.0f, 0.0f));
		co.add(new Coord(0.0f, -1.0f));
		co.add(new Coord(2.0f, -1.0f));
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(0.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(1.0f, -1.0f));
		co.add(new Coord(0.0f, -1.0f));
		coords.add(new Coords(co));
		blockData.add(new BlockData(0.5f, 7, 0.025f, coords));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 2.0f));
		co.add(new Coord(0.0f, 2.0f));
		co.add(new Coord(2.0f, 2.0f));
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		coords = new ArrayList<Coords>();
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(2.0f, 2.0f));
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(2.0f, 1.0f));
		co.add(new Coord(0.0f, 1.0f));
		co.add(new Coord(2.0f, 0.0f));
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 2.0f));
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(0.0f, 0.0f));
		co.add(new Coord(2.0f, 0.0f));
		coords.add(new Coords(co));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(0.0f, 2.0f));
		co.add(new Coord(1.0f, 1.0f));
		co.add(new Coord(0.0f, 1.0f));
		co.add(new Coord(2.0f, 1.0f));
		co.add(new Coord(0.0f, 0.0f));
		coords.add(new Coords(co));
		blockData.add(new BlockData(0.5f, 7, 0.025f, coords));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 0.0f));
		coords = new ArrayList<Coords>();
		coords.add(new Coords(co));
		blockData.add(new BlockData(0.5f, 7, 0.05f, coords));
		
		co = new ArrayList<Coord>();
		co.add(new Coord(1.0f, 0.0f));
		co.add(new Coord(0.0f, 0.0f));
		co.add(new Coord(14.0f, 0.0f));
		co.add(new Coord(2.0f, 0.0f));
		co.add(new Coord(13.0f, 0.0f));
		co.add(new Coord(3.0f, 0.0f));
		co.add(new Coord(12.0f, 0.0f));
		co.add(new Coord(4.0f, 0.0f));
		co.add(new Coord(11.0f, 0.0f));
		co.add(new Coord(5.0f, 0.0f));
		co.add(new Coord(10.0f, 0.0f));
		co.add(new Coord(6.0f, 0.0f));
		co.add(new Coord(9.0f, 0.0f));
		co.add(new Coord(7.0f, 0.0f));
		co.add(new Coord(8.0f, 0.0f));
		coords = new ArrayList<Coords>();
		coords.add(new Coords(co));
		blockData.add(new BlockData(0.5f, 7, 0.01f, coords));
		
		for(int i = 0; i < blockData.size(); i++)
		{
			totalFrequency += blockData.get(i).frequency;
		}			
	}
    
    void clear()
    {
    	int i;
    	int j;
    	int r;
    	x = 0;
    	
    	field = new int[15][][];
    	for(i = 0; i < 15; i++)
    	{
    		field[i] = new int[15][];
    	}
    	
    	viewPort.reset();
    	lines = 0;
    	score = 0;            	
    	//初始化各个模式的方块     	
    	if(this.mode == 1)
    	{
    		int[][][] f = field;
    		f[10][0] = new int[2];
    		f[10][0][0] = 1;  		f[10][0][1] = 1;
    		f[10][1]= new int[2];
    		f[10][1][0] = 1;   	f[10][1][1] = 1;
    		f[11][0] = new int[2];
    		f[11][0][0] = 1;		f[11][0][1] = 1;
    		f[11][1] = new int[2];
    		f[11][1][0] = 1;		f[11][1][1] = 1;
    		for(int m = 0; m < 4; m++)
    		{
    			f[9][m] = new int[2];
    			f[9][m][0] = 2;
    			f[9][m][1] = 2;
    		}
    		f[8][0] = new int[2];
    		f[8][0][0] = 3;		f[8][0][1] = 3;
    		for(int m = 0; m < 2; m++)
    		{
    			f[7][m] = new int[2];
    			f[7][m][0] = 3;
    			f[7][m][1] = 3;
    		}
    		f[6][0] = new int[2];
    		f[6][0][0] = 3;		f[6][0][1] = 3;
    		f[12][0] = new int[2];
    		f[12][0][0] = 4;	f[12][0][1] = 4;
    		for(int m = 0; m < 3; m++)
    		{
    			f[13][m] = new int[2];
    			f[13][m][0] = 4;
    			f[13][m][1] = 4;
    		}
    		for(int n = 3; n < 6; n++)
    		{
    			f[n][0] = new int[2];
    			f[n][0][0] = 5;
    			f[n][0][1] = 5;
    		}
    		f[5][1] = new int[2];
    		f[5][1][0] = 5;		f[5][1][1] = 5;
    		f[1][0] = new int[2];
    		f[1][0][0] = 6;		f[1][0][1] = 6;
    		for(int n = 0; n < 2; n++)
    		{
    			f[2][n] = new int[2];
    			f[2][n][0] = 6;		f[2][n][1] = 6;
    		}
    		f[3][1] = new int[2];
    		f[3][1][0] = 6;		f[3][1][1] = 6;
    		for(int m = 2; m < 4; m++)
    		{
    			f[7][m] = new int[2];
    			f[7][m][0] = 7;
    			f[7][m][1] = 7;
    		}
    		for(int m = 1; m < 3; m++)
    		{
    			f[6][m] = new int[2];
    			f[6][m][0] = 7;
    			f[6][m][1] = 7;
    		}
    		pieceCount = 8;
    	}        	
    	if(this.mode == 2)
    	{
    		int[][][] f = field;
    		for(int n = 0; n < 13; n += 3)
    		{
    			f[n][0] = new int[2];
    			f[n][0][0] = (int) (Math.random() * 6 + 1);
    			f[n][0][1] = 0;
    		}
    		pieceCount = 1;
    	}        	
    	if(this.mode == 3)
    	{
    		for(i = 0; i < 9; i++)
    		{
    			for(j = 0; j < 5; j++)
    			{
    				r = (int) (Math.random() * 15);
    				if(field[r][i] != null)
    				{
    					j--;
    					continue;
    				}
    				else
    				{
    					field[r][i] = new int[2];
    					field[r][i][0] = 1;
    					field[r][i][1] = 0;
    				}
    			}
    		}
    		pieceCount = 1;
    	}
    }
    
    Coord iso(float x, float y, boolean b, float theta)
	{
		
		float r = b ? innerRadius : 60.0f;
		float v = 30.0f;
		return new Coord((float)(r * Math.cos((2.0f * Math.PI) * ((x -theta) / 15.0f - 1.0f / 4.0f)) * (y + v) / v),
				(float)(200.0f - y * 20.0f * (y / 2.0f + 60.0f) / 60.0f - 0.3 * r * Math.sin((2.0f * Math.PI) * ((x - theta) / 15.0f - 1.0f / 4.0f))));
	}
    
    public void drawCylinder(Canvas canvas, Paint paint, boolean includePiece, 
    		boolean hideTop, float progress, ArrayList<Integer> clearing)
	{
		paint.setAntiAlias(true);
		float theta = (viewPort.getPosition() % 15 + 15) % 15;			
		int xOff = (int)((theta) % 15  + 15) % 15;
		int x = 0;
		int y;
		int i;
		int j;
		float yPos = 0.0f;
		int xSlot = 0;
		//long now = System.currentTimeMillis();
		int block_type;
		int clearedBelow = 0;
		int maxBlock[] = new int[15];
		float intensity;
		int obj[];
		int[] DATA={7, 8, 6, 9, 5, 10, 4, 11, 3, 12, 2, 13, 1, 14, 0};
		for(i = 0; i < 15; i++)
		{
			x = DATA[i];
			clearedBelow = 0; 
			for(y = 0; y < 15; y++)
			{
				xSlot = (x + xOff) % 15;
				if(field[xSlot][y] != null)
				{
					maxBlock[xSlot] = y;
					intensity = (float) (0.6f + 0.4f * Math.cos(2 * Math.PI * (xSlot - theta) / 15));
					block_type = field[xSlot][y][0] - 1;
					int theme = blockData.get(block_type).theme;
					if(this.mode == 3 && y >= 3)
					{
						theme = 20;
					}
					if(this.displayGold)
					{
						theme = 30;
					}					
					
					if(progress != 0 && clearing.contains(y))
					{
						clearedBelow++;
						paint.setAlpha((int) (Math.max(0, Math.min(1, intensity * (1 - progress * 1.5))) * 255));
						linePaint.setAlpha((int) (Math.max(0, Math.min(1, intensity * (1 - progress * 1.5))) * 255));
						yPos = y;
					}
					else
					{
						if(this.mode == 3)
						{								
							paint.setAlpha((int) (Math.max((intensity - 0.3) / 0.7, 0) * 255));			
							linePaint.setAlpha((int) (Math.max((intensity - 0.3) / 0.7, 0) * 255));	
						}
						else
						{
							paint.setAlpha(226);
							linePaint.setAlpha(226);
						}
						progress = progress < 0 ? 0 : progress;
						yPos = (float) (y - clearedBelow * Math.pow(Math.max(0, progress), 5));
					}
					/*intensity *= 1 + Math.sin(xSlot * 77777 + now / 871) * 
									Math.sin(xSlot * 31247 + now / 1713) * 
									Math.sin(y * 41996 + now / 1713) *
									Math.sin(y * 85555 + now / 797) / 3;*/
					intensity = 0.8f;
					intensity = intensity < 0 ? 0: intensity > 1 ? 1: intensity;
					int alpha = paint.getAlpha();
					switch(theme)
					{
					case 0:
						paint.setStyle(Style.FILL);							
						paint.setARGB(alpha, (int)(intensity * 255), (int)(intensity * 0), (int)(intensity * 0));
						break;
					case 1:
						paint.setStyle(Style.FILL);							
						paint.setARGB(alpha, (int)(intensity * 0), (int)(intensity * 255), (int)(intensity * 0));
						break;
					case 2:
						paint.setStyle(Style.FILL);							
						paint.setARGB(alpha, (int)(intensity * 0), (int)(intensity * 0), (int)(intensity * 255));
						break;
					case 3:
						paint.setStyle(Style.FILL);							
						paint.setARGB(alpha, (int)(intensity * 255), (int)(intensity * 255), (int)(intensity * 0));
						break;
					case 4:
						paint.setStyle(Style.FILL);							
						paint.setARGB(alpha, (int)(intensity * 255), (int)(intensity * 0), (int)(intensity * 255));
						break;
					case 5:
						paint.setStyle(Style.FILL);							
						paint.setARGB(alpha, (int)(intensity * 0), (int)(intensity * 255), (int)(intensity * 255));
						break;
					case 6:
						paint.setStyle(Style.FILL);							
						paint.setARGB(alpha, (int)(intensity * 220), (int)(intensity * 220), (int)(intensity * 220));
						break;
					case 20:
						paint.setStyle(Style.FILL);							
						paint.setARGB(alpha, (int)(intensity * 100), (int)(intensity * 100), (int)(intensity * 100));
						break;
					case 30:
						paint.setStyle(Style.FILL);							
						paint.setARGB(alpha, (int)(intensity * 190), (int)(intensity * 190), (int)(intensity * 0));
						break;
					default:
						paint.setStyle(Style.FILL);
						paint.setARGB(alpha, (int)(intensity * 220), (int)(intensity * 220), (int)(intensity * 220));
						break;
					}
					float angle = ((xSlot - theta) % 15 + 15) % 15;				
					drawFront(canvas, paint, xSlot, yPos, angle > 3.3 && angle < 10.7, theta, 
							(angle > 11.9 || angle < 10.9) && ((obj = field[(xSlot + 14) % 15][y]) != null && obj[1] == field[xSlot][y][1]),
							(angle < 2.1 || angle > 3.1) && ((obj = field[(xSlot + 1) % 15][y]) != null && obj[1] == field[xSlot][y][1]),
							((obj = y >= 14 ? null :field[xSlot][y + 1]) != null && obj[1] == field[xSlot][y][1]),
									((obj = (y -1 < 0 ? null :field[xSlot][y - 1])) != null && obj[1] == field[xSlot][y][1]));
					if(clearing != null)
					{
						obj = new int[clearing.size()];
						for(int k = 0; k < clearing.size(); k++)
						{
							obj[k] = ((Integer)clearing.get(k)).intValue();
						}
					}
					else
					{
						obj = null;
					}					
					if(y >= 14 || field[xSlot][y + 1] == null || (/*(obj = clearing)*/ obj != null && clearing.contains(y + 1)))
					{						
						drawTop(canvas, paint, xSlot, yPos, theta,
							((obj != null || (y < 14 && field[(xSlot + 14) % 15][y + 1] == null)) && (obj = field[(xSlot + 14) % 15][y]) != null && obj[1] == field[xSlot][y][1]),
							((obj != null || (y < 14 && field[(xSlot + 1) % 15][y + 1] == null)) && (obj = field[(xSlot + 1) % 15][y]) != null && obj[1] == field[xSlot][y][1]), false);								
					}						
					
					if(angle > 6.5 && angle < 14)
					{
						if(field[((x + 1 +xOff) % 15 + 15) % 15][y] == null)
						{
							drawSide(canvas, paint, xSlot + 1, yPos, theta, 
									((obj = y >= 14 ? null : field[xSlot][y + 1]) != null && obj[1] == field[xSlot][y][1]),
									((obj = (y - 1 < 0 ? null :field[xSlot][y - 1])) != null && obj[1] == field[xSlot][y][1]));
						}
					}
					if(angle < 7.5)
					{
						if(field[((x - 1 + xOff) % 15 + 15) % 15][y] == null)
						{
							drawSide(canvas, paint, xSlot, yPos, theta,
									((obj = y >= 14 ? null : field[xSlot][y + 1]) != null && 
											obj[1] == field[xSlot][y][1]),
									((obj = (y - 1 < 0 ? null :field[xSlot][y - 1])) != null && 
											obj[1] == field[xSlot][y][1]));								
						}
					}
				}
			}
		}		
		for(i =0; i < 15 && !hideTop; i++)			
		{
			intensity = maxBlock[i];
			for(j = 1; j < 5; j++)
			{
				intensity = Math.max(intensity, maxBlock[(i + j) % 15] -j);
				intensity = Math.max(intensity, maxBlock[(i - j + 15) % 15] - j);					
			}
			if(intensity > 9)
			{					
				paint.setStyle(Style.FILL);
				paint.setColor(Color.rgb(((int)(255 * (Math.sin(System.currentTimeMillis() 
						/ 200) / 2 + 1 / 2))), 30, 0));
				paint.setAlpha((int)(intensity - 9) * 51);
				drawTop(canvas, paint, (float)i, 14.0f, (float)theta, false, false, true);	
				//drawTop(canvas, paint, (float)i, 14.0f, (float)theta, false, false, true);	//480 * 854
				//drawTop(canvas, paint, (float)i, 12.0f, (float)theta, false, false, true);
			}
		}
		if(includePiece)
		{									
			drawPiece(canvas, paint);
		}
	}
    
    void drawPiece(Canvas canvas, Paint paint)
	{
		paint.setAntiAlias(true);
		float theta = (viewPort.getPosition() % 15 + 15) % 15;
		float thetaTarget = (viewPort.mTarget % 15 + 15) % 15;		
		//boolean ghost = true;
		float slotY = (float) Math.floor(posY);
		//float intensity = (float) (Math.sin(System.currentTimeMillis() / 150) * 0.05 + 0.95);
		float intensity = 0.8f;
		paint.setStyle(Style.FILL);
		paint.setColor(getBlockColor(type, intensity, 140));
		boolean joinTop;
		boolean joinBottom;
		boolean joinLeft;
		boolean joinRight;
		/*float opacityMultiplier = this.time > pieceSpawnTime ? 
				Math.min(1, (this.time - pieceSpawnTime) / 250) : 1;	*/   //源码
		float opacityMultiplier = 1;
		ArrayList<Coords> coords = new ArrayList<Coords>();
		Coords cos;
		//if(ghost)
		if(GlobalVars.GHOST)
		{
			//piece = GHOST;
			/*if(ghostPiece == null)
			{
				ghostPiece = new ArrayList<Coords>();
			}
			else
			{
				ghostPiece.clear();
			}*/
			
			int shadowDrop = solveShadow();
			if(innerRadius == 0)
			{
				innerRadius = 41.0f;
			}
			paint.setAlpha((int) (0.4 * opacityMultiplier * 255));				
			int j;
			for(int i = 0; i < block.size(); i++)
			{
				joinBottom = joinRight = false;
				for( j = block.size() - 1; j >= 0; j--)
				{
					if(j != i)
					{
						if(block.get(j).x == block.get(i).x && 
								block.get(j).y == block.get(i).y - 1)
						{
							joinBottom = true;
						}
						else if(block.get(j).y == block.get(i).y && 
								block.get(j).x == block.get(i).x + 1)
						{
							joinRight = true;
						}
					}
				}				
				cos = new Coords(drawFront(canvas, paint, x + block.get(i).x, shadowDrop + block.get(i).y, false, 
						theta, false, false, false, false));
				coords.add(cos);
				drawTop(canvas, paint, x + block.get(i).x, shadowDrop + block.get(i).y, theta,
						false, false, false);
				drawTop(canvas, paint, x + block.get(i).x, shadowDrop + block.get(i).y, theta,
						false, false, false);
				drawSide(canvas, paint, x + block.get(i).x , shadowDrop + block.get(i).y, theta,
						false, false);
				
				if(!joinRight)
				{						
					drawSide(canvas, paint, x + block.get(i).x + 1, shadowDrop + block.get(i).y, theta,
							false, false);
				}
				if(!joinBottom)
				{						
					drawTop(canvas, paint, x + block.get(i).x, shadowDrop + block.get(i).y - 1, theta, 
							false, false, false);
				}				
			}
			if(innerRadius == 41.0f)
			{
				innerRadius = 0;
			}
			positions(coords, ghostPositions);
		}			
		paint.setStyle(Style.FILL);
		paint.setColor(getBlockColor(type, intensity, 0));
		paint.setAlpha((int)(0.9 * opacityMultiplier * 255));
		
		//piece = DROP;
		coords.clear();
		/*if(dropPiece == null)
		{
			dropPiece = new ArrayList<Coords>();
		}
		else
		{
			dropPiece.clear();
		}*/
		
		int j;
		for(int i = block.size() - 1; i >= 0; i--)
		{				
			joinTop = joinBottom = joinLeft = joinRight = false;
			for(j = block.size() - 1; j >= 0; j--)
			{
				if(j != i)
				{
					if(block.get(j).x == block.get(i).x)
					{
						if(block.get(j).y == block.get(i).y + 1)
						{
							joinTop = true;
						}
						else if(block.get(j).y == block.get(i).y - 1)
						{
							joinBottom = true;
						}
					}
					else if(block.get(j).y == block.get(i).y)
					{
						if(block.get(j).x == (block.get(i).x + 14) % 15)
						{
							joinLeft = true;
						}
						else if(block.get(j).x == (block.get(i).x + 1) % 15)
						{
							joinRight = true;
						}
					}
				}
			}
			cos = new Coords(drawFront(canvas, paint, x + block.get(i).x, posY + block.get(i).y, false, thetaTarget, 
					joinLeft, joinRight, joinTop, joinBottom));				
			coords.add(cos);
			int index = (int) (posY + block.get(i).y);				
			if(block.get(i).x + blockData.get(type).view < 1 && (index < 0 || index >= 15 ||
					field[(int) (((x + block.get(i).x + 1) % 15 + 15) % 15)][index] == null))
			{					
				for(j = block.size() - 1; j >= 0; j--)
				{
					if(block.get(j).x == 1 && block.get(j).y == block.get(i).y)
					{							
						break;
					}
				}					
				if(j < 0)
				{									
					drawSide(canvas, paint, x + block.get(i).x + 1, posY + block.get(i).y, thetaTarget, 
							joinTop, joinBottom);
				}
			}
			index = (int)(slotY + block.get(i).y);
			if(block.get(i).x + blockData.get(type).view > 2 && (index < 0 || index >= 15 ||
					field[(int) (((x + block.get(i).x - 1) % 15 + 15) % 15)][index] == null))
			{										
				for(j = block.size() - 1; j >= 0; j--)
				{
					if(block.get(j).x == (block.get(i).x - 1) && block.get(j).y == block.get(i).y)
					{							
						break;
					}
				}							
				if(j < 0)
				{									
					drawSide(canvas, paint, x + block.get(i).x, posY + block.get(i).y, thetaTarget, 
							joinTop, joinBottom);
				}
			}
			//if(1 != 0 || field[(int) (((x + block.get(i).x) % 15 + 15) % 15)][(int) (slotY + block.get(i).y + 1)] == null)
			{
				for(j = block.size() - 1; j >= 0; j--)
				{
					if(block.get(j).x == block.get(i).x &&
							block.get(j).y - 1 == block.get(i).y)
					{
						break;
					}
				}				
				if(j < 0)
				{
					drawTop(canvas, paint, x + block.get(i).x, posY + block.get(i).y, thetaTarget, 
							joinLeft, joinRight, false);
				}
			}				
		}			
		positions(coords, dropPositions);		
		//piece = 0;
	}
    
    void positions(ArrayList<Coords> coordsArr, float[] pos)
    {     	
    	int sz = coordsArr.size();
    	pos[0] = coordsArr.get(0).block.get(0).x;
    	pos[1] = pos[0];
    	pos[2] = coordsArr.get(0).block.get(0).y;
    	pos[3] = pos[2];
    	for(int i = 0; i < sz; i++)
    	{
    		ArrayList<Coord> cos = coordsArr.get(i).block;
    		int len = cos.size();
    		for(int j = 0; j < len; j++)
    		{
    			Coord co = cos.get(j);
    			if(pos[0] > co.x){
    				pos[0] = co.x;
    			}
    			if(pos[1] < co.x){
    				pos[1] = co.x;
    			}
    			if(pos[2] > co.y){
    				pos[2] = co.y;
    			}
    			if(pos[3] < co.y){
    				pos[3] = co.y;
    			}
    		}
    	}
    	
    	float w = pos[1] - pos[0];
    	float h = pos[3] - pos[2];
    	if(w < h)
    	{
    		float mid = (pos[0] + pos[1]) * 0.5f;
    		pos[0] = mid - 0.5f * h;
    		pos[1] = mid + 0.5f * h;
    	}
    	else if(w > h)
    	{
    		float mid = (pos[2] + pos[3]) * 0.5f;
    		pos[2] = mid - w * 0.5f;
    		pos[3] = mid + w * 0.5f;
    	}    	
    }
    
    int getBlockColor(int type, float intensity, float lightness)
	{
		if(type < 20)
		{
			type = blockData.get(type).theme;
		}
		float L = lightness <= 0 ? 0 : lightness;
		switch(type)
		{
		case 0:
			return Color.rgb((int)(intensity * 255), (int)(intensity * L), (int)(intensity * L));
		case 1:
			return Color.rgb((int)(intensity * L), (int)(intensity * 255), (int)(intensity * L));
		case 2:
			return Color.rgb((int)(intensity * L), (int)(intensity * L), (int)(intensity * 255));
		case 3:
			return Color.rgb((int)(intensity * 255), (int)(intensity * 255), (int)(intensity * L));
		case 4:
			return Color.rgb((int)(intensity * 255), (int)(intensity * L), (int)(intensity * 255));
		case 5:
			return Color.rgb((int)(intensity * L), (int)(intensity * 255), (int)(intensity * 255));
		case 6:
			return Color.rgb((int)(intensity * 220), (int)(intensity * 220), (int)(intensity * 220));
		case 20:
			return Color.rgb((int)(intensity * 100), (int)(intensity * 100), (int)(intensity * 100));
		case 30:
			return Color.rgb((int)(intensity * 190), (int)(intensity * 190), 0);
		default:
			return Color.rgb((int)(intensity * 220), (int)(intensity * 220), (int)(intensity * 220));
		}		
	}
    
    ArrayList<Coord> drawFront(Canvas canvas, Paint paint, float x, float y, boolean r, float theta,
    		boolean joinLeft, boolean joinRight, boolean joinTop, boolean joinBottom)
	{
		paint.setAntiAlias(true);
		Path path = new Path();
		ArrayList<Coord> co = new ArrayList<Coord>();
		co.add(iso((float)(x - 0.015), (float)(y + 0.015), r, theta));			
		co.add(iso((float)(x + 1.015), (float)(y + 0.015), r, theta));
		co.add(iso((float)(x + 1.015), (float)(y - 1.015), r, theta));
		co.add(iso((float)(x - 0.015), (float)(y - 1.015), r, theta));
		
		/*if(piece == GHOST || piece == DROP)
		{
			ArrayList<Coord> area = new ArrayList<Coord>();
			area.add(co.get(0));
			area.add(co.get(2));
			if(piece == GHOST)
			{
				ghostPiece.add(new Coords(area));
			}
			else
			{
				dropPiece.add(new Coords(area));
			}
		}*/
		
		path.moveTo(co.get(0).x, co.get(0).y);
		path.lineTo(co.get(1).x, co.get(1).y);
		path.lineTo(co.get(2).x, co.get(2).y);
		path.lineTo(co.get(3).x, co.get(3).y);
		path.close();		
		
		paint.setStyle(Style.FILL);			
		canvas.drawPath(path, paint);		
		if(joinTop || joinBottom || joinLeft || joinRight)
		{
			path.reset();
			if(joinTop)
			{
				path.moveTo(co.get(1).x,co.get(1).y);					
			}
			else
			{
				path.moveTo(co.get(0).x, co.get(0).y);
				path.lineTo(co.get(1).x, co.get(1).y);
			}
			if(joinRight)
			{
				path.moveTo(co.get(2).x, co.get(2).y);
			}
			else
			{
				path.lineTo(co.get(2).x, co.get(2).y);
			}
			if(joinBottom)
			{
				path.moveTo(co.get(3).x, co.get(3).y);
			}
			else
			{
				path.lineTo(co.get(3).x, co.get(3).y);
			}
			if(!joinLeft)
			{
				path.lineTo(co.get(0).x, co.get(0).y);
			}
		}	
		canvas.drawPath(path, linePaint);
		return co;
	}
    
    void drawTop(Canvas canvas, Paint paint, float x, float y, float theta, boolean joinLeft,
			boolean joinRight, boolean noLines)
	{
		paint.setAntiAlias(true);
		ArrayList<Coord> co = new ArrayList<Coord>();
		co.add(iso((float)(x - 0.015), y, false, theta));
		co.add(iso((float)(x - 0.015), y, true, theta));
		co.add(iso((float)(x + 1.015), y, true, theta));
		co.add(iso((float)(x + 1.015), y, false, theta));			
		
		Path path = new Path();
		path.moveTo(co.get(0).x, co.get(0).y);
		path.lineTo(co.get(1).x, co.get(1).y);
		path.lineTo(co.get(2).x, co.get(2).y);
		path.lineTo(co.get(3).x, co.get(3).y);
		path.close();
		paint.setStyle(Style.FILL);			
		canvas.drawPath(path, paint);
		
		if(noLines)
		{
			return;
		}
		if(joinLeft || joinRight)
		{
			path.reset();
			if(joinLeft)
			{
				path.moveTo(co.get(1).x, co.get(1).y);					
			}
			else
			{
				path.moveTo(co.get(0).x, co.get(0).y);
				path.lineTo(co.get(1).x, co.get(1).y);					
			}
			path.lineTo(co.get(2).x, co.get(2).y);
			if(joinRight)
			{
				path.moveTo(co.get(3).x, co.get(3).y);
			}
			else
			{
				path.lineTo(co.get(3).x, co.get(3).y);
			}
			path.lineTo(co.get(0).x, co.get(0).y);
		}		
		canvas.drawPath(path, linePaint);
	}
    
    void drawSide(Canvas canvas, Paint paint, float x, float y, float theta, 
    		boolean joinTop, boolean joinBottom)
	{
		paint.setAntiAlias(true);
		ArrayList<Coord> co = new ArrayList<Coord>();
		co.add(iso(x, (float)(y - 1.015), false, theta));
		co.add(iso(x, (float)(y + 0.015), false, theta));
		co.add(iso(x, (float)(y + 0.015), true, theta));
		co.add(iso(x, (float)(y - 1.015), true, theta));			
		
		Path path = new Path();
		path.moveTo(co.get(0).x, co.get(0).y);
		path.lineTo(co.get(1).x, co.get(1).y);
		path.lineTo(co.get(2).x, co.get(2).y);
		path.lineTo(co.get(3).x, co.get(3).y);
		path.close();			
		paint.setStyle(Style.FILL);
		canvas.drawPath(path, paint);
		
		if(joinTop || joinBottom)
		{
			path.reset();
			path.moveTo(co.get(0).x, co.get(0).y);
			path.lineTo(co.get(1).x, co.get(1).y);
			if(joinTop)
			{
				path.moveTo(co.get(2).x, co.get(2).y);
			}
			else
			{
				path.lineTo(co.get(2).x, co.get(2).y);
			}
			path.lineTo(co.get(3).x, co.get(3).y);
			if(!joinBottom)
			{
				path.lineTo(co.get(0).x, co.get(0).y);
			}
		}		
		canvas.drawPath(path, linePaint);
	}
    
    public void setMode(int mode)
	{
		this.mode = mode;
		this.clear();
	}
    
    public int getRandomPieceType()
	{
		float r = (float)Math.random() * totalFrequency;
		float sum = 0.0f;
		int i = 0;
		for(; i < blockData.size(); i++)
		{
			sum += blockData.get(i).frequency;
			if(r < sum)
			{
				return i;
			}
		}
		return i - 1;
	}  
    
    int solveShadow()
	{
		float slotY = (float) Math.floor(posY);
		int i;
		int j;
		if(block.size() == 1)
		{
			//notice 源码没有 i < 15
			for(i = 0; i < 15; i++)
			{
				if(field[(int) ((((x + block.get(0).x) % 15) + 15) % 15)][i] == null)
				{
					return i;
				}
			}
		}
		for(j = 0; ; j++)
		{
			for(i = block.size() - 1; i >= 0; i--)
			{
				if(slotY + block.get(i).y - j < 0 ||(slotY + block.get(i).y - j < 15 &&
						field[(int) (((x + block.get(i).x) % 15 + 15) % 15)][(int) (slotY + block.get(i).y - j)] != null))
				{
					return (int) (slotY - j + 1);
				}
			}
		}			
	} 
    
    public class Coords
    {
    	public ArrayList<Coord> block;
    	Coords(ArrayList<Coord> b)
    	{
    		block = b;
    	}
    }
    
    public class Coord 
    {
    	public float x; 
    	public float y;
    	Coord(float aX, float aY)
    	{
    		x = aX;
    		y = aY;
    	}
    }
    
    public class BlockData	{
		public float view;
		public int theme;
		float frequency;
		public ArrayList<Coords> coords;
		BlockData(float v, int t, float f, ArrayList<Coords> c)
		{
			view = v;
			theme = t;
			frequency = f;
			coords = c;
		}
	}
}