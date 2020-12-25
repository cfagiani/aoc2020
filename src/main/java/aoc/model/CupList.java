package aoc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Models a ring of cups as a circular linked list. We keep a backing map of node values to nodes to make it easier
 * to locate specific values without having to traverse the whole list.
 * Used in Day 23.
 */
public class CupList {

    private ListNode curNode;
    private Map<Integer, ListNode> nodeDict;
    private Integer maxVal = 0;
    private Integer minVal = Integer.MAX_VALUE;

    /**
     * Initializes the cup list with the cups parsed from the valueString. The valueString is assumed to be a list
     * of single digit cup values.
     *
     * @param valueString
     */
    public CupList(String valueString) {
        this(valueString, valueString.length());
    }

    /**
     * Initializes the cup list with the value string followed by consecutively increasing cup values until the total
     * size of the list is total.
     *
     * @param valueString
     * @param total
     */
    public CupList(String valueString, int total) {
        ListNode head = null;
        ListNode node = null;
        nodeDict = new HashMap<>();
        long count = 0;
        for (char n : valueString.toCharArray()) {
            node = addNode(Integer.parseInt(n + ""));
            if (head == null) {
                head = node;
            }
            curNode = node;
            count++;
        }
        // now keep adding nodes until we reach the total
        int lastVal = maxVal;
        while (count < total) {
            node = addNode(lastVal + 1);
            lastVal++;
            count++;
        }
        // now node points to the last element so make it's "next" point to the head
        node.next = head;
        curNode = head;
    }

    /**
     * Adds a node and makes curNode point to the newly added node. This also tracks the min/max values for the entire
     * list and updates if needed.
     *
     * @param val
     * @return
     */
    private ListNode addNode(int val) {
        ListNode node = new ListNode(val);
        if (node.value > maxVal) {
            maxVal = node.value;
        }
        if (node.value < minVal) {
            minVal = node.value;
        }
        if (curNode != null) {
            curNode.next = node;
        }
        curNode = node;
        nodeDict.put(node.value, node);
        return node;
    }

    /**
     * Removes count nodes starting from the current cup.
     *
     * @param count
     * @return
     */
    public List<Integer> remove(int count) {
        List<Integer> removedValues = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            removedValues.add(curNode.next.value);
            curNode.next = curNode.next.next;

        }
        return removedValues;
    }

    /**
     * Returns the value of the current cup.
     *
     * @return
     */
    public Integer getCurrentValue() {
        return curNode.value;
    }

    /**
     * Inserts cups with the values contained in the cups list after the cup with the value passed in.
     *
     * @param val
     * @param cups
     */
    public void insertAfterValue(Integer val, List<Integer> cups) {
        if (val < minVal) {
            val = maxVal;
        }
        while (cups.contains(val)) {
            val--;
            if (val < minVal) {
                val = maxVal;
            }
        }
        ListNode destination = nodeDict.get(val);

        for (Integer cVal : cups) {
            ListNode n = new ListNode(cVal);
            n.next = destination.next;
            destination.next = n;
            destination = n;
            nodeDict.put(n.value, n);
        }
    }

    /**
     * Prints all nodes after the cup labeled with the value passed in.
     *
     * @param val
     */
    public void printStartingFrom(Integer val) {
        ListNode node = nodeDict.get(val).next;
        // loop around again and print this time
        while (!node.value.equals(val)) {
            System.out.print(node.value);
            node = node.next;
        }
    }

    /**
     * Returns the product of count cup values that follow the cup labeled with the value passed in.
     *
     * @param val
     * @param count
     * @return
     */
    public Long getProductStartingAfter(Integer val, int count) {
        long product = 1L;
        ListNode temp = nodeDict.get(1);
        for (int i = 0; i < count; i++) {
            temp = temp.next;
            product *= temp.value;
        }
        return product;
    }

    /**
     * advances the current node to the following node
     */
    public void advanceCurrent() {
        curNode = curNode.next;
    }


    /**
     * Internal structure to hold a cup's value and pointer to the next cup in the linked list.
     */
    private class ListNode {
        private ListNode next;
        private Integer value;

        public ListNode(Integer val) {
            this.value = val;
        }
    }

}


