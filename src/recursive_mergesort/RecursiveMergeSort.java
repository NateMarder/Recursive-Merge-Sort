/**
 * @Author Nate Marder
 * @Date 3/22/2014
 * @Description: This program sorts and prints floating point values as read
 * from a text file. The application determines the name of the text file from
 * the input provided by the customer through the keyboard.  The values are
 * sorted using a recursive merge-sort algorithm.
 */

package recursive_mergesort;

//imports...
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class RecursiveMergeSort {

    public static void main(String[] args) throws IOException {
        ArrayList<Double> all = new ArrayList();
        String file_Choice; //used to input user'input_string file choices
        Scanner input = new Scanner(System.in);  //gathers input from user
        String input_string;
        Double nextDouble;
        Boolean yesRUN = true;

        //input user's file selection and validate the user's file choice....
        System.out.print("Please input the file which contains the numbers "
                + "you want to sort\n----> ");
        file_Choice = input.nextLine();
        
        /**
         * Put whatever .txt files you want into the project's files, then 
         * just change the regular expression below... or or just assign your
         * file name as String file_Choice, and get rid of the next while loop
         * all together...
         */
        
        while (!file_Choice.matches("(file1.txt|bad-input-1.txt|"
                + "bad-input-2.txt)")) {
            System.out.print("\nInvalid command-line arguments.\n----> ");
            file_Choice = input.nextLine();
        }        
        
        //read the input file, validates input, terminates if problems are encountered
        Scanner read = new Scanner(new BufferedReader(new FileReader(file_Choice)));
        if (!read.hasNext()){
            System.out.println("Empty file encountered.");yesRUN = false;
        }
        while (read.hasNext()) { //read comp line from the file
            input_string = read.nextLine();
            if (input_string.isEmpty()) {
                System.out.println("Empty line encountered.");
                yesRUN = false;
                break;
            } else if (input_string.matches("(([-]?[.]?[0-9]+)?([-]?[0-9]+[.]?[0-9]*)?([-]?[0-9]+[.]?)?)")){
                nextDouble = Double.valueOf(input_string);
                all.add(nextDouble);
            } else if (!input_string.matches("(([-]?[.]?[0-9]+)?([-]?[0-9]+[.]?[0-9]*)?([-]?[0-9]+[.]?)?)")){
                System.out.println("Invalid line encountered: '" + input_string + "'.");
                yesRUN = false;
                break;
            } else{
                System.out.print("Failed to read input file: ");
                yesRUN = false;
                break;
            }
        }
        read.close();

        //if no errors or empty lines were encountered...
        if (yesRUN) {
            double[] variables = new double[all.size()];
            for (int i = 0; i < all.size(); i++) {//first put values into 'unboxed' double array
                variables[i] = all.get(i);
            }
        mergeSort(variables);//mergeSort them
        }//ends if yesRUN section
    }//ends main argument
    
    //methods overloaded merge-sort (the second method uses recursive algorithm)
    static void mergeSort(double[] values) {
        double[] scratch = new double[values.length];
        int min = 0;
        int max = values.length - 1;
        mergeSort(values, scratch, min, max);
    }
    
    //recursive method is here pseudo-code was from discrete math book
    static void mergeSort(double[] values, double[] scratch, int min, int max) {
        //base-case min==max
        int lhsMax = (min + max) / 2;
        int rhsMin = lhsMax + 1;
        int lhs = min;
        int rhs = rhsMin;
        int dst = min;

        if (min != max) {
            mergeSort(values, scratch, min, lhsMax); //recursive call(s)
            mergeSort(values, scratch, rhsMin, max); //recursive call(s)
            System.arraycopy(values, 0, scratch, 0, values.length);
            while (lhs <= lhsMax && rhs <= max) {
                values[dst++]=(scratch[lhs] < scratch[rhs])?scratch[lhs++]:scratch[rhs++];
            }
            while (lhs <= lhsMax) { values[dst++] = scratch[lhs++]; }
            while (rhs <= max) { values[dst++] = scratch[rhs++]; }
        }
        
        //takes care of printing sorted odd-number variable arrays
        if (values.length % 2 != 0 && dst == values.length && lhs + lhsMax == dst) {
            for (int i = 0; i < values.length; i++) {
                System.out.println(values[i]);
            }
        }
        //takes care of printing sorted even-number variable arrays
        else if (values.length % 2 == 0 && dst == values.length && dst / 2 == lhs) {
            for (int i = 0; i < values.length; i++) {
                System.out.println(values[i]);
            }
        } 
    }//ends recursive merge-sort method
}//end of public class
