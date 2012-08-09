import java.util.Random;

public class SumArrayTest{
    public static void main(String[] args){
        Random ranGen = new Random();
        Student student = new Student();
        SumArray solution = new SumArray();
        int studentSol, realSol;

        int[] intArray = new int[20];

        for(int i = 0; i < intArray.length; i++){
            intArray[i] = ranGen.nextInt(10);
        }
        
        // Call student method and correct implementation
        studentSol = student.findSum(intArray);
        realSol = solution.findSum(intArray);

        // Print out info
        System.out.println("Your solution: " + studentSol);
        System.out.println("Real solution: " + realSol);

        if(studentSol == realSol){
            System.out.println("Success!");
            System.out.println(args[0]);
        } else {
            System.out.println("Incorrect.  Try again.");
        }
    }
}
