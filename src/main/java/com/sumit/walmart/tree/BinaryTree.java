package com.sumit.walmart.tree;

import com.sumit.walmart.Criteria;
import com.sumit.walmart.Subject;
import com.sumit.walmart.domain.Context;
import com.sumit.walmart.listener.Listener;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static java.util.Collections.EMPTY_LIST;

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

    private int getLevel(Node node, int data) {
        return getLevelUtil(node, data, 1);
    }

    /**
     * Calculates result for all registered criteria
     */
    public void traverseForAllCriteria(final Listener listener) {
        log.info("***** Getting result for all criteria *****");
        Node temp = root;
        listener.reset();
        inorder(listener, temp, EMPTY_LIST);
        listener.getCriteriaList().forEach(Criteria::display);
    }

    /**
     * Accept filter request and calculates result who are actually interested
     *
     * @param listedCriteria
     */
    public void traverseForFilteredCriteria(Listener listener, List<String> listedCriteria) {
        log.info("***** Getting result for all filtered criteria *****");
        log.info("Filtered criteria : {}", listedCriteria);
        Node temp = root;
        listener.reset();
        List<String> filterDataList = listedCriteria != null ? listedCriteria : EMPTY_LIST;
        inorder(listener, temp, filterDataList);
        listener.getCriteriaList().stream().filter(criteria -> filterDataList.contains(criteria.getId())).forEach(Criteria::display);
    }

    private void inorder(Listener listener, Node temp, List<String> listedCriteria) {
        if (temp == null) {
            return;
        }
        inorder(listener, temp.getLeft(), listedCriteria);
        log.debug("Item : {}, Level : {}", temp.getData(), getLevel(root, temp.getData()));
        Subject subject = Subject.builder().number(temp.getData()).level(getLevel(root, temp.getData())).build();
        Context context = Context.builder().filteredCriteria(listedCriteria).subject(subject).build();

        listener.notify(context);
        inorder(listener, temp.getRight(), listedCriteria);
    }

}
