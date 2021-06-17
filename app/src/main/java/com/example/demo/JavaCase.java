package com.example.demo;

import android.os.Build;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class JavaCase {

    public static void main(String[] args) {
        case4();
    }

    public static void case0() {
        System.out.println("===");
    }

    private static final Object LOCK = new Object();
    private static int current = 1;
    public static void Print1to100() {
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            new Thread(() -> {
                while (true) {
                    synchronized (LOCK) {
                        while (current % 3 != finalI) {
                            if (current > 100) {
                                break;
                            }
                            try {
                                LOCK.wait();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (current > 100) {
                            break;
                        }
                        System.out.println(current + "");
                        current++;
                        LOCK.notifyAll();
                    }
                }
            }).start();
        }

    }

    public static void case4() {
        System.out.println("===");
        System.out.println("mouseCount:15="+mouseCount(5,5));
        System.out.println("findSame:"+findSame("abc", new String[]{"aaaxxb","xbc","mla"}));
    }

    public static void case3() {
        System.out.println("===");
        System.out.println("coinChange:"+coinChange(new int[]{1, 2, 5}, 11));
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("a", "1");
        hashMap.get("a");
        System.out.println("fab12="+fab(12));
//        PriorityQueue
        ExecutorService executor = new ThreadPoolExecutor(1,1,
                1, TimeUnit.DAYS, new ArrayBlockingQueue<>(1));
        ExecutorService executors = Executors.newSingleThreadExecutor();
        Executors.newCachedThreadPool();
        Executors.newFixedThreadPool(1);
        Executors.newScheduledThreadPool(1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // 24
            Executors.newWorkStealingPool();
        }
    }

    static int mouseCount(int n, int x) {
        int[][] dp = new int[n + 1][6];
        dp[0][0] = x;
        for (int i = 1; i < 6; i++) {
            dp[0][i] = 0;
        }
        for (int i = 1; i < n + 1; i++) {
            for (int j = 0; j < 6; j++) {
                if (j == 0) {
                    dp[i][j] = 2 * dp[i-1][1] + 2 * dp[i-1][3];
                }
                if (j == 1) {
                    dp[i][j] = dp[i-1][0];
                }
                if (j == 2) {
                    dp[i][j] = dp[i-1][1];
                }
                if (j == 3) {
                    dp[i][j] = dp[i-1][2] - 1;
                }
                if (j == 4) {
                    dp[i][j] = dp[i-1][3];
                }
                if (j == 5) {
                    dp[i][j] = 0;
                }
            }
        }
        return dp[n][0] + dp[n][1] + dp[n][2] + dp[n][3] + dp[n][4] + dp[n][5];
    }

    static String findSame(String s, String[] words) {
        int[] sameCounts = new int[words.length];
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            sameCounts[i] = 0;
            for (char w: word.toCharArray()) {
                if(s.indexOf(w) > -1) sameCounts[i]++;
            }
        }
        int r = 0;
        int index = 0;
        for (int j = 0; j < sameCounts.length; j++) {
            if (sameCounts[j] > r) {
                r = sameCounts[j];
                index = j;
            }
        }
        return words[index];
    }

    private static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int i = 0; i < amount + 1; i++) {
            for (int coin : coins) {
                if (i >= coin) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    public static class ListNode {
         int val;
         ListNode next;
         ListNode(int x) { val = x; }
    }
    private static ListNode reverseList(ListNode head) {
        ListNode temp;
        ListNode cur = head;
        ListNode pre = null;
        while (cur != null) {
            temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;
    }

    static ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode ln1 = head;
        ListNode ln2 = head.next;
        ln1.next = swapPairs(ln2.next);
        ln2.next = ln1;
        return ln2;
    }

    private static int fab(int c) {
        int[] dp = new int[c+1];
        for (int i = 0; i < c + 1; i++) {
            dp[i] = i<3?i:dp[i-1]+dp[i-2];
            System.out.print(dp[i]+", ");
        }
        return dp[c];
    }

    public static void case2() {
        System.out.println("==="+(1 << 2));
        int[] a = {49, 38, 65, 97, 76, 13, 27, 49};
        quickSort(a, 0, a.length - 1);
        System.out.println(Arrays.toString(a));
    }

    private static void quickSort(int[] a, int l, int r) {
        if (l >= r) return;
        int i = l, j = r, x = a[l];
        while (i < j) {
            while(i < j && x <= a[j]) j--;
            if (i < j) a[i++] = a[j];
            while(i < j && a[i] < x) i++;
            if (i < j) a[j--] = a[i];
        }
        a[i] = x;
        quickSort(a, l, i - 1);
        quickSort(a, i + 1, r);
    }

    public static void case1() {
        System.out.println("===");
        ReentrantLock reentrantLock = new ReentrantLock();
        Thread.yield();

        atomicInteger.incrementAndGet(); //执行自增1
        System.out.println(atomicInteger.toString());
    }

    // ------------------------- 悲观锁的调用方式 -------------------------
// synchronized
    public synchronized void testMethod() {
        // 操作同步资源
    }

    // ReentrantLock
    private ReentrantLock lock = new ReentrantLock(); // 需要保证多个线程使用的是同一个锁

    public void modifyPublicResources() {
        lock.lock();
        // 操作同步资源
        lock.unlock();
    }

    // ------------------------- 乐观锁的调用方式 -------------------------
    private static AtomicInteger atomicInteger = new AtomicInteger();  // 需要保证多个线程使用的是同一个AtomicInteger

}
