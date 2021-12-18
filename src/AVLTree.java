// Details: Goni Cohen 308503291 gonicohen, Hana Rivman 318419132 hanarivman.


/**
 * AVLTree
 * <p>
 * An implementation of an AVL Tree with distinct integer keys and info.
 */

public class AVLTree {
	private IAVLNode root = new AVLNode();
	private IAVLNode min;
	private IAVLNode max;

	/**
	 * public boolean empty()
	 * <p>
	 * Returns true if and only if the tree is empty.
	 */
	public boolean empty() {
		return !root.isRealNode();
	}

	/**
	 * public String search(int k)
	 * <p>
	 * Returns the info of an item with key k if it exists in the tree. otherwise,
	 * returns null. Calls search_node which returns the node pointer if such node
	 * exists, else, returns null. The complexity of the function is the complexity
	 * of search_node, which is O(log(n)).
	 */
	public String search(int k) {
		return search_node(k).getValue();
	}

	/**
	 * private IAVLNode search_node(int x)
	 * <p>
	 * Searches the node with key k starting from root, returns null if the node is
	 * not found. Complexity=O(log(n)).
	 */
	private IAVLNode search_node(int x) {
		IAVLNode node = root;
		while (node.isRealNode()) {
			if (node.getKey() < x) {
				node = node.getRight();
			} else if (node.getKey() > x) {
				node = node.getLeft();
			} else {
				return node;
			}
		}
		return node;
	}

	/**
	 * public int insert(int k, String i)
	 * <p>
	 * Inserts an item with key k and info i to the AVL tree. The tree must remain
	 * valid, i.e. keep its invariants. Returns the number of re-balancing
	 * operations, or 0 if no re-balancing operations were necessary. A
	 * promotion/rotation counts as one re-balance operation, double-rotation is
	 * counted as 2. Returns -1 if an item with key k already exists in the tree.
	 * Updates min and max pointers, runs insert_rec, then rebalances Runs in
	 * O(log(n)) complexity
	 */
	public int insert(int k, String i) {
		IAVLNode node;
		if (!root.isRealNode()) {
			node = new AVLNode(k, i);
			root = node;
			max = root;
			min = root;
			return 0;
		}
		if (search(k) != null) {
			return -1;
		}
		node = insert_rec(root, k, i);
		max = max.getKey() < k ? node : max;
		min = min.getKey() > k ? node : min;
		return insert_rebalance(node);
	}

	/**
	 * private IAVLNode insert_rec(IAVLNode node, int k, String i)
	 * <p>
	 * Recursive function that inserts a node, and returns the pointer of the node
	 * inserted Complexity = O(log(n))
	 */

	private IAVLNode insert_rec(IAVLNode node, int k, String i) {
		node.setSize(node.getSize() + 1);
		if (node.getKey() < k) {
			if (node.getRight().isRealNode()) {
				return insert_rec(node.getRight(), k, i);
			} else {
				node.setRight(new AVLNode(k, i));
				node.getRight().setParent(node);
				return node.getRight();
			}
		} else {
			if (node.getLeft().isRealNode()) {
				return insert_rec(node.getLeft(), k, i);
			} else {
				node.setLeft(new AVLNode(k, i));
				node.getLeft().setParent(node);
				return node.getLeft();
			}

		}

	}

	/**
	 * private int insert_rebalance(IAVLNode node)
	 * <p>
	 * Performs O(log(n)) iterations starting from inserted node(always a leaf
	 * node), moving up.Performs a rotation if needed. Runs in O(log(n)) complexity.
	 */

	private int insert_rebalance(IAVLNode node) {
		updateHeight(node);
		updateSize(node);
		node = node.getParent();
		int count = 0;
		while (node != null) {
			int prevHeight = node.getHeight();
			updateHeight(node);
			updateSize(node);
			if (prevHeight != node.getHeight()) {
				if (Math.abs(bf(node)) == 2) {
					int[] arr = rotations_check_ins(node);
					node = insert_rotations(node, arr[0]);
					count += arr[1];
				}
				node = node.getParent();

			} else {
				break;
			}
		}

		return count;
	}

