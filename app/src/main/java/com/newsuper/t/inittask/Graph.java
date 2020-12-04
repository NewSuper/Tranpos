package com.newsuper.t.inittask;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Vector;

/**
 * 有向无环图的拓扑排序算法
 */
public class Graph {
    //顶点数
    private int mVerticeCount;
    //邻接表
    private List<Integer>[] mAdj;

    public Graph(int count) {
        this.mVerticeCount = count;
        mAdj = new ArrayList[mVerticeCount];
        for (int i = 0; i < mVerticeCount; i++) {
            mAdj[i] = new ArrayList<>();
        }
    }

    //添加边
    public void addEdge(int u, int v) {
        mAdj[u].add(v);
    }

    //拓扑排序
    public Vector<Integer> topologicalSort() {
        int indegree[] = new int[mVerticeCount];
        //初始化所有点的入度数量
        for (int i = 0; i < mVerticeCount; i++) {
            ArrayList<Integer> temp = (ArrayList<Integer>) mAdj[i];
            for (int node : temp) {
                indegree[node]++;
            }
        }
        Queue<Integer> queue = new LinkedList<>();
        //找出所有入度为0 的点
        for (int i = 0; i < mVerticeCount; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }
        int cnt = 0;
        Vector<Integer> topOrder = new Vector<>();
        while (!queue.isEmpty()) {
            int u = queue.poll();
            topOrder.add(u);
            //找到该点（入度为0）的所有邻接点
            for (int node : mAdj[u]) {
                //把这个点的入度减减，如果入度变成了0，那么添加到入度0的队列里
                if (--indegree[node] == 0) {
                    queue.add(node);
                }
            }
            cnt++;
        }
        if (cnt != mVerticeCount) {
            throw new IllegalStateException("Exists a cycle in the graph");
        }
        return topOrder;
    }
}
