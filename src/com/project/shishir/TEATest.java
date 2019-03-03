package com.project.shishir;

public class TEATest {

    byte[] result;
    byte[] tesst;
    byte[] crypt;
    byte[] original;
    private String username;
    com.project.shishir.TEA tea = new com.project.shishir.TEA();

    public void Username(String username) {
        this.username = username;
    }

      /* Create a cipher using the first 16 bytes of the passphrase */

    public void createCipher(String msg) {
        String key = "1234567";
        byte[] key_Byte = key.getBytes();
        //System.out.println(key_Byte.length);

       // System.out.println("Entered text is "+msg);
        original = msg.getBytes();
        System.out.println(original);
    }
    public byte[] encrypt() {
      /* Run it through the cipher... and back */
        crypt = tea.encrypt(original);
        System.out.println(crypt);
        return crypt;
    }

    public byte[] decrypt(byte[] dec) {
        result = tea.decrypt(dec);
        String test = new String(result);
        tesst = test.getBytes();
        System.out.println("Decrypted Text is");
        //System.out.println(tesst);
        System.out.println(test);
        if (!test.equals(username))
            System.out.print(" ");
        return result;

    }
}
