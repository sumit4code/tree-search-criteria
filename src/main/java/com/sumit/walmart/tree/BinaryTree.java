package com.sumit.walmart.tree;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;

@Slf4j
public class BinaryTree {

    private Node root;

    public BinaryTree() {
        this.root = null;
    }

    public void insert(int key) {
        if (root == null) {
            root = new Node(key);
            return;
        }
        Node temp = root;
        Queue<Node> q = new LinkedList<>();
        q.add(temp);

        while (!q.isEmpty()) {
            temp = q.peek();
            q.remove();

            if (temp.getLeft() == null) {
                temp.setLeft(new Node(key));
                break;
            } else {
                q.add(temp.getLeft());
            }

            if (temp.getRight() == null) {
                temp.setRight(new Node(key));
                break;
            } else {
                q.add(temp.getRight());
            }
        }
    }


    private int getLevelUtil(Node node, int data, int level) {
        if (node == null) {
            return 0;
        }
        if (node.getData() == data) {
            return level;
        }
        int downLevel = getLevelUtil(node.getLeft(), data, level + 1);
        if (downLevel != 0) {
            return downLevel;
        }
        downLevel = getLevelUtil(node.getRight(), data, level + 1);
        return downLevel;
    }

    public int getLevel(Node node, int data) {
        return getLevelUtil(node, data, 1);
    }


    public Node getRoot() {
        return root;
    }
}