	/**
	 * private int[] rotations_check_ins(IAVLNode node)
	 * <p>
	 * Checks bf of node and its son, returns an array of [rotation case, num of
	 * rotations]. Complexity is O(1).
	 */

	private int[] rotations_check_ins(IAVLNode node) {
		int[] result = new int[2];

		if (bf(node) == -2 && bf(node.getRight()) == -1) {
			result[0] = 1;
			result[1] = 1;
			return result;
		}
		if (bf(node) == -2 && bf(node.getRight()) == 1) {
			result[0] = 2;
			result[1] = 2;
			return result;
		}
		if (bf(node) == 2 && bf(node.getLeft()) == -1) {
			result[0] = 3;
			result[1] = 2;
			return result;
		}
		if (bf(node) == 2 && bf(node.getLeft()) == 1) {
			result[0] = 4;
			result[1] = 1;
		}
		return result;
	}

	/**
	 * private IAVLNode insert_rotations(IAVLNode node, int i)
	 * <p>
	 * Gets the node to be rotated and the rotation case, then rotates and returns
	 * the upper node of the rotation. Complexity is O(1).
	 */

	private IAVLNode insert_rotations(IAVLNode node, int i) {

		if (i == 1) {
			node = leftRotate(node);
			return node;
		}
		if (i == 2) {
			rightRotate(node.getRight());
			node = leftRotate(node);
			return node;

		}
		if (i == 3) {
			leftRotate(node.getLeft());
			node = rightRotate(node);
			return node;
		}
		if (i == 4) {
			node = rightRotate(node);
		}
		return node;

	}

	/**
	 * private IAVLNode leftRotate(IAVLNode n)
	 * <p>
	 * Updates child and parent pointers according to cases learnt in class, and
	 * returns the right child of the node. Complexity is O(1).
	 */

	private IAVLNode leftRotate(IAVLNode n) {
		IAVLNode right_child = n.getRight();
		IAVLNode left_child_of_right_child = right_child.getLeft();
		IAVLNode parent = n.getParent();
		n.setRight(left_child_of_right_child);
		right_child.setLeft(n);
		if (parent == null) {
			this.root = right_child;
			right_child.setParent(null);
		} else {
			if (parent.getLeft() == n) {
				parent.setLeft(right_child);
			} else {
				parent.setRight(right_child);
			}
			right_child.setParent(parent);
		}
		if (left_child_of_right_child.isRealNode()) {
			left_child_of_right_child.setParent(n);
		}
		n.setParent(right_child);
		updateHeight(n);
		updateHeight(right_child);
		updateSize(n);
		updateSize(right_child);
		return right_child;
	}

	/**
	 * private IAVLNode rightRotate(IAVLNode n)
	 * <p>
	 * Updates child and parent pointers according to cases learnt in class, and
	 * returns the left child of the node. Complexity is O(1).
	 */

	private IAVLNode rightRotate(IAVLNode n) {
		IAVLNode left_child = n.getLeft();
		IAVLNode right_child_of_left_child = left_child.getRight();
		IAVLNode parent = n.getParent();
		n.setLeft(right_child_of_left_child);
		left_child.setRight(n);
		if (parent == null) {
			this.root = left_child;
			left_child.setParent(null);
		} else {
			if (parent.getLeft() == n) {
				parent.setLeft(left_child);

			} else {
				parent.setRight(left_child);

			}
			left_child.setParent(parent);
		}
		n.setParent(left_child);
		if (right_child_of_left_child.isRealNode()) {
			right_child_of_left_child.setParent(n);
		}
		updateHeight(n);
		updateHeight(left_child);
		updateSize(n);
		updateSize(left_child);
		return left_child;
	}

	/**
	 * public int delete(int k)
	 * <p>
	 * Deletes an item with key k from the binary tree, if it is there. The tree
	 * must remain valid, i.e. keep its invariants. Returns the number of
	 * re-balancing operations, or 0 if no re-balancing operations were necessary. A
	 * promotion/rotation counts as one re-balance operation, double-rotation is
	 * counted as 2. Returns -1 if an item with key k was not found in the tree.
	 * Calls O(logn) complexity functions, according to the node deleted. Complexity
	 * is O(logn).
	 */

