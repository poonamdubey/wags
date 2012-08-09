public class ReverseSolution{
    public String[] reverseArray(String[] words){
        int len = words.length;

        for(int i = 0; i < len / 2; i++){
            String temp = words[i];
            words[i] = words[len - 1 - i];
            words[len - 1 - i] = temp;
        }

        return words;
    }
}
