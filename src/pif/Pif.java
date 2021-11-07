package pif;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Pif {

    private final List<PifEntry> entries;

    public Pif() {
        this.entries = new ArrayList<>();
    }

    public void writeToPifFile(){
        String fileName = "Pif.out";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            entries.forEach(pifEntry -> {
                try {
                    writer.write(pifEntry.toString() + '\n');
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.close();
        }catch (IOException error){
            error.printStackTrace();
        }
    }

    public void addElement(String token, int position){
        entries.add(new PifEntry(token,position));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        entries.forEach(pifEntry -> stringBuilder.append(pifEntry.toString()).append('\n'));
        return stringBuilder.toString();
    }
}
