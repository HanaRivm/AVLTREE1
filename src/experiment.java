import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class experiment {
    private AVLTree t = new AVLTree();
    private int[] arr;
    private int index;

    public experiment(int length) {
        arr = new int[length];
    }


    private int swaps(int key) {
        int count = 0;
        int i = index - 1;
        while (i - 1 >= 0 && arr[i - 1] > key) {
            arr[i] = arr[i - 1];
            arr[i - 1] = key;
            i = i - 1;
            count++;
        }
        return count;
    }

//    private int[] insert_new(int k, String v) {
//        int path_cnt = 0;
//        int[] result = {path_cnt, 0};
//        if (t.max == null) {
//            t.insert(k, v);
//            arr[index] = k;
//            index++;
//            return result;
//        }
//        AVLTree.IAVLNode node = t.max;
//        while (node.getParent() != null && node.getParent().getKey() > k) {
//            node = node.getParent();
//            path_cnt++;
//        }
//        while (node != null) {
//            if (node.getKey() < k) {
//                node = node.getRight();
//            } else {
//                node = node.getLeft();
//            }
//            path_cnt++;
//        }
//        t.insert(k, v);
//        arr[index] = k;
//        index++;
//        result[0] = path_cnt;
//        result[1] = swaps(k);
//        return result;
//    }


    //        private int search_from_max (AVLNode nMax,int k){
//            AVLNode parent = (AVLNode) nMax.getParent();
//            if (parent != null) {
//                if (k > nMax.getKey() || parent.getKey() < k) {
//                    return 1;
//                }
//                if (parent > k) {
//                    return
//                }
//            } else {
//                AVLNode parent = (AVLNode) nMax.getParent();
//                if (parent.getKey())
//            }
//        }
    public static AVLTree[] split(int x, AVLTree t) {
        AVLTree.IAVLNode curr = t.search_node(x);
        int maxJoin = 0;
        int numJoins = 0;
        int sumJoins = 0;
        AVLTree t_less = t.subTreeMaker(curr.getLeft());
        AVLTree t_more = t.subTreeMaker(curr.getRight());

        AVLTree.IAVLNode parent = curr.getParent();
        while (parent != null) {
            int join = 0;
            if (parent.getRight() == curr) {
                join=t_less.join(parent, t.subTreeMaker(parent.getLeft()));
            } else {
                join=t_more.join(parent, t.subTreeMaker(parent.getRight()));
            }
            numJoins++;
            sumJoins +=join;
            maxJoin = Math.max(maxJoin,join);
            curr = parent;
            parent = parent.getParent();
        }
        System.out.println("Average join "+ (sumJoins/numJoins));
        System.out.println("Max join "+maxJoin);

//        t_more.max = max;
//        t_more.min = successor(curr);
//        t_less.max = predecessor(curr);
//        t_less.min = min;
        AVLTree[] result = { t_less, t_more };
        return result;
    }

    public static void main(String[] args) {
      //  experiment t = new experiment(8000);
        AVLTree t2=new AVLTree();
        int swap = 0;
        int path = 0;
        Random rand = new Random();
        int i = 0;
        while (i<512000) {
        	int value = rand.nextInt(100000000);
        	if (t2.insert(value,String.valueOf(i))!=-1)
        		i++;
        }
    //        int[] s_1 = t2.insert_new(value, String.valueOf(i));
//            System.out.println(value);
//            swap = swap + s_1[1];
//            path = path + s_1[0];
        
        int index = rand.nextInt(512000);
        int [] keys= t2.keysToArray();
        int randomkey = keys[index];
        System.out.println("random key");
        split(keys[index],t2);
        System.out.println("maxKeyof left");
        int k = t2.predecessor(t2.getRoot()).getKey();
        System.out.println(String.valueOf(k));
;       split(k,t2);
    }

}
