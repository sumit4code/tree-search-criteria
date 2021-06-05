package com.sumit.walmart.service;

import com.sumit.walmart.Criteria;
import com.sumit.walmart.Subject;
import com.sumit.walmart.domain.Context;
import com.sumit.walmart.listener.Listener;
import com.sumit.walmart.tree.BinaryTree;
import com.sumit.walmart.tree.Node;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.Collections.EMPTY_LIST;

@Slf4j
public class YellingService {

    private final Listener listener;
    private final ExecutorService executorService;

    public YellingService(Listener listener) {
        this.listener = listener;
        this.executorService = Executors.newFixedThreadPool(5);
    }

    /**
     * Calculates result for all registered criteria
     */
    public void traverseForAllCriteria(BinaryTree binaryTree) {
        log.info("***** Getting result for all criteria *****");
        Node temp = binaryTree.getRoot();
        listener.reset();
        calculateAndWait(binaryTree, temp, EMPTY_LIST);
        listener.getCriteriaList().parallelStream().forEach(Criteria::display);
    }

    /**
     * Accept filter request and calculates result who are actually interested
     *
     * @param binaryTree
     * @param listedCriteria
     */
    public void traverseForFilteredCriteria(BinaryTree binaryTree, List<String> listedCriteria) {
        log.info("***** Getting result for all filtered criteria *****");
        log.info("Filtered criteria : {}", listedCriteria);
        Node temp = binaryTree.getRoot();
        listener.reset();
        List<String> filterDataList = listedCriteria != null ? listedCriteria : EMPTY_LIST;
        calculateAndWait(binaryTree, temp, filterDataList);
        listener.getCriteriaList().parallelStream().filter(criteria -> filterDataList.contains(criteria.getId())).forEach(Criteria::display);
    }

    private void calculateAndWait(BinaryTree binaryTree, Node temp, List<String> filterDataList) {
        CountDownLatch countDownLatch = new CountDownLatch(binaryTree.size());
        inorder(binaryTree, temp, filterDataList, countDownLatch);
        try {
            countDownLatch.await();
        } catch (InterruptedException exception) {
            log.error("Error occurred", exception);
        }
    }

    private void inorder(BinaryTree binaryTree, Node temp, List<String> listedCriteria, CountDownLatch countDownLatch) {
        if (temp == null) {
            return;
        }
        inorder(binaryTree, temp.getLeft(), listedCriteria, countDownLatch);
        int level = binaryTree.getLevel(binaryTree.getRoot(), temp.getData());
        //   log.debug("Item : {}, Level : {}", temp.getData(), level);
        Subject subject = Subject.builder().number(temp.getData()).level(level).build();
        Context context = Context.builder().filteredCriteria(listedCriteria).subject(subject).build();

        executorService.execute(new WorkingThread(countDownLatch, listener, context));
        inorder(binaryTree, temp.getRight(), listedCriteria, countDownLatch);
    }

    public static class WorkingThread implements Runnable {
        private final CountDownLatch countDownLatch;
        private final Listener listener;
        private final Context context;

        public WorkingThread(CountDownLatch countDownLatch, Listener listener, Context context) {
            this.countDownLatch = countDownLatch;
            this.listener = listener;
            this.context = context;
        }

        @Override
        public void run() {
            listener.notify(context);
            countDownLatch.countDown();
        }
    }

    public void stopYelling(){
        this.executorService.shutdown();
    }
}
