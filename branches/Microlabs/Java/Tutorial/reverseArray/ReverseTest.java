import java.util.Arrays;

public class ReverseTest{
    
    public static void main(String[] args){
        boolean correct = true;
        String[] words = {"can", "you", "reverse", "this", "array"};

        // the if(correct) basically means if you haven't been wrong yet
        if(correct) correct = printInfo(words);

        String[] newWords = {"how", "about", "an", "even", "number", "array"};
        System.out.println("===============================\n");

        if(correct) correct = printInfo(newWords);
        
        if(correct) System.out.println("Success!\n" + args[0]);
        else System.out.println("Incorrect...");
    }

    public static boolean printInfo(String[] words){
        ReverseSolution solution = new ReverseSolution();
        Student student = new Student();

        /*
         *  DISPLAYING INFO
         */

        // Print the original array
        System.out.println("Original Array:");
        for(int i = 0; i < words.length; i++){
            System.out.print(words[i] + " ");    
        }
        System.out.println("\n");

        // Reverse the array correctly
        String[] reversedArray = solution.reverseArray(words.clone());
        
        // Print the reversed array
        System.out.println("Reversed Array:");
        for(int i = 0; i < reversedArray.length; i++){
            System.out.print(reversedArray[i] + " ");    
        }
        System.out.println("\n");

        // Reverse the array with students method
        String[] studentReverse = student.reverseArray(words.clone());

        // Print out the student's reversed array
        System.out.println("Your Reversed Array:");
        for(int i = 0; i < studentReverse.length; i++){
            System.out.print(studentReverse[i] + " ");    
        }
        System.out.println("\n");

        boolean correct = Arrays.equals(reversedArray, studentReverse);

        return correct;
    }

}
