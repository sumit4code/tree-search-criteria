package com.sumit.walmart;

import com.sumit.walmart.criteria.ElementDividedBySevenImpl;
import com.sumit.walmart.criteria.EventNumberImpl;
import com.sumit.walmart.criteria.LevelTwoCountImpl;
import com.sumit.walmart.criteria.OddNumberImpl;
import com.sumit.walmart.listener.Listener;
import com.sumit.walmart.service.YellingService;
import com.sumit.walmart.tree.BinaryTree;

import java.util.Arrays;

import static com.sumit.walmart.helper.RandomNumberGenerator.randomNumber;

public class Application {

    public static void main(String[] args) {
        Listener listener = new Listener();
        listener.addCriteria(new EventNumberImpl());
        listener.addCriteria(new LevelTwoCountImpl());
        listener.addCriteria(new OddNumberImpl());
        listener.addCriteria(new ElementDividedBySevenImpl());
        YellingService yellingService = new YellingService(listener);

        BinaryTree binaryTree = new BinaryTree();
        for (int i = 0; i < 10000; i++) {
            binaryTree.insert(randomNumber(1, 100));
        }
        binaryTree.insert(49);
        yellingService.traverseForAllCriteria(binaryTree);

        yellingService.traverseForFilteredCriteria(binaryTree, Arrays.asList(EventNumberImpl.IDENTIFIER, LevelTwoCountImpl.IDENTIFIER, OddNumberImpl.IDENTIFIER));
        yellingService.stopYelling();
    }
}
