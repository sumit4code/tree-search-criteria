package com.sumit.walmart;

import com.sumit.walmart.criteria.ElementDividedBySevenImpl;
import com.sumit.walmart.criteria.EventNumberImpl;
import com.sumit.walmart.criteria.LevelTwoCountImpl;
import com.sumit.walmart.criteria.OddNumberImpl;
import com.sumit.walmart.observer.Observer;
import com.sumit.walmart.tree.BinaryTree;

import java.util.Arrays;

import static com.sumit.walmart.helper.RandomNumberGenerator.randomNumber;

public class Application {

    public static void main(String[] args) {
        Observer observer = new Observer();
        observer.addCriteria(new EventNumberImpl());
        observer.addCriteria(new LevelTwoCountImpl());
        observer.addCriteria(new OddNumberImpl());
        observer.addCriteria(new ElementDividedBySevenImpl());

        BinaryTree binaryTree = new BinaryTree(observer);
        for (int i = 0; i < 10; i++) {
            binaryTree.insert(randomNumber(1, 100));
        }
        binaryTree.insert(49);

        binaryTree.traverseForAllCriteria();

        binaryTree.traverseForFilteredCriteria(Arrays.asList(EventNumberImpl.IDENTIFIER, LevelTwoCountImpl.IDENTIFIER));

    }
}
