
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;
import java.util.*;

public class tests {
	@Test
	public void LargeScale() {
		AVLTree t1 = new AVLTree();

		int i = 0;
		while (i <= 99) {
			t1.insert(i, String.valueOf(i));
			i++;
		}
		AVLTree.print2D(t1.getRoot());

		//AVLTree.print2D(t1.getRoot());
		AVLTree [] splitted=t1.split(30);
		System.out.println(isBalanced(splitted[0].getRoot()));
		AVLTree.print2D(splitted[0].getRoot());
//		System.out.print(splitted[0].size());
//
//		System.out.print(Arrays.toString(splitted[0].keysToArray()));
//
//		splitted=splitted[0].split(9);
//		System.out.println(isBalanced(splitted[0].getRoot()));
//		System.out.print(Arrays.toString(t1.keysToArray()));

		
		
//		Random rand = new Random();
//
//		while (!t1.empty()) {
//			int[] keys = t1.keysToArray();
//
//			int k = rand.nextInt(t1.size());
//			AVLTree.print2D(t1.getRoot());
//			System.out.println(k);
//			System.out.println(Arrays.toString(keys));
//			System.out.println(keys[k] + "  " + t1.size());
//			AVLTree[] splitted = t1.split(keys[k]);
//			if (!isBalanced(t1.getRoot())) {
//				System.out.println("oops");
//			}
//			t1 = splitted[1];
//
//
//		}
//		AVLTree t2 = new AVLTree();
//		t2.insert(8, "8");
//		AVLTree [] splitted = t2.split(8);
//		System.out.println(splitted[0].getRoot().getKey());
//		System.out.println(splitted[1].getRoot().getKey());
//
//		AVLTree.print2D(splitted[1].getRoot());

//		AVLTree t2 = new AVLTree();
//		i = 0;
//		while (i < 1005) {
//			t2.insert(i, String.valueOf(i));
//			i++;
//		}
//
//		t1.join(new AVLTree.AVLNode(1500, "1500"), t2);
//
//		AVLTree t3 = new AVLTree();
//		AVLTree t4 = new AVLTree();
//		Random rand = new Random(23456767);
//		int size = t1.size();
//		i=0;
//		while (i<300) {
//			int value = rand.nextInt(t1.size());
//			if(t1.delete(value)!=-1) {
//				i++;
//				size--;
//			}
//			if(!isBalanced(t1.getRoot())) {
//				System.out.println("oops");
//			}
//			if(size!=t1.size()) {
//				System.out.println("oops");
//			}
//		}
//		System.out.println("finished");

		// t1.join(new AVLTree.AVLNode(30, "30"), t3);
		// System.out.println(isBalanced(t1.getRoot()));
//
//		for(int j =0; j<1000;j++) {
//			System.out.println("-------------------------------");
//			System.out.println(t2.max());
//			t2.delete(Integer.valueOf(t2.max()));
//			System.out.println(t2.size());
//
//
//		}
		// System.out.println(t2.max());
//		AVLTree[] splitted = t2.split(t2.getRoot().getKey());
//		System.out.println(splitted[0].max() + " " + splitted[1].max());
//		System.out.println(splitted[0].delete(splitted[0].getRoot().getKey()));
		// splitted[0].join(new AVLTree.AVLNode(998,"998"),splitted[1]);
		// System.out.println(splitted[0].min());
		// System.out.println(t2.size());

	}

	public boolean isBalanced(AVLTree.IAVLNode node) {
		int lh; /* for height of left subtree */

		int rh; /* for height of right subtree */

		/* If tree is empty then return true */
		if (node == null || node.isRealNode() == false)
			return true;

		/* Get the height of left and right sub trees */
		lh = node.getLeft().getHeight();
		rh = node.getRight().getHeight();

		if (Math.abs(lh - rh) <= 1 && isBalanced(node.getLeft()) && isBalanced(node.getRight()))
			return true;

		/* If we reach here then tree is not height-balanced */
		return false;

	}

