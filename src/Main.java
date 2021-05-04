import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    /**
     * Prints a message according to a given grade.
     *
     * It is guaranteed that the grade is within the range [0, 100].
     *
     * @param grade The grade
     */
    public static void gradeMessage(int grade) {
        // get second decimal
        int tens = grade / 10;
        // print message
        switch(tens)
        {
            case 10:
                System.out.println("Excellent");
                break;
            case 9:
                System.out.println("Great");
                break;
            case 8:
                System.out.println("Very good");
                break;
            case 7:
                System.out.println("Good");
                break;
            default:
                System.out.println("OK");
                break;
        }
    }

    /**
     * Compresses a given string.
     *
     * The compression process is done by replacing a sequence of identical consecutive characters
     * with that same character followed by the length of sequence.
     *
     * It is guaranteed that the string contains only letters (lowercase and uppercase).
     *
     * @param stringToCompress The string to compress
     * @return The compressed version of the string
     */
    public static String compressString(String stringToCompress) {
        String compressedString = "";
        StringBuilder compressedStringHelp= new StringBuilder();
        int countConsecutive= 0;
        for (int i= 0; i< stringToCompress.length(); i++)
        {
            countConsecutive++;
            // If next character is different than current append this char to result
            if (i+ 1 >= stringToCompress.length()
                    || stringToCompress.charAt(i) != stringToCompress.charAt(i+ 1))
            {
                compressedStringHelp.append(stringToCompress.charAt(i));
                compressedStringHelp.append(countConsecutive);
                countConsecutive= 0;
            }
        }
        compressedString = compressedStringHelp.toString();
        return compressedString;
    }

    /**
     * @param c the char to check if is a number
     * @return true if char is a number or false otherwise
     */
    public static boolean isNumber(char c)
    {
        if(c >= '0' && c <= '9')
            return true;
        else
            return false;
    }
    /**
     * Decompresses a given string.
     *
     * The decompression process is done by duplicating each sequence of characters
     * according to the number which appears after the sequence.
     *
     * It is guaranteed that the string is a legal compressed string.
     *
     * @param compressedString The string to decompress
     * @return The decompressed string
     */
    public static String decompressString(String compressedString) {
        String decompressedString = "";

        StringBuilder decompressedBuilder = new StringBuilder();
        StringBuilder supportNumBuilder = new StringBuilder();
        StringBuilder supportStringBuilder = new StringBuilder();

        String currString = "";

        // support variables
        char currChar;
        char nextChar;
        boolean isCurrCharNum;
        boolean isNextCharNum;
        int supportNum = 0;
        int compressedStringLen = compressedString.length();
        String supportString = "";

        for(int i = 0; i < compressedStringLen - 1; i++)
        {
            currChar = compressedString.charAt(i);
            nextChar = compressedString.charAt(i+1);

            isCurrCharNum = isNumber(currChar);
            isNextCharNum = isNumber(nextChar);

            // if this is the last iteration
            if(i == compressedStringLen - 2) {
                // there are only numbers as last characters in the compressed string
                supportNumBuilder.append(nextChar);

                if(isCurrCharNum){
                    supportNumBuilder.append(currChar);
                }
                else {
                    supportStringBuilder.append(currChar);
                }

                supportString = supportStringBuilder.toString();
                supportNum = Integer.parseInt(supportNumBuilder.toString());

                for(int j = 0; j < supportNum; j++){
                    decompressedBuilder.append(supportString);
                }

                decompressedString = decompressedBuilder.toString();

                return decompressedString;
            }

            // otherwise
            if((isCurrCharNum && !isNextCharNum)) {
                supportNumBuilder.append(currChar);
                supportString = supportStringBuilder.toString();
                supportStringBuilder.setLength(0);
                supportNum = Integer.parseInt(supportNumBuilder.toString());
                supportNumBuilder.setLength(0);

                for(int j = 0; j < supportNum; j++){
                    decompressedBuilder.append(supportString);
                }
            }
            else if(isCurrCharNum) {
                supportNumBuilder.append(currChar);
            }
            else {
                supportStringBuilder.append(currChar);
            }
        }
        return decompressedString;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String filePath = args[0];
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        // Tests for part A
        int numberOfGrades = scanner.nextInt();
        for (int i = 0; i < numberOfGrades; i++) {
            int grade = scanner.nextInt();
            gradeMessage(grade);
        }

        // Tests for part B1
        int numberOfStringsToCompress = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < numberOfStringsToCompress; i++) {
            String stringToCompress = scanner.nextLine();
            String compressedString = compressString(stringToCompress);
            System.out.println("The compressed version of " + stringToCompress + " is " + compressedString);
        }

        // Tests for part B2
        int numberOfDecompressedStrings = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < numberOfDecompressedStrings; i++) {
            String compressedString = scanner.nextLine();
            String decompressedString = decompressString(compressedString);
            System.out.println("The decompressed version of " + compressedString + " is " + decompressedString);
        }

        // Tests for both part B1 and B2
        int numberOfCombinedTests = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < numberOfCombinedTests; i++) {
            String stringToCompress = scanner.nextLine();
            String compressedString = compressString(stringToCompress);
            String decompressedString = decompressString(compressedString);
            System.out.println("decompress(compress(" + stringToCompress + ")) == " + stringToCompress + "? " + stringToCompress.equals(decompressedString));
        }
    }
}

