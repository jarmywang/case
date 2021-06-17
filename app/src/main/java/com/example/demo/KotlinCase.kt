package com.example.demo

import java.util.*

fun main () {
    val list = arrayListOf("1",1,{})
    list[0] = "2"
    list.add(2)
    val list2 = mutableListOf("1",1,{})
    list2[0] = "2"
    list2.add(2)

    println(list)
    println(list2)

    println("coinCountMin= ${coinChange(intArrayOf(1, 2, 5), 11)}")

    val arr = mutableListOf(5,4,8,9,2,3,6,1,7)
    println(selectSort(arr))
    println(bubbleSort(arr))
}

fun coinChange(coins: IntArray, amount: Int): Int {
    val dp = IntArray(amount + 1)
    Arrays.fill(dp, amount + 1)
    dp[0] = 0
    for (i in 1..amount) for (j in coins.indices) {
        if (coins[j] <= i) dp[i] = dp[i].coerceAtMost(dp[i - coins[j]] + 1)
    }
    return if (dp[amount] > amount) -1 else dp[amount]
}

// 1、时间复杂度：O(n2) 2、空间复杂度：O(1) 3、非稳定排序 4、原地排序
fun selectSort(arr: MutableList<Int>): MutableList<Int> {
    for (i in arr.indices) {
        var min = i
        for (j in i + 1 until arr.size) {
            if (arr[min] > arr[j]) min = j
        }
        arr[i] = arr[min].also { arr[min] = arr[i] }
    }
    return arr
}

// 1、时间复杂度：O(n2) 2、空间复杂度：O(1) 3、稳定排序 4、原地排序
fun bubbleSort(arr: MutableList<Int>): MutableList<Int> {
    for (i in arr.indices) for (j in 0 until arr.size - i - 1)
        if (arr[j] > arr[j + 1])
            arr[j] = arr[j + 1].also { arr[j + 1] = arr[j] }
    return arr
}