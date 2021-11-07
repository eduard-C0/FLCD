package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * SymbolTable represented as a alphabetically BinarySearchTree
 */
public class SymbolTable {

    private Node root;
    private String fileName = "SymbolTable.out";

    /**
     * This method returns a position of a token or if the token doesn't exist in the symbol table
     * that token will be added and the position will be returned.
     * @param key which is the token
     * @return position of the token
     */
    public int addAndReturnPosition(String key){
        int position = search(key);
        if (position == -1)
        {
            add(key,-1);
            nodesInAsscendingOrder();
            return search(key);
        }
        return position;
    }

    /**
     * This method adds a token to the symbol table.
     * @param key the token we want to add
     * @param value the position of the token
     */
    private void add(String key, int value) {
        Node nodeToAdd = new Node(key,value);

        if (root == null) {
            root = nodeToAdd;
            return;
        }
        Node previousNode = null;
        Node currentNode = root;

        while (currentNode != null) {
            if (currentNode.key.compareTo(nodeToAdd.key) > 0) {
                previousNode = currentNode;
                currentNode = currentNode.leftChild;
            } else if (currentNode.key.compareTo(nodeToAdd.key) < 0) {
                previousNode = currentNode;
                currentNode = currentNode.rightChild;
            }
        }
        if (previousNode.key.compareTo(nodeToAdd.key) > 0) {
            previousNode.leftChild = nodeToAdd;
        } else {
            previousNode.rightChild = nodeToAdd;
        }
    }

    /**
     * This method searches for a token in the symbol table.
     *
     * @param key the token for which we are looking for
     * @return the position of the token
     */
    private int search(String key) {
        Node currentNode = root;
        while (currentNode != null){
            if(currentNode.key.compareTo(key) > 0){
                currentNode = currentNode.leftChild;
            }
            else if (currentNode.key.compareTo(key) < 0){
                currentNode = currentNode.rightChild;
            }
            else {
                return currentNode.valueOfNode;
            }
        }
        return -1;
    }

    /**
     * This method goes through the symbol table represented as a binary search tree like this (Left, Root, Right)
     *
     * @param currentNode starting node from where we want to start the inorder traversal
     * @param queue an empty queue for storing the order in which the nodes were visited
     */
    private void inorderTraversal(Node currentNode, Queue<Node> queue){
        if (currentNode == null)
            return;
        inorderTraversal(currentNode.leftChild,queue);
        queue.add(currentNode);
        inorderTraversal(currentNode.rightChild,queue);
    }

    /**
     * This method reorders the tokens alphabetically
     */
    private void nodesInAsscendingOrder() {
        Queue<Node> queue = new LinkedList<Node>();
        inorderTraversal(this.root, queue);
        int index = 0;
        while (!queue.isEmpty()) {
            Node currentNode = queue.remove();
            currentNode.valueOfNode = index;
            index += 1;
        }
    }

    public void writeToFile(){
        Queue<Node> queue = new LinkedList<Node>();
        inorderTraversal(this.root,queue);
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            while (!queue.isEmpty()){
                Node currentNode = queue.remove();
                writer.write(currentNode.key + ":" + currentNode.valueOfNode + '\n');
            }
            writer.close();
        }catch (IOException error){
            error.printStackTrace();
        }
    }

}
