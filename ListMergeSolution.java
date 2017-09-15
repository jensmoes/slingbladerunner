
	//A node for linked lists
	class ListNode {
		int val;
		ListNode next;
		
		ListNode(int aValue){
			val = aValue;
			next = null;
		}
	}
	
	//Add numbers of two sorted lists
	class ListSums {
		public ListSums(){
			
		}
		
		//Sums up two lists of inverted digits 2,4,3 + 5,6,4 = 7,0,8 (465 + 342) essentially
		public ListNode sumLists(ListNode l1, ListNode l2){
			ListNode p1 = l1;
			ListNode p2 = l2;
			ListNode head = new ListNode(0);
			ListNode pNew = head;
			boolean carry = false;//Is there a remainder to be carried over
			while(p1 != null || p2 !=null)//Go as long as there is data in one
			{
				
				int p1val = 0,p2val = 0;//Placeholders for values
				//Grab the values to be added
				if(p1 != null && p2 != null)//Both have values
				{
					p1val = p1.val;
					p2val = p2.val;
					p1 = p1.next;
					p2 = p2.next;
				}
				else if(p1 == null){
					p2val = p2.val;
					p2 = p2.next;
				}
				else{
					p1val = p1.val;
					p1 = p1.next;
				}
				
				int result = 0;
				if(carry){
					result = p1val+p2val+1;//Remainder can only be one for digits 0-9
				}else{
					result = p1val + p2val;
				}
				//save result and increment pNew
				if(result>=10){
					pNew.next = new ListNode(result%10);
					carry = true;
				}else{
					pNew.next = new ListNode(result);
					carry = false;
				}
				pNew = pNew.next;
				
			}
			
			return head.next;
		}
	}

public class ListMergeSolution {

	public ListMergeSolution() {
		// TODO Auto-generated constructor stub
	}


	
	
		//Merges two sorted lists. This may modify original lists since no new objects are created and next is changed
		public ListNode mergeLists(ListNode list1, ListNode list2){
			//Set working pointers to both lists
			ListNode p1 = list1;
			ListNode p2 = list2;
			//A head of the new list
			ListNode head = new ListNode(0);
			//A working pointer for the new list
			ListNode p = head;//head.next will be the first item in new list
			
			while(p1 != null && p2 != null)//Go until one list is empty
			{
				if(p1.val <= p2.val)//Find lowest value
				{
					//p.val = p1.val; 
					//Create a new node instead if you want to copy objects in order to not modify old lists
					p.next = p1;//Insert item in list
					p1 = p1.next;//Increment working pointer
				}else
				{
					//p.val = p2.val;
					p.next = p2;
					p2 = p2.next;
				}
				p = p.next;//Point to the last item in the new list
			}
			//One of the lists are empty
			//Append the rest of the remaining to the new list
			if(p1 == null)
			{
				p.next = p2;
			}
			else
			{
				p.next = p1;
			}

			return head.next;
			
		}
		
		/**
		 * @param args
		 */
		public static void main(String[] args) {
			ListNode n1 = new ListNode(2);
			ListNode n2 = new ListNode(4);
			ListNode n3 = new ListNode(3);
	 
			ListNode n4 = new ListNode(5);
			ListNode n5 = new ListNode(5);
			ListNode n6 = new ListNode(6);
	 
			n1.next = n2;
			n2.next = n3;
			n3.next = n4;
			n4.next = n5;
			n5.next = n6;

			ListNode m1 = new ListNode(5);
			ListNode m2 = new ListNode(6);
			ListNode m3 = new ListNode(4);
	 
			ListNode m4 = new ListNode(5);
			ListNode m5 = new ListNode(6);
			ListNode m6 = new ListNode(7);
			ListNode m7 = new ListNode(8);
	 
			m1.next = m2;
			m2.next = m3;
			m3.next = m4;
			m4.next = m5;
			m5.next = m6;
			m6.next = m7;

			ListMergeSolution merger = new ListMergeSolution();
			ListNode pNew;
			//= merger.mergeLists(n1, m1);
			//printList(pNew);
			
			//Reuse old list but fewer digits
			n3.next = null;
			m4.next = null;
			ListSums summer = new ListSums();
			pNew = summer.sumLists(n1, m1);
			printList(pNew);
			
	 
		}
		
		public static void printList(ListNode x) {
			if(x != null){
				System.out.print(x.val + " ");
				while (x.next != null) {
					System.out.print(x.next.val + " ");
					x = x.next;
				}
				System.out.println();
			}
	 
		}
		
}
