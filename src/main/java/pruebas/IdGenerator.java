package pruebas;

public class IdGenerator {
    private static final int LENGTH = 11;

    private static IdGenerator instancia = null;


    public IdGenerator() {
    }

    public static IdGenerator getInstancia() {
        if(instancia == null) {
            instancia = new IdGenerator();
        }
        return instancia;
    }

    public static String generateNextId(String currentId) {
        char[] idArray = currentId.toCharArray();
        boolean carry = true;

        for (int i = idArray.length - 1; i >= 0 && carry; i--) {
            if (Character.isDigit(idArray[i])) {
                if (idArray[i] == '9') {
                    idArray[i] = '0';
                } else {
                    idArray[i]++;
                    carry = false;
                }
            } else if (Character.isLetter(idArray[i])) {
                if (idArray[i] == 'Z') {
                    idArray[i] = 'A';
                } else {
                    idArray[i]++;
                    carry = false;
                }
            }
        }

        if (carry) {
            // If carry is still true, it means we have exhausted the sequence
            // We need to wrap around to "ZZ000000000"
            for (int i = 0; i < idArray.length; i++) {
                if (Character.isLetter(idArray[i])) {
                    idArray[i] = 'Z';
                } else if (Character.isDigit(idArray[i])) {
                    idArray[i] = '0';
                }
            }
        }

        return new String(idArray);
    }

    public static void main(String[] args) {
        System.out.println(generateNextId("00000000009")); // Should print 00000000010
        System.out.println(generateNextId("A0000000009")); // Should print A0000000010
        System.out.println(generateNextId("99999999999")); // Should print Z0000000000
        System.out.println(generateNextId("Z9999999999")); // Should print ZZ000000000
    }
}