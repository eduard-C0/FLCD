package model;

public class Node {
    String key;
    int valueOfNode;
    Node leftChild;
    Node rightChild;

    public Node(String key, int valueOfNode) {
        this.key = key;
        this.valueOfNode = valueOfNode;
        this.leftChild = null;
        this.rightChild = null;
    }

    @Override
    public String toString() {
        return "Node{" +
                "key='" + key + " }";
    }
}