	public int delete(int k) {
		IAVLNode deletedNode = search_node(k);
		if (!deletedNode.isRealNode()) {
			return -1;
		}
		if (deletedNode == root) {
			return delete_root(deletedNode);
		}
		IAVLNode rightChild = deletedNode.getRight();
		IAVLNode leftChild = deletedNode.getLeft();
		if (deletedNode == min) {
			min = successor(deletedNode);
		}
		if (deletedNode == max) {
			max = predecessor(deletedNode);
		}
		if (!rightChild.isRealNode() && !leftChild.isRealNode()) {
			return delete_leaf(deletedNode);
		}
		if (rightChild.isRealNode() && leftChild.isRealNode()) {

			return delete_binary(deletedNode);

		} else {
			return delete_unary(deletedNode);
		}

	}

	/**
	 * private int delete_root(IAVLNode node)
	 * <p>
	 * Deletes the root node according to cases (leaf, unary node, binary node).
	 * Complexity is O(logn).
	 */
	private int delete_root(IAVLNode node) {
		if (size() == 1) {
			min = null;
			max = null;
			root = new AVLNode();
			return 0;
		}
		IAVLNode rightChild = node.getRight();
		IAVLNode leftChild = node.getLeft();
		if (rightChild.isRealNode() && !leftChild.isRealNode()) {
			rightChild.setParent(null);
			this.root = rightChild;
			min = rightChild;
			return 0;
		}
		if (!rightChild.isRealNode() && leftChild.isRealNode()) {
			leftChild.setParent(null);
			this.root = leftChild;
			max = leftChild;
			return 0;
		}
		return delete_binary(node);
	}

	/**
	 * private int delete_leaf(IAVLNode node)
	 * <p>
	 * Deletes a leaf, then rebalances. Complexity is O(logn).
	 */

	private int delete_leaf(IAVLNode node) {
		IAVLNode parent = node.getParent();
		if (parent.getRight() == node) {
			parent.setRight(new AVLNode());
		} else {
			parent.setLeft(new AVLNode());
		}
		return delete_rebalance(parent);
	}

	/**
	 * private int delete_unary(IAVLNode node)
	 * <p>
	 * Deletes an unary node, then rebalances. Complexity is O(logn).
	 */

	private int delete_unary(IAVLNode node) {
		IAVLNode parent = node.getParent();
		IAVLNode rightChild = node.getRight();
		IAVLNode leftChild = node.getLeft();
		if (rightChild.isRealNode()) {
			if (parent.getRight() == node) {
				parent.setRight(rightChild);
			} else {
				parent.setLeft(rightChild);
			}
			rightChild.setParent(parent);
		} else {
			if (parent.getRight() == node) {
				parent.setRight(leftChild);
			} else {
				parent.setLeft(leftChild);
			}
			leftChild.setParent(parent);

		}
		return delete_rebalance(parent);
	}

	/**
	 * private int delete_binary(IAVLNode node)
	 * <p>
	 * Deletes an binary node, then rebalances. Complexity is O(logn).
	 */
	private int delete_binary(IAVLNode node) {
		IAVLNode parent = node.getParent();
		IAVLNode rightChild = node.getRight();
		IAVLNode leftChild = node.getLeft();
		IAVLNode successor = successor(node);
		IAVLNode successorParent = successor.getParent();
		IAVLNode successorRight = successor.getRight();
		if (successor == rightChild) {
			successor.setLeft(leftChild);
			leftChild.setParent(successor);
			successor.setParent(parent);
			successorParent = successor;

		} else {
			successorParent.setLeft(successorRight);
			successorRight.setParent(successorParent);
			successor.setRight(rightChild);
			rightChild.setParent(successor);
			successor.setLeft(leftChild);
			leftChild.setParent(successor);
			successor.setParent(parent);
		}
		if (parent != null) {
			if (parent.getRight() == node) {
				parent.setRight(successor);
			} else {
				parent.setLeft(successor);
			}
		} else {
			this.root = successor;
		}
		return delete_rebalance(successorParent);
	}

	/**
	 * private IAVLNode successor(IAVLNode node)
	 * <p>
	 * Finds successor in O(log(n))) complexity.
	 */

