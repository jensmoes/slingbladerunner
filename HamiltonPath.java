
import java.awt.List;
import java.io.*;
import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;

import javax.management.timer.TimerMBean;
public class HamiltonPath {

	static int level = 0;
	static int max_level = 0;
	static final int CUTOFF_TARGET = 1300;//Stop if you reach this, make it high when using timer based search
	static final int CANDIDATE_TARGET = 270;//Save these for postprocessing, 240 is OK with 250ms
	static final int MAX_TIME =15*1000;//ms to recurse in search for candidates. 250 is pretty good(About 30 mins). 2 min is decent for vetting candidates
	static int TARGET = CUTOFF_TARGET;
	static boolean interrupted = false;
	static String FILE_PATH = "/Users/jens/Documents/Code/JAVA/Exercises/src/movies.txt";
    static long SEED = System.nanoTime();

	private static ArrayList<String> titles;
    private boolean[][] matrix;
	
	public HamiltonPath(boolean[][] aMatrix) {
		matrix = aMatrix;
        max_level=0;
        level=0;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileInputStream fr;
		titles = new ArrayList<String>();
		try {
			fr = new FileInputStream(FILE_PATH);
			BufferedReader br = new BufferedReader(new InputStreamReader(fr));
			String line;
			while((line = br.readLine() )!= null)
			{
				titles.add(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.printf("No file found at %s. Did you enter a correct FILE_PATH?",FILE_PATH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		boolean[][] matrix = makeMatrix(titles);


		HamiltonPath path = new HamiltonPath(matrix);

		path.traverseGraph(matrix);
		//This one should not matter since all nodes get touched anyhow, but it will do the stuff in a different order, so outcome on partial runs will be affected by high MAX_TIME
//		path.traverseGraphRandomly(matrix);
		//Define candidates statically from previous run
		int[][] candidates = new int[][]{
				{8,2958},{22,3950},{22,3954},{45,1864},{62,3257},{116,2958},{145,3257},{147,2034},{155,3363},{155,3366},{165,6494},{201,2744},{209,2958},{212,2958},{214,2958},{216,1864},{226,2744},{285,1126},{310,3346},{310,3399},{350,1126},{368,6582},{382,1220},{382,1221},{392,2395},{427,771},{503,1864},{524,1864},{537,3500},{542,1164},{553,1864},{596,2958},{643,3257},{745,1397},{773,6494},{784,2687},{786,1864},{787,1164},{810,2296},{810,2297},{830,6060},{832,5109},{835,3346},{835,3399},{838,3257},{838,5474},{858,4298},{876,3003},{876,3004},{901,1864},{920,3363},{920,3366},{930,1864},{1007,2958},{1013,3257},{1013,5474},{1070,5667},{1070,5668},{1082,3257},{1084,2395},{1164,1126},{1167,6582},{1180,1864},{1209,4215},{1220,3257},{1242,3257},{1317,6494},{1323,2958},{1326,4215},{1328,1221},{1334,1864},{1345,6060},{1374,1556},{1383,5109},{1391,2237},{1397,4389},{1413,2395},{1451,2296},{1451,2297},{1453,6569},{1484,1496},{1496,382},{1533,4619},{1558,4412},{1596,1734},{1619,1220},{1619,1221},{1654,3289},{1672,4215},{1676,3515},{1678,1619},{1679,4215},{1700,2296},{1712,6569},{1721,1126},{1749,1762},{1749,1764},{1749,1765},{1752,2422},{1762,3235},{1777,3257},{1804,2602},{1831,2395},{1860,1864},{1865,1864},{1868,1167},{1902,482},{1942,1220},{1942,1221},{1943,2958},{1944,2958},{1960,3950},{1960,3954},{1960,5339},{1960,5340},{1967,2687},{2034,3346},{2034,3399},{2068,3257},{2094,4412},{2098,3257},{2128,1762},{2128,1764},{2128,1765},{2129,2237},{2143,4412},{2210,876},{2297,1126},{2299,1220},{2299,1221},{2330,2958},{2332,3257},{2332,5474},{2348,3257},{2348,5474},{2381,3257},{2395,6228},{2405,6228},{2422,1762},{2422,1764},{2422,1765},{2452,368},{2456,1902},{2473,3758},{2476,3257},{2523,2034},{2574,1864},{2602,4619},{2619,3257},{2656,6582},{2662,3758},{2663,4095},{2671,2602},{2680,2395},{2687,1220},{2687,1221},{2741,4215},{2744,1220},{2744,1221},{2764,2395},{2814,2296},{2814,2297},{2864,5109},{2886,2687},{2900,2237},{2903,2958},{2904,1864},{2912,887},{2918,1864},{2947,2237},{2958,1960},{2983,6569},{3003,3004},{3065,3257},{3066,1220},{3066,1221},{3075,3076},{3076,6159},{3088,3289},{3090,3257},{3146,2602},{3180,2958},{3200,2534},{3206,2534},{3212,6060},{3235,1762},{3235,1764},{3235,1765},{3244,1220},{3244,1221},{3289,1126},{3312,2602},{3316,1388},{3333,2958},{3346,1220},{3346,1221},{3363,3950},{3363,3954},{3366,1220},{3366,1221},{3379,3257},{3379,5474},{3399,6159},{3419,3422},{3419,6159},{3422,6159},{3515,382},{3557,3559},{3559,6169},{3573,1220},{3573,1221},{3613,3614},{3614,5227},{3634,1126},{3640,72},{3640,120},{3640,285},{3690,2958},{3709,1864},{3758,1126},{3764,4412},{3800,2912},{3803,4215},{3841,6494},{3847,2395},{3865,2958},{3937,1220},{3937,1221},{3987,4412},{3992,2395},{4025,3257},{4036,2958},{4060,1759},{4064,1676},{4084,4095},{4095,1220},{4095,1221},{4113,1864},{4126,6494},{4198,2296},{4198,2297},{4249,2395},{4253,3257},{4253,5474},{4272,5109},{4298,6159},{4332,6582},{4365,2395},{4375,4412},{4385,4909},{4389,3557},{4389,3559},{4408,1220},{4408,1221},{4411,3257},{4412,6159},{4427,1220},{4427,1221},{4431,1220},{4431,1221},{4471,1864},{4519,4552},{4520,4522},{4522,6169},{4552,1126},{4578,1126},{4588,3366},{4590,3363},{4590,3366},{4595,6159},{4663,876},{4788,1970},{4848,4389},{4884,4215},{4889,1556},{4904,1126},{4909,2534},{5068,1864},{5084,1126},{5096,2534},{5189,2237},{5198,2237},{5213,1864},{5301,1220},{5301,1221},{5304,1762},{5304,1764},{5304,1765},{5314,5679},{5339,3950},{5339,3954},{5339,5340},{5340,3758},{5373,6507},{5394,2958},{5411,3003},{5411,3004},{5428,2958},{5455,3206},{5474,1220},{5474,1221},{5502,3363},{5502,3366},{5577,2602},{5581,3363},{5581,3366},{5582,1864},{5654,1220},{5654,1221},{5656,2534},{5663,5667},{5663,5668},{5668,3950},{5668,3954},{5668,5339},{5668,5340},{5671,5864},{5677,5679},{5679,6228},{5682,6494},{5702,1126},{5736,1864},{5754,6060},{5806,4215},{5810,6060},{5849,6159},{5859,4408},{5860,4412},{5862,5864},{5895,4215},{5917,1864},{5996,6494},{6000,3363},{6000,3366},{6038,3257},{6043,2958},{6045,3257},{6115,2958},{6117,2958},{6121,1721},{6159,1126},{6169,1220},{6169,1221},{6190,1220},{6190,1221},{6214,3257},{6226,6159},{6279,5667},{6279,5668},{6294,1864},{6330,6060},{6399,1676},{6417,2395},{6424,2395},{6450,3289},{6494,1126},{6498,6060},{6507,1220},{6507,1221},{6514,4215},{6519,2237},{6544,2958},{6569,1960},{6582,1220},{6582,1221}
		
		};
		int size = (candidates).length;
		ArrayList<Node> c = new ArrayList<HamiltonPath.Node>();
		for(int i = 0;i< size;i++)
		{
			c.add(path.new Node(candidates[i][0], candidates[i][1]));
		}
		int[][] bestCandidates5second = new int[][]{
				{259,1693,792},//got 270 on random short run, 4 hours run on the other hand yielded only 237
                {1,1732,3389},
				{258,5314,5677},
				{256,3841,6498},
				{255,6531,786},
				{255,764,715},
				{255,498,788},
				{254,3992,2395},
				{253,1285,2677},
				{253,1816,4227},
				{253,5179,761},
				{252,2197,6307},
				{252,647,6190},
				{251,643,3245},
				{251,5888,199},
				{251,4079,1573},
				{250,1410,6558},
				{250,4131,5875},
		};
		ArrayList<Node> bests = new ArrayList<HamiltonPath.Node>();
		for(int[] bc : bestCandidates5second)
		{
			bests.add(path.new Node(bc[1],bc[2]));
		}

		ArrayList<Path> results=null;
		//Traverse the candidates found earlier
//		while(results == null || results.isEmpty())//keep going (using random selection)
//		{
//			results = path.traverse(bests);
//            SEED = System.nanoTime();
//        }
//		path.traverse(c);

		System.out.println("Wow we reached the end!\n");
		System.out.printf("Deepest path = %d\n",HamiltonPath.max_level);

		
	}


	private ArrayList<Path> traverse(ArrayList<Node> nodes)
	{
		int max = 0;
		ArrayList<Path> bestNodes = new ArrayList<HamiltonPath.Path>();
		int total = nodes.size();
        System.out.printf("Traversing %d nodes\n", total);
        System.out.printf("MAX_TIME=%d CANDIDATE_TARGET=%d\n", MAX_TIME,CANDIDATE_TARGET);

        System.out.printf("Seed = %d\n",SEED);

        for(Node nd: nodes)
		{
//			max_level = 0;//Reset max counter for each node

//			printTimeStamp();

//			System.out.printf("Starting node %d,  %d (of %d)\n",nd.i,nd.j,nodes.size());
			int div = total/100;
			if(div>0 && nodes.indexOf(nd)%div == 0)
			{
				System.out.printf("(Processed %d/%d)\n",nodes.indexOf(nd),total);
			}
			Timer t = new Timer();
			t.schedule(new InterruptTask(), MAX_TIME);
			Element lp = findPath(nd.i, nd.j);
			t.cancel();
			t.purge();
			if(interrupted == true)
			{
				TARGET = CUTOFF_TARGET;
				interrupted = false;
//				System.out.printf("Interrupted (%d,%d) at %d deep\n",nd.i,nd.j,lp==null?-1:lp.length);
//				printTimeStamp();
			}
			if(lp != null)
			{
				lp.path.add(0, nd.i);
				lp.length++;
				if(lp.length > max)
				{
					max = lp.length;
				}
				if(lp.length >= CANDIDATE_TARGET)
				{
					bestNodes.add(new Path(nd, lp));
					printPath(lp.path);					
					System.out.printf("FOUND CANDIDATE %d,  total %d\n",lp.length,bestNodes.size());
				}
//				else//Try it again, use this for low cutoff times experiment
//				{
//					nodes.add(0, nd);
//				}
			}
			
			level = 0;
		}
		if(bestNodes.isEmpty() == false){
			//Print the longest found (last)
			int longest = 0;
			Path longestPath=null;
			for(Path b : bestNodes)
			{
				if(b.path.length > longest)
					longestPath = b;
			}
			System.out.printf("Longest path is %d\n",longest);
			this.printPath(longestPath.path.path);
			printPaths(bestNodes);
		}
		return bestNodes;
	}

    //Traverses the entire graph
    private void traverseGraph(boolean[][] matrix)
    {
        ArrayList<Node> nodes = findAllNodes();
        traverse(nodes);
    }

    private void traverseGraphRandomly()//If you want to try out random areas of the graph
	{
		ArrayList<Node> nodes = findAllNodes();
		//randomize array
		long seed = System.nanoTime();
		Collections.shuffle(nodes,new Random(seed));
		traverse(nodes);
		System.out.println("Seed used:f");
		System.out.print(seed);
		
	}

	//Recursive method for finding a path
	private Element findPath(int i, int j)
	{
		//if(i!=j && matrix[i][j] == false) ;//error
		if(isRowEmpty(matrix[j]) || max_level >= TARGET)
		{
			return new Element(new ArrayList<Integer>( Arrays.asList(j)),0);
		}
		level++;
		if(level>max_level)
		{
			max_level = level;
			System.out.printf("Deepest level increased: %d\n", max_level);
		}
//		if(level > CANDIDATE_TARGET)
//			System.out.printf("(%d,%d) Level better than cutoff(%d): %d\n", i,j,CUTOFF_TARGET,level);
			
		HashMap<Integer,Boolean> lock = null;
		matrix[i][j] = false;//Used node
		lock = lockColumn(matrix, j);
		
		//Recurse in to the longest subpath
//		Element best = findLongestPath(j, matrix);
		//Random selection of leaves. Will only matter if MAX_TIME is small thus affecting which leaves get attention
		Element best = findLongestPathRand(j, matrix);

		//System.out.printf("findPath row %d has n = %d nodes\n",j, rowContainsNode(matrix[j]));
		
		matrix[i][j] = true;//can be used again
		unLockColumn(matrix, j, lock);
		level--;
		best.path.add(0,j);//add current node to subpath
		best.length +=1;
		return best;
	}
	
	private Element findLongestPath(int j,boolean[][] matrix)
	{
		Element best = new Element(new ArrayList<Integer>(),0);//empty
		for(int n=0;n<matrix[0].length;n++)
		{
			if(matrix[j][n] == true && j!=n)
			{
				Element tmp = findPath(j,n);
				if(tmp==null) continue;
				if(tmp.length > best.length )
					best = tmp;
			}
		}
		return best;
	}
	private Element findLongestPathRand(int j,boolean[][] matrix)//Random subpath selection experiment
	{
		Element best = new Element(new ArrayList<Integer>(),0);//empty
		ArrayList<Node> nodelist = new ArrayList<HamiltonPath.Node>();
		for(int n=0;n<matrix[0].length;n++)
		{
			if(matrix[j][n] == true && j!=n)
			{
				nodelist.add(new Node(j,n));
			}
		}
		while(nodelist.isEmpty() == false)
		{
			Node n = nodelist.remove(randInt(0, nodelist.size()-1));
			Element tmp = findPath(n.i,n.j);
			if(tmp==null) continue;
			if(tmp.length > best.length )
				best = tmp;
		}
		return best;
	}
	
	//Matrix manipulation methods
    private ArrayList<Node> findAllNodes()
    {
        int size = matrix[0].length;
        ArrayList<Node> nodes = new ArrayList<Node>();
        for(int i = 0;i<size;i++)
        {
            for(int j = 0; j<size;j++)
            {
                if(matrix[i][j] == true)
                    nodes.add(new Node(i,j));
            }
        }
        return nodes;
    }

	private static boolean isRowEmpty(boolean[] in)
	{
		for(int i=0;i<in.length;i++)
		{
			if(in[i] == true) return false;
		}
		return true;
	}
	private HashMap<Integer,Boolean> lockColumn(boolean[][] m,int c)
	{
		HashMap<Integer,Boolean> map = new HashMap<Integer, Boolean>();
		for(int i=0;i<m[0].length;i++)
		{
			if(m[i][c] == true)
			{
				map.put(i, m[i][c]);
				m[i][c] = false;
			}
		}
		return map;
	}
	private void unLockColumn(boolean[][] m, int c, HashMap<Integer,Boolean> map)
	{
		for(int i=0;i<m[0].length;i++)
		{
			if(map.containsKey(i))
			{
				m[i][c] = true;
			}
		}
		return;
	}

	private static int rowContainsNode(boolean[] row)
	{
		int r=0;
		for(int i=0;i<row.length;i++)
		{
			if(row[i] == true) r++;
		}
		return r;
		
	}


    //Utility functions for creating a graph from movie titles

    //TODO Create a utility class for these problem specific methods

    private static void printMatrix(boolean[][] m)
	{
		int i=0;
		int j=0;
		int len=m[0].length;
		for(i=0;i<len;i++)
		{
			for(j=0;j<len;j++)
			{
				if(m[i][j] == true)
					System.out.print("X ");
				else
					System.out.print("O ");
			}
			System.out.print("\n");
		}
	}

	private static boolean[][] makeMatrix(ArrayList<String> input)
	{
		int size = input.size();
		boolean[][] result = new boolean[size][size];//0 based indices
		System.out.printf("Creating %d x %d matrix\n", size,size);
		for(int i = 0; i < size;i++)
		{
			for(int j = 0; j < size;j++)
			{
				if(i==j)
					result[i][j]=false;
				else
				{
					result[i][j]=doesChain(input.get(i), input.get(j));
				}
			}
			//System.out.printf("Calculated %d items\n", i*size);
		}

        System.out.println("Done creating matrix");
        long nodes = 0;
        long bidirectionals = 0;
        for(int i=0;i<titles.size();i++)
        {
            for(int j=0;j<titles.size();j++)
                if(result[i][j] == true)
                {
                    nodes++;
                    if(result[j][i] == true)
                        bidirectionals++;
                }
        }
        bidirectionals /= 2;
        System.out.printf("Found %d nodes\n", nodes);
        System.out.printf("Found %d bidirectional nodes\n", bidirectionals);

        return result;
	}

	private static boolean doesChain(String s1, String s2)
	{
//		ArrayList<String> subs = subStrings(s1);
		String[] s2Words = s2.split(" ");
		String[] s1Words = s1.split(" ");
		int word_len = Math.min(s1Words.length,s2Words.length);

		for(int i = 0; i< word_len;i++)//Outer loop = number of words to test
		{
			int p1 = s1Words.length-1-i;
			int p2 = 0;
			boolean doesChain = true;
			//Inner loop test all i words match
			for(int j=0;j<=i;j++)
			{
				if(s1Words[p1].equals(s2Words[p2]) == false)
				{
					doesChain=false;
					break;
				}
				p1++;
				p2++;
			}
			if(doesChain == true)
				return true;
		}
		return false;
    }
	private static ArrayList<String> subStrings(String s1)
	{
		String[] strList = s1.split(" ");
		StringBuilder sb = new StringBuilder();
		ArrayList<String> result = new ArrayList<String>();
		for(int j=strList.length-1;j>=0;j--)
		{
			sb.insert(0,strList[j]);
			result.add(sb.toString());
			sb.insert(0," ");
		}
		return result;
	}

    //Embedded classes

    //Represents a branch of a tree. Used to find the longest path from a node
    private class Element{
        public Element(ArrayList<Integer> p, int l)
        {
            path = p;
            length = l;
        }
        ArrayList<Integer> path;
        int length;
    }

    //A node in path
    private class Node{
        int i,j;
        public Node(int i,int j){
            this.i=i;
            this.j=j;
        }
    }

    //A path
    private class Path{
        Node root;
        Element path;
        public Path(Node aRoot,Element aElem)
        {
            root = aRoot;
            path = aElem;
        }
    }
    //Interrupts a recursion
    private class InterruptTask extends TimerTask {

        public void run() {
//			System.out.printf("Interrupting at %d\n",level);
            TARGET = level;
            interrupted = true;
        }
    }
    //Static helper methods

    public static int randInt(int min, int max) {

        Random rand = new Random();
        rand.setSeed(SEED);
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
    private static void printTimeStamp(){
        java.util.Date date= new java.util.Date();
        System.out.println(new Timestamp(date.getTime()));
    }
    private static void printPaths(ArrayList<Path> paths){
        System.out.print("Best paths (L,i,j)\n");
        for(Path p : paths)
        {
            System.out.printf("%d,%d,%d\n",p.path.length,p.root.i,p.root.j);
        }
    }
    private static void printPath(ArrayList<Integer> path)
    {
        for(int q = 0; q < path.size();q++)
        {
            System.out.println(titles.get(path.get(q)));

        }
        System.out.printf("\npath depth %d\n", path.size());
    }
}
