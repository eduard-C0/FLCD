package fa;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Fa {

    private String fileName;
    private final List<String> states = new ArrayList<>();
    private final List<String> alphabet = new ArrayList<>();
    private String initialState;
    private final List<String> finalStates = new ArrayList<>();
    private final List<Transition> transitions = new ArrayList<>();;

    public Fa(String fileName) {
        this.fileName = fileName;
        readFromFaFile();
    }


    private void readFromFaFile(){
        try{
            File FaFile = new File(fileName);
            Scanner reader = new Scanner(FaFile);
            String type = "";
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                if(data.equals("States")){
                    type = data;
                    continue;
                }
                if(data.equals("Alphabet")){
                    type = data;
                    continue;
                }
                if(data.equals("InitialState")){
                    type = data;
                    continue;
                }
                if(data.equals("FinalState")){
                    type = data;
                    continue;
                }
                if(data.equals("Transitions")){
                    type = data;
                    continue;
                }

                if(type.equals("States")){
                    states.add(data);
                }
                else if (type.equals("Alphabet")){
                    alphabet.add(data);
                }
                else if (type.equals("InitialState")){
                    initialState = data;
                }
                else if(type.equals("FinalState")){
                    finalStates.add(data);
                }
                else if(type.equals("Transitions")){
                    String[] elements =  data.split("\\s+");
                    transitions.add(new Transition(elements[0],elements[1],elements[2]));
                }
            }
            reader.close();
        }catch (IOException error){
            error.printStackTrace();
        }
    }

    public boolean checkSequence(String sequence){
        Deque<List<String>> queue = new LinkedList<>();
        queue.add(Arrays.asList("",initialState));
        while (!queue.isEmpty()){
            List<String> element = queue.remove();
            String currentSequence = element.get(0);
            String currentState = element.get(1);

            if(currentSequence.equals(sequence) && finalStates.contains(currentState)){
                return true;
            }

            transitions.stream().filter(transition -> transition.getPrevious().equals(currentState)).forEach(transition ->
                    {
                        String newCurrentSequnce = currentSequence + transition.getValue();
                        String newCurrentState = transition.getNext();
                        if(sequence.startsWith(newCurrentSequnce)){
                            queue.add(Arrays.asList(newCurrentSequnce,newCurrentState));
                        }
                    }
                    );
        }
        return false;
    }


    public List<String> getStates() {
        return states;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public String getInitialState() {
        return initialState;
    }

    public List<String> getFinalStates() {
        return finalStates;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }
}