	private IAVLNode successor(IAVLNode node) {
		if (node.getKey() == max.getKey()) {
			return null;
		}
		if (!node.getRight().isRealNode() && node != root) {
			while (node.getParent().getRight() == node) {
				node = node.getParent();
			}
			return node.getParent();
		}

		node = node.getRight();
		while (node.getLeft().isRealNode()) {
			node = node.getLeft();
		}
		return node;
	}

	/**
	 * private int delete_rebalance(IAVLNode node)
	 * <p>
	 * Loops on all ancestors starting at the node returned after deleting and
	 * moving up. Performs a rebalance according to BF. Complexity is O(log(n)).
	 */
	private int delete_rebalance(IAVLNode node) {
		int count = 0;
		while (node != null) {
			updateHeight(node);
			updateSize(node);
			if (Math.abs(bf(node)) == 2) {
				int[] arr = rotations_check_del(node);
				node = delete_rotations(node, arr[0]);
				count += arr[1];
			}
			node = node.getParent();
		}
		return count;

	}

	/**
	 * private int[] rotations_check_del(IAVLNode node)
	 * <p>
	 * Checks bf of node and its son, returns an array of [rotation case, num of
	 * rotations]. Complexity is O(1).
	 */
	private int[] rotations_check_del(IAVLNode node) {
		int[] result = new int[2]; // first element for the required procedure,
									// second for the number of rotations needed in the chosen case.

		if (bf(node) == -2 && (bf(node.getRight()) == -1 || bf(node.getRight()) == 0)) {
			result[0] = 1;
			result[1] = 1;
			return result;
		}
		if (bf(node) == -2 && bf(node.getRight()) == 1) {
			result[0] = 2;
			result[1] = 2;
			return result;

		}
		if (bf(node) == 2 && bf(node.getLeft()) == -1) {
			result[0] = 3;
			result[1] = 2;
			return result;
		}
		if (bf(node) == 2 && (bf(node.getLeft()) == 1 || bf(node.getLeft()) == 0)) {
			result[0] = 4;
			result[1] = 1;
		}
		return result;

	}

	/**
	 * private IAVLNode delete_rotations(IAVLNode node, int i)
	 * <p>
	 * Gets the node to be rotated and the rotation case, then rotates and returns
	 * the upper node of the rotation. Complexity is O(1).
	 */

	private IAVLNode delete_rotations(IAVLNode node, int i) {

		if (i == 1) {
			node = leftRotate(node);
			return node;
		}
		if (i == 2) {
			rightRotate(node.getRight());
			node = leftRotate(node);
			return node;
		}
		if (i == 3) {
			leftRotate(node.getLeft());
			node = rightRotate(node);
			return node;
		}
		if (i == 4) {
			node = rightRotate(node);
		}
		return node;

	}

	/**
	 * public String min()
	 * <p>
	 * Returns the info of the item with the smallest key in the tree, or null if
	 * the tree is empty. Runs in O(1) complexity.
	 */

	public String min() {
		if (!empty()) {
			return min.getValue();
		}
		return null;
	}

	/**
	 * public String max()
	 * <p>
	 * Returns the info of the item with the largest key in the tree, or null if the
	 * tree is empty. Runs in O(1) complexity.
	 */
	public String max() {
		if (!empty()) {
			return max.getValue();
		}
		return null;
	}

	/**
	 * public int[] keysToArray()
	 * <p>
	 * Returns a sorted array which contains all keys in the tree, or an empty array
	 * if the tree is empty. Runs n successor calls, with overall complexity O(n).
	 */

	public int[] keysToArray() {
		int[] keys = new int[size()];
		IAVLNode node = min;

		int i = 0;
		while (node != null) {
			keys[i] = node.getKey();
			node = successor(node);
			i++;
		}
		return keys;
	}

	/**
	 * public String[] infoToArray()
	 * <p>
	 * Returns an array which contains all info in the tree, sorted by their
	 * respective keys, or an empty array if the tree is empty. Runs n successor
	 * calls, with overall complexity of O(n).
	 */
	public String[] infoToArray() {
		String[] values = new String[size()];
		IAVLNode node = min;
		int i = 0;
		while (node != null) {
			values[i] = node.getValue();
			node = successor(node);

			i++;
		}

		return values;
	}

