package fa;

public class Transition {

    private String previous;
    private String value;
    private String next;

    public Transition(String previous, String value, String next) {
        this.previous = previous;
        this.value = value;
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Transition: " + previous + "---->" + value + "----->" + next + '\n';
    }
}
