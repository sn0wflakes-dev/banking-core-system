package aji.intern.core.helper;

import java.security.SecureRandom;

public class CardNumberBuilder {
    private String BIN = "";
    private String accountIdentifier = "";

    public CardNumberBuilder setBIN(String BIN) {
        this.BIN = BIN;
        return this;
    }

    public CardNumberBuilder generateRandomAccountIdentifier() {
        StringBuilder stringBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();

        int ACCOUNT_ID_LEN = 9;
        for(int i = 0; i < ACCOUNT_ID_LEN; i++) {
            stringBuilder.append(random.nextInt(10));
        }

        accountIdentifier = stringBuilder.toString();
        return this;
    }

    public String build() {
        String partialNumber = BIN + accountIdentifier;
        char checkDigit = luhnValidation(partialNumber);
        return partialNumber + checkDigit;
    }

    private char luhnValidation(String number) {
        int sum = 0;
        boolean alternate = true;
        for(int i = number.length() - 1; i >= 0; i--) {
            int n = Character.getNumericValue(number.charAt(i));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        int mod = sum % 10;
        int checkDigit = (mod == 0) ? 0 : 10 - mod;
        return Character.forDigit(checkDigit, 10);
    }
}
