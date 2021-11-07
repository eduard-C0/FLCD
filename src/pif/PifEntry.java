package pif;

public class PifEntry {
    private String token;
    private int position;

    public PifEntry(String token, int position) {
        this.token = token;
        this.position = position;
    }

    @Override
    public String toString() {
        return  token + ':'+ position ;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
