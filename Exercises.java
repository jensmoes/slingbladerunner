import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.management.ListenerNotFoundException;


public class Exercises {

	public Exercises() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.print(stairs(10));
//		System.out.println("\n");
//		System.out.print(iterate_stairs(10));
//		System.out.print(dynamic_stairs(10));
//		System.out.print(reverse_number(-123));
		int[] array = {1,2,2,3,3,3};
//		System.out.print(removeDupes(array).length);
				
		//System.out.print(palindrome_number(131));
		
		int[] twosumarray = {2,7,11,15,-2,21,4,3};
	//	twoSum(twosumarray, 13);
		String str = new String("testicles");
//		System.out.print(reverseStringIter(str));
		System.out.print(reverseStringQuick(str.toCharArray()));
		str.substring(1);
		quickSort(twosumarray, 0, twosumarray.length-1);
		//palindSystem.out.print(twosumarray.toString());
		System.out.print(longestPalindrome2(new String("dabbcba")));
	}
	
	public static String reverseString(String s)
	{
		if(s.length() < 2) return s;
		String result = reverseString(s.substring(1));
		return result + s.charAt(0);
	}
	public static String reverseStringIter(String s)
	{
		char[] carr = s.toCharArray();
//		//String result = new String();
//		char[] result = new char[s.length()]; 
//		for(int i=s.length()-1;i>=0;i--)
//		{
//			result[s.length() -1 - i] = carr[i];
//		}
//		return String.copyValueOf(result);
		
		StringBuilder result = new StringBuilder();
		for(int i = carr.length-1;i>=0;i--)
		{
			result.append(carr[i]);
		}
		return result.toString();
	}
	
	public static char[] reverseStringQuick(char[] s)
	{
		if(s.length < 2) return s;
		
		int i = 0;
		int j = s.length -1;
		while(i<j)
		{
			char tmp = s[i];
			s[i] = s[j];
			s[j] = tmp;
			i++;
			j--;
		}
		return s;
	}

	public static ArrayList<int[]> twoSum(int[] numbers, int target)
	{
		//Store the values we are looking for in a hashmap
		HashMap<Integer,Integer> map = new HashMap<Integer, Integer>();
		ArrayList<int[]> results = new ArrayList<int[]>();
		for(int i =0; i<numbers.length;i++)
		{
			if(map.containsKey(numbers[i]))
			{
				//A number we are looking for, insert it into results
				int[] res = new int[2];
				res[0] = map.get(numbers[i]) + 1;
				res[1] = i+1;
				results.add(res);
			}
			else
			{
				//A number we havent seen before, insert it into the map
				map.put(target-numbers[i],i);
			}
		}
		return results;
	}
	public static double power(double base, int exponent)
	{
		//Use the fact x^n = x^(n/2+n/2+n%2) = x^(n/2) * x^(n/2) * x^(n%2)
		double result = 1;
		
		if(exponent == 0) return 1;//End recursion
		
		result = power(base, exponent/2);//By dividing by 2 you split up multiplications so that x^4 = x^2 * x^2 instead of x*x*x*x you now have (x*x)*(x*x) which is 3 operations
		if(exponent%2 == 0)//Potentially save one multiplication operation
			return result*result;
		else
			return result*result*base;
	}
	public static double pow(double base, int exponent){
		//Use inverse for negative numbers
		if(exponent < 0)
			return 1/power(base, -exponent);
		else
			return power(base, exponent);
		
	}
	
	//Climbing stairs taking 1 or two steps, how many ways can you climb them?
	
	public static long stairs(int steps)
	{
		//Because climbing to step n can be done in 1 or 2 steps, you either come from step n-1 or n-2 depending on your choice
		//The number of ways to get to step n is therefore f(n-1) + f(n-2) which is a Fibonacci sequence
		if(steps <= 2)//Zero is zero, 1 has one option, 2 has 2 options (1,1 or 2)
			return steps;
		return stairs(steps-1) + stairs(steps-2);
	}
	public static long iterate_stairs(int steps)
	{
		if(steps <= 2)//Zero is zero, 1 has one option, 2 has 2 options (1,1 or 2)
			return steps;

		long one = 1;
		long two = 2;
		long three = 0;
		for(int i=3;i<=steps;i++)//From the 3rd step to the last
		{
			three = one + two;//Steps to get to current one
			one = two;//Increment n-2
			two = three;//and n-1
		}
		return three;
	}
	public static long dynamic_stairs(int steps)
	{
		ArrayList<Integer> A = new ArrayList<Integer>(100);
		A.add((int)0, (int)0);
		A.add(1,1);
		A.add(2,2);
		
		for(int i = 0; i<=steps;i++)
		{
			if(A.size() <= i)
			{
				A.add(i, A.get(i-1)+A.get(i-2));
			}
		}
		return A.get(steps);
	}
	
	//Reversing a number by traversing the digits using /10 and inverting the result by using *10 + modulus
	public static int reverse_number(int number)
	{
		int result = 0;
		
		while(number != 0)
		{
			int modulus = number % 10;//The trailing digit
			result = result*10 + modulus;//Increment tens and add remainder
			number = number / 10;//Decrement number by one factor to remove used trailing digit
		}
		return result;
	}
	//Check if a number is palindromic
	public static boolean palindrome_number(int number)
	{
		//Determine number of digits by calculating divisor
		int div = 1;//One digit, two digits = 10 etc.
		while(number/div >= 10)
		{
			div = div * 10;
		}
		//Now div is the magnitude of the number
		int left, right;
		while(div > 1){
			//Get the left most digit by integer division with divisor 456/100 = 4
			left = number/div;
			//Right digit is found using modulus 10
			right = number%10;
			if(right != left) return false;
			//Clip checked digits
			number = number%div;//Left digit from modulus of the divisor
			number = number/10;//Right digit by dividing by 10
			//Adjust divisor by a factor of 2 since we removed 2 digits
			div = div/100;
		}
		return true;
	}
	
	//Removing dupes from a sorted array
	public static int[] removeDupes(int[] array)
	{
		int uniqueIndex = 0;
		int i = 1;//Start comparison at second item
		
		while(i < array.length)
		{
			if(array[i] == array[uniqueIndex])//We have a duplicate
			{
				i++;//Skip this item
			}
			else//A unique item
			{
				uniqueIndex++;//We have one more unique item
				array[uniqueIndex] = array[i];//Insert the new value in the first available spot
				i++;//Continue
			}
		}
		//Copy the array up to and including last unique item
		return Arrays.copyOf(array, uniqueIndex+1);

	}
	
	public static int partition(int[] A, int left, int right)
	{
		int partition = A[(right+left)/2];//We select the middle. Or left + (right-left)/2 to prevent overflow of right+left
		int i = left;
		int j = right;
		while(i<=j)
		{
			while(A[i] < partition)
				i++;//Increment left until we have a value greater than or equal to partition
			while(A[j] > partition)
				j--;
			//Check i and j again
			if(j>=i)//Swap the values and move pointers inwards
			{
				int temp = A[i];
				A[i] = A[j];
				A[j] = temp;
				i++;
				j--;
			}
		}
		return i;
	}
	public static void quickSort(int[] A, int start, int end)
	{
		int partition = partition(A, start, end);
		//Now quick sort the left side
		if(partition -1 > start)
			quickSort(A, start, partition-1);//Must be at least two left
		if(partition < end)
			quickSort(A, partition, end);
	}
	
	public static String longestPalindrome2(String s) {
		if (s == null)
			return null;
	 
		if(s.length() <=1)
			return s;
	 
		int maxLen = 0;
		String longestStr = null;
	 
		int length = s.length();
	 
		int[][] table = new int[length][length];
	 
		//every single letter is palindrome
		for (int i = 0; i < length; i++) {
			table[i][i] = 1;
		}
		printTable(table);
	 
		//e.g. bcba
		//two consecutive same letters are palindrome
		for (int i = 0; i <= length - 2; i++) {
			if (s.charAt(i) == s.charAt(i + 1)){
				table[i][i + 1] = 1;
				longestStr = s.substring(i, i + 2);
			}	
		}
		printTable(table);
		//condition for calculate whole table
		for (int l = 3; l <= length; l++) {
			for (int i = 0; i <= length-l; i++) {
				int j = i + l - 1;
				if (s.charAt(i) == s.charAt(j)) {
					table[i][j] = table[i + 1][j - 1];
					if (table[i][j] == 1 && l > maxLen)
						longestStr = s.substring(i, j + 1);
				} else {
					table[i][j] = 0;
				}
				printTable(table);
			}
		}
	 
		return longestStr;
	}
	public static void printTable(int[][] x){
		for(int [] y : x){
			for(int z: y){
				System.out.print(z + " ");
			}
			System.out.println();
		}
		System.out.println("------");
	}

}
