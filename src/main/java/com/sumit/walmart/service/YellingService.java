package com.sumit.walmart.service;

import com.sumit.walmart.Criteria;
import com.sumit.walmart.Subject;
import com.sumit.walmart.domain.Context;
import com.sumit.walmart.listener.Listener;
import com.sumit.walmart.tree.BinaryTree;
import com.sumit.walmart.tree.Node;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static java.util.Collections.EMPTY_LIST;

@Slf4j
public class YellingService {

    private final Listener listener;

    public YellingService(Listener listener) {
        this.listener = listener;
    }

    /**
     * Calculates result for all registered criteria
     */
    public void traverseForAllCriteria(BinaryTree binaryTree) {
        log.info("***** Getting result for all criteria *****");
        Node temp = binaryTree.getRoot();
        listener.reset();
        inorder(binaryTree, temp, EMPTY_LIST);
        listener.getCriteriaList().forEach(Criteria::display);
    }

    /**
     * Accept filter request and calculates result who are actually interested
     *
     * @param  binaryTree
     * @param listedCriteria
     */
    public void traverseForFilteredCriteria(BinaryTree binaryTree, List<String> listedCriteria) {
        log.info("***** Getting result for all filtered criteria *****");
        log.info("Filtered criteria : {}", listedCriteria);
        Node temp = binaryTree.getRoot();
        listener.reset();
        List<String> filterDataList = listedCriteria != null ? listedCriteria : EMPTY_LIST;
        inorder(binaryTree, temp, filterDataList);
        listener.getCriteriaList().stream().filter(criteria -> filterDataList.contains(criteria.getId())).forEach(Criteria::display);
    }

    private void inorder(BinaryTree binaryTree, Node temp, List<String> listedCriteria) {
        if (temp == null) {
            return;
        }
        inorder(binaryTree, temp.getLeft(), listedCriteria);
        int level = binaryTree.getLevel(binaryTree.getRoot(), temp.getData());
        log.debug("Item : {}, Level : {}", temp.getData(), level);
        Subject subject = Subject.builder().number(temp.getData()).level(level).build();
        Context context = Context.builder().filteredCriteria(listedCriteria).subject(subject).build();

        listener.notify(context);
        inorder(binaryTree, temp.getRight(), listedCriteria);
    }
}
