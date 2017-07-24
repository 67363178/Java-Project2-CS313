
public class LinkedList {
	ListNode in=new ListNode();
	ListNode head=in;
	ListNode tail=in;
	
	/*
	 * add -O(1)
	 */
	public void add (String x){
		ListNode n=new ListNode(x);
		tail.next=n;
		tail=n;
		
	}
	

	/*
	 * Linked List to array
	 * O(n)
	 */
	public String[] toArray(String [] array){
		ListNode p=head.next;
		int i=0;
		String data="";
		while(p!=null){
			
				data =p.data;
				array[i]=data;
				i=i+1;
				p=p.next;
			
		}
		return array;
	}
	
	/*
	 * Linked List to String
	 * O(n)
	 */
	public String toString(){
		ListNode p=head.next;
		String returnString="";
		while(p!=null){
			returnString +=p.data+"\n";
			p=p.next;
		}
		return returnString;
	}
}
