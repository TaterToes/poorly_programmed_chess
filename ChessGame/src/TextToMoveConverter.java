public class TextToMoveConverter {
    public int getColumnValue(String input) {
        if (input == null || input.isEmpty()) {
            return -1; // Return an error code if input is empty or null
        }

        char firstLetter = Character.toLowerCase(input.charAt(0)); // Get first letter and convert to lowercase
        return switch (firstLetter) {
            case 'a' -> 1;
            case 'b' -> 2;
            case 'c' -> 3;
            case 'd' -> 4;
            case 'e' -> 5;
            case 'f' -> 6;
            case 'g' -> 7;
            case 'h' -> 8;
            default -> -1;
        }; // Return -1 if the letter is not in range
    }

    public int getRowValue(String input) {
        if (input == null || input.length() < 2) {
            return -1; // Return -1 if no second character
        }

        char secondChar = input.charAt(1); // Get the second character

        if (secondChar >= '1' && secondChar <= '8') {
            return Character.getNumericValue(secondChar); // Convert char to integer
        }

        return -1; // Return -1 if it's not a valid row number
    }
}
