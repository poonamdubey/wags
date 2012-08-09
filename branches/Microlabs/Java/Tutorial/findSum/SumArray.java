public class SumArray{
    public int findSum(int[] intArray){
        int sum = 0;

        for(int i = 0; i < intArray.length; i++){
            sum += intArray[i];
        }

        return sum;
    }
}