	/**
	 * public int size()
	 * <p>
	 * Returns the number of nodes in the tree. Returns the size field of root. Runs
	 * in O(1) complexity.
	 */
	public int size() {
		return root.getSize();
	}

	/**
	 * public int getRoot()
	 * <p>
	 * Returns the root AVL node, or null if the tree is empty. Runs in O(1)
	 * complexity.
	 */
	public IAVLNode getRoot() {
		return root;
	}

	/**
	 * public AVLTree[] split(int x)
	 * <p>
	 * splits the tree into 2 trees according to the key x. Returns an array [t1,
	 * t2] with two AVL trees. keys(t1) < x < keys(t2).
	 * <p>
	 * precondition: search(x) != null (i.e. you can also assume that the tree is
	 * not empty) postcondition: none. Runs in O(log(n)) complexity.
	 */

	public AVLTree[] split(int x) {
		IAVLNode curr = search_node(x);
		int currSuccessor = successor(curr) != null ? successor(curr).getKey() : -1;
		int currPredecessor = predecessor(curr) != null ? predecessor(curr).getKey() : -1;
		AVLTree t_less = subTreeMaker(curr.getLeft());
		AVLTree t_more = subTreeMaker(curr.getRight());
		IAVLNode parent = curr.getParent();
		while (parent != null) {
			IAVLNode oldParent = parent.getParent();

			if (parent.getRight() == curr) {
				t_less.join(parent, subTreeMaker(parent.getLeft()));
			} else {
				t_more.join(parent, subTreeMaker(parent.getRight()));
			}
			curr = parent;
			parent = oldParent;
		}
		t_more.max = max;
		t_more.min = currSuccessor != -1 ? t_more.search_node(currSuccessor) : null;
		t_less.max = currPredecessor != -1 ? t_less.search_node(currPredecessor) : null;
		t_less.min = min;

		AVLTree[] result = { t_less, t_more };
		return result;
	}

	/**
	 * private AVLTree subTreeMaker(IAVLNode n)
	 * <p>
	 * Creates a sub-tree with n as its root. Runs in O(1) complexity.
	 */
	private AVLTree subTreeMaker(IAVLNode n) {
		if (!n.isRealNode())
			return new AVLTree();
		n.setParent(null);
		AVLTree t = new AVLTree();
		t.root = n;
		t.max = n;
		t.min = n;
		return t;
	}

	/**
	 * public int join(IAVLNode x, AVLTree t)
	 * <p>
	 * joins t and x with the tree. Returns the complexity of the operation
	 * (|tree.rank - t.rank| + 1).
	 * <p>
	 * precondition: keys(t) < x < keys() or keys(t) > x > keys(). t/tree might be
	 * empty (rank = -1). postcondition: none. Complexity is O(log(n)) .
	 */
	public int join(IAVLNode x, AVLTree t) {
		int height1 = root.getHeight();
		int height2 = t.getRoot().getHeight();

		if (this.empty()) {
			this.root = t.root;
			this.max = t.max;
			this.min = t.min;
			this.insert(x.getKey(), x.getValue());
			return Math.abs(height1 - height2) + 1;
		}
		if (t.empty()) {
			this.insert(x.getKey(), x.getValue());
			return Math.abs(height1 - height2) + 1;
		}

		if (root.getKey() < x.getKey()) {
			this.max = t.max;
			x.setLeft(root);
			x.setRight(t.root);
		} else {
			this.min = t.min;
			x.setLeft(t.root);
			x.setRight(root);
		}
		int difference = x.getLeft().getHeight() - x.getRight().getHeight();
		if (difference < -1) {
			x.getLeft().setParent(x);
			IAVLNode curr = x.getRight();
			while (curr.getHeight() > x.getLeft().getHeight()) {
				curr = curr.getLeft();
			}
			IAVLNode parent = curr.getParent();
			if (parent != null) {
				parent.setLeft(x);
			}
			this.root = x.getRight();
			x.setParent(parent);
			x.setRight(curr);
			curr.setParent(x);
		} else if (difference > 1) {
			IAVLNode curr = x.getLeft();
			x.getRight().setParent(x);
			while (curr.getHeight() > x.getRight().getHeight()) {
				curr = curr.getRight();
			}
			IAVLNode parent = curr.getParent();
			if (parent != null) {
				parent.setRight(x);
			}
			this.root = x.getLeft();
			x.setParent(parent);
			x.setLeft(curr);
			curr.setParent(x);
		} else {
			this.root = x;
			x.getRight().setParent(x);
			x.getLeft().setParent(x);
		}
		root.setParent(null);
		updateHeight(x);
		updateSize(x);
		insert_rebalance(x);

		return Math.abs(height1 - height2) + 1;

	}