	public boolean splitMany(AVLTree t) {
		if (t.empty()) {
			return true;
		}
		if (!isBalanced(t.getRoot())) {
			return false;
		}
		int[] keys = t.keysToArray();
		Random rand = new Random();
		int k = rand.nextInt(t.size());
		AVLTree[] splitted = t.split(keys[k]);
		return splitMany(splitted[0]) && splitMany(splitted[1]);

	}

//	@Test
	public void RebalancingCount() {

		// only insertion
		AVLTree.AVLNode a = new AVLTree.AVLNode(100, "100");

		AVLTree t1 = new AVLTree();
		assertEquals(0, t1.insert(10, "10"));
		assertEquals(0, t1.insert(20, "20"));
		assertEquals(0, t1.insert(5, "5"));
		assertEquals(0, t1.insert(15, "15"));
		assertEquals(0, t1.insert(40, "40"));
		assertEquals(2, t1.insert(13, "13"));
		assertEquals(15, t1.getRoot().getKey());
		assertEquals("40", t1.max());
		assertEquals("5", t1.min());
		assertEquals(1, t1.insert(50, "50"));
		assertEquals("50", t1.max());
		assertEquals(0, t1.insert(60, "60"));
		assertEquals(0, t1.insert(6, "6"));
		assertEquals(1, t1.insert(7, "13"));

		AVLTree t2 = new AVLTree();
		assertEquals(0, t2.insert(1, "1"));
		assertEquals(0, t2.insert(2, "2"));
		assertEquals(1, t2.insert(3, "3"));
		assertEquals(2, t2.getRoot().getKey());
		assertEquals(0, t2.insert(4, "4"));
		assertEquals(1, t2.insert(5, "5"));
		assertEquals("5", t2.max());
		assertEquals("1", t2.min());
		assertEquals(1, t2.insert(6, "6"));
		assertEquals("6", t2.max());
		assertEquals(4, t2.getRoot().getKey());
		assertEquals(1, t2.insert(7, "7"));
		assertEquals("7", t2.max());
		assertEquals(-1, t2.insert(1, "1"));

		// should we check a non avlnode insertion?
		// deletions and insertions
		AVLTree t3 = new AVLTree();
		assertEquals(0, t3.insert(50, "50"));
		assertEquals(0, t3.insert(200, "200"));
		assertEquals(2, t3.insert(100, "100"));
		assertEquals(0, t3.insert(250, "250"));
		assertEquals(2, t3.insert(210, "210"));
		assertEquals(0, t3.insert(40, "40"));
		assertEquals(1, t3.insert(10, "10"));
		assertEquals(0, t3.insert(15, "15"));
		assertEquals(-1, t3.insert(100, "100"));
		assertEquals(0, t3.delete(100));
		assertEquals(1, t3.delete(200));
		assertEquals(0, t3.delete(50));
		assertEquals(1, t3.insert(500, "500"));
		assertEquals(0, t3.insert(600, "600"));
		assertEquals(1, t3.insert(700, "700"));
		assertEquals(0, t3.delete(600));
		assertEquals(1, t3.insert(401, "401"));
		assertEquals(2, t3.insert(402, "402"));
		assertEquals(1, t3.insert(200, "200"));
		assertEquals(1, t3.insert(800, "800"));
		assertEquals(1, t3.insert(900, "900"));
		assertEquals(1, t3.insert(20, "20"));
		assertEquals(0, t3.insert(25, "25"));
		assertEquals(1, t3.insert(27, "27"));
		assertEquals(1, t3.insert(30, "30"));
		assertEquals(1, t3.insert(35, "35"));
		assertEquals("900", t3.max());
		assertEquals("10", t3.min());
		assertEquals(0, t3.delete(500));
		assertEquals(1, t3.delete(900));
		assertEquals(0, t3.delete(200));
		assertEquals(0, t3.delete(250));
		assertEquals(1, t3.delete(210));
		assertEquals(2, t3.delete(700));
		t3.delete(200);
		t3.delete(800);
		t3.delete(20);
		// AVLTree.print2D(t3.getRoot());
		// System.out.println("--------------------");
		AVLTree[] splitted = t3.split(35);
		AVLTree m = splitted[0];
		m.join(new AVLTree.AVLNode(35, "35"), splitted[1]);
		// AVLTree.print2D(m.getRoot());
		assertEquals("40", t3.search(40));
		assertEquals(null, t3.search(10000));

	}
//
//	@Test
//	public void joining() {
//		AVLTree t1 = new AVLTree();
//		t1.insert(10, "10");
//		t1.insert(20, "20");
//		t1.insert(5, "5");
//		t1.insert(15, "15");
//		t1.insert(40, "40");
//		t1.insert(13, "13");
//		AVLTree.AVLNode a = new AVLTree.AVLNode(100, "100");
//		AVLTree t2 = t1;
//		t2.insert(100, "100");
//		AVLTree t3 = new AVLTree();
//		assertEquals(4, t1.join(a, t3));
//		assertEquals(t2, t1);
//		AVLTree t4 = new AVLTree();
//		AVLTree.AVLNode b = new AVLTree.AVLNode(2, "2");
//		assertEquals(4, t4.join(b, t1));
//		AVLTree t5 = new AVLTree();
//		t5.insert(67, "67");
//		t5.insert(100, "100");
//		t5.insert(200, "200");
//		t5.insert(60, "60");
//		t5.insert(45, "45");
//		t5.insert(300, "300");
//		AVLTree t6 = new AVLTree();
//		t6.insert(10, "10");
//		t6.insert(20, "20");
//		t6.insert(5, "5");
//		t6.insert(15, "15");
//		t6.insert(40, "40");
//		t6.insert(13, "13");
//		b = new AVLTree.AVLNode(41, "41");
////		AVLTree.print2D(t5.getRoot());
////		AVLTree.print2D(t6.getRoot());
//
//		t5.join(b, t6);
//		assertEquals(0,t5.delete(300));
//		assertEquals(1,t5.delete(200));
//
//		//AVLTree.print2D(t5.getRoot());
//		
//
//	}
//
//	@Test
//	public void splitting() {
//		AVLTree t1 = new AVLTree();
//		t1.insert(100, "100");
//		t1.insert(50, "50");
//		t1.insert(200, "200");
//		t1.insert(30, "30");
//		t1.insert(150, "150");
//		t1.insert(300, "300");
//		t1.insert(20, "20");
//		t1.insert(400, "400");
//		t1.insert(30, "30");
//		t1.insert(500, "500");
//		t1.insert(2, "2");
//		t1.delete(100);
//		t1.insert(100, "100");
//		t1.insert(799, "799");
//
//		AVLTree[] splitted = t1.split(200);
//		AVLTree t2 = splitted[0];
//		AVLTree t3 = splitted[1];
////	t2.print2D(t2.getRoot());
////	System.out.println("------------------------------------------------------");
////	t3.print2D(t3.getRoot());
//
//		// assertTrue(Arrays.equals(splitted1, splitted2));
//
//	}
}
