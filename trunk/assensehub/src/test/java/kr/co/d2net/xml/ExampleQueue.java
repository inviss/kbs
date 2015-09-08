package kr.co.d2net.xml;

import java.util.LinkedList;
import java.util.Queue;

public class ExampleQueue {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Queue<String> que = new LinkedList<String>();
			que.offer("1111");
			que.offer("2222");
			que.offer("3333");
			que.offer("4444");
			que.offer("5555");
			
			while(que.peek() != null) {
				System.out.println(que.poll());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
