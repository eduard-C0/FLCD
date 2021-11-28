package grammar;

import java.util.List;

public class Production {
    private List<String> right;
    private List<String> left;

    public Production(List<String> left, List<String> right) {
        this.right = right;
        this.left = left;
    }

    public List<String> getRight() {
        return right;
    }

    public void setRight(List<String> right) {
        this.right = right;
    }

    public List<String> getLeft() {
        return left;
    }

    public void setLeft(List<String> left) {
        this.left = left;
    }

    @Override
    public String toString() {
        return left.toString() + "->"+ right.toString();
    }
}