	/**
	 * private IAVLNode predecessor(IAVLNode node)
	 * <p>
	 * Returns predecessor of given node. Complexity is O(logn).
	 */
	private IAVLNode predecessor(IAVLNode node) {
		if (node == min) {
			return null;
		}
		if (!node.getLeft().isRealNode()) {
			while (node.getParent().getLeft() == node) {
				node = node.getParent();
			}
			return node.getParent();
		}

		node = node.getLeft();
		while (node.getRight().isRealNode()) {
			node = node.getRight();
		}
		return node;
	}

	/**
	 * private int bf(IAVLNode node)
	 * <p>
	 * Returns bf of the given node. complexity is O(1).
	 */
	private int bf(IAVLNode node) {
		return (node.getLeft().getHeight()) - node.getRight().getHeight();
	}

	/**
	 * private void updateHeight(IAVLNode node)
	 * <p>
	 * updates height of the given node. Complexity is O(1).
	 */

	private void updateHeight(IAVLNode node) {
		node.setHeight(Math.max((node.getRight().getHeight()), node.getLeft().getHeight()) + 1);
	}

	/**
	 * private void updateSize(IAVLNode node)
	 * <p>
	 * updates size of the given node. Complexity is O(1).
	 */

	private void updateSize(IAVLNode node) {

		node.setSize(node.getLeft().getSize() + node.getRight().getSize() + 1);
	}

	/**
	 * public interface IAVLNode ! Do not delete or modify this - otherwise all
	 * tests will fail !
	 */
	public interface IAVLNode {
		public int getKey(); // Returns node's key (for virtual node return -1).

		public String getValue(); // Returns node's value [info], for virtual node returns null.

		public void setLeft(IAVLNode node); // Sets left child.

		public IAVLNode getLeft(); // Returns left child, if there is no left child returns null.

		public void setRight(IAVLNode node); // Sets right child.

		public IAVLNode getRight(); // Returns right child, if there is no right child return null.

		public void setParent(IAVLNode node); // Sets parent.

		public IAVLNode getParent(); // Returns the parent, if there is no parent return null.

		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node.

		public void setHeight(int height); // Sets the height of the node.

		public int getHeight(); // Returns the height of the node (-1 for virtual nodes).

		public void setSize(int size); // Sets the size of the node.

		public int getSize(); // Returns the size of the node (0 for virtual nodes).
	}

	/**
	 * public class IAVLNode
	 * <p>
	 * If you wish to implement classes other than AVLTree (for example IAVLNode),
	 * do it in this file, not in another file.
	 * <p>
	 * This class can and MUST be modified (It must implement IAVLNode).
	 */
	public static class AVLNode implements IAVLNode {
		private int key;
		private String value;
		private int height;
		private IAVLNode parent;
		private IAVLNode left;
		private IAVLNode right;
		public int size;

		public AVLNode() {
			this.key = -1;
			this.value = null;
			this.height = -1;
			this.size = 0;

		}

		public AVLNode(int key, String value) {
			this.key = key;
			this.value = value;
			this.size = 1;
			this.left = new AVLNode();
			this.right = new AVLNode();
			this.right.setParent(this);
			this.left.setParent(this);

		}

		public int getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}

		public void setLeft(IAVLNode node) {
			this.left = node;
		}

		public IAVLNode getLeft() {
			return left;
		}

		public void setRight(IAVLNode node) {
			this.right = node;
		}

		public IAVLNode getRight() {
			return right;
		}

		public void setParent(IAVLNode node) {
			this.parent = node;
		}

		public IAVLNode getParent() {
			return parent;
		}

		public boolean isRealNode() {
			return key != -1;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public int getHeight() {
			return height;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public int getSize() {
			return size;
		}

	}

}
