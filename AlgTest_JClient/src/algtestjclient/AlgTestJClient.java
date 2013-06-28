/*  
    Copyright (c) 2008-2012 Petr Svenda <petr@svenda.com>

     LICENSE TERMS

     The free distribution and use of this software in both source and binary
     form is allowed (with or without changes) provided that:

       1. distributions of this source code include the above copyright
          notice, this list of conditions and the following disclaimer;

       2. distributions in binary form include the above copyright
          notice, this list of conditions and the following disclaimer
          in the documentation and/or other associated materials;

       3. the copyright holder's name is not used to endorse products
          built using this software without specific written permission.

     ALTERNATIVELY, provided that this notice is retained in full, this product
     may be distributed under the terms of the GNU General Public License (GPL),
     in which case the provisions of the GPL apply INSTEAD OF those given above.

     DISCLAIMER

     This software is provided 'as is' with no explicit or implied warranties
     in respect of its properties, including, but not limited to, correctness
     and/or fitness for purpose.

    Please, report any bugs to author <petr@svenda.com>
/**/

package algtestjclient;

import java.io.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
/**
 *
 * @author petr
 */
public class AlgTestJClient {
    static CardMngr cardManager = new CardMngr();
    
    /**
     * Version 1.1 (28.6.2013)
     * + information about version added
     * + link to project added into resulting file 
     */
    public final static String ALGTEST_JCLIENT_VERSION_1_1 = "1.1";
    /**
     * Version 1.0 (27.11.2012)
     * + initial version of AlgTestJClient, clone of AlgTestCppClient
     */
    public final static String ALGTEST_JCLIENT_VERSION_1_0 = "1.0";
 
    /**
     * Current version
     */
    public final static String ALGTEST_JCLIENT_VERSION = ALGTEST_JCLIENT_VERSION_1_1;
    
    public final static int STAT_OK = 0;    
        /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int bForceAllTests = 0; // If 0, questions to test parts will be displayed, otherwise, all tests will be performed

        try {

            if (args.length > 0) {
                try {
                    bForceAllTests = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    System.err.println("First argument must be 0 or 1 type (bForceAllTests)");
                }
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));            

            StringBuilder atr = new StringBuilder(); 
            StringBuilder reader = new StringBuilder();
            StringBuilder protocol = new StringBuilder();
            
            
            // Find first card with AlgTest applet and connect
            if (cardManager.ConnectToCard(reader, atr, protocol)) {
               
                String message = "";
                String fileName = "AlgTest_" + atr + ".csv";
                fileName = fileName.replace(":", "");

                FileOutputStream file = new FileOutputStream(fileName);
                
                StringBuilder value = new StringBuilder();                
                 
                message += "INFO: This file was generated by AlgTest utility. See http://www.fi.muni.cz/~xsvenda/jcsupport.html for more results, source codes and other details.;\r\n";
                System.out.println(message);
                file.write(message.getBytes());                
                
                message = "Execution date/time; ";
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                message += dateFormat.format(date) + "\r\n";
                System.out.println(message);
                file.write(message.getBytes()); 
                
                message = "AlgTestJClient version; " + ALGTEST_JCLIENT_VERSION + "\r\n";
                System.out.println(message);
                file.write(message.getBytes());    
             
                value.setLength(0);
                if (cardManager.GetAppletVersion(value) == CardMngr.STAT_OK) {
                    message = "AlgTest applet version; " + value + "\r\n";
                    System.out.println(message);
                    file.write(message.getBytes()); 
                }
                else { System.out.println("\nERROR: GetAppletVersion fail"); }
                

                message = "Used reader; " + reader + "\r\n";
                System.out.println(message);
                file.write(message.getBytes());
                message = "Card ATR; " + atr + "\r\n";
                System.out.println(message);
                file.write(message.getBytes());
                message = "Used protocol; " + protocol + "\r\n";
                System.out.println(message);
                file.write(message.getBytes());
                

                System.out.println("\n\n#########################");
                System.out.println("\nJCSystem information");

                if (cardManager.GetJCSystemInfo(value, file) == CardMngr.STAT_OK) {}
                else { System.out.println("\nERROR: GetJCSystemInfo fail"); }


                int	answ = bForceAllTests;
                if (bForceAllTests == 0) {
                    System.out.println("\n\n#########################");
                    System.out.println("\n\nQ: Do you like to test supported algorithms?");
                    System.out.println("Type 1 for yes, 0 for no: ");	
                    answ = Integer.decode(br.readLine());                
                }
                if (answ == 1) {

                        System.out.println("#########################");

                        // Class javacardx.crypto.Cipher
                        value.setLength(0);
                        if (cardManager.GetSupportedAndParse(CardMngr.CLASS_CIPHER, CardMngr.CIPHER_STR, value, file, (byte) 0) == STAT_OK) {}
                        else { System.out.println("\nERROR: javacardx.crypto.Cipher fail\r\n"); }
                        file.flush();

                        // Class javacard.security.Signature
                        value.setLength(0);
                        if (cardManager.GetSupportedAndParse(CardMngr.CLASS_SIGNATURE, CardMngr.SIGNATURE_STR, value, file, (byte) 0) == STAT_OK) {}
                        else { System.out.println("\nERROR: jav1acard.security.Signature fail\n"); }
                        file.flush();

                        // Class javacard.security.MessageDigest
                        value.setLength(0);
                        if (cardManager.GetSupportedAndParse(CardMngr.CLASS_MESSAGEDIGEST, CardMngr.MESSAGEDIGEST_STR, value, file, (byte) 0) == STAT_OK) {}
                        else { System.out.println("\nERROR: javacard.security.MessageDigest fail\r\n"); }
                        file.flush();

                        // Class javacard.security.RandomData
                        value.setLength(0);
                        if (cardManager.GetSupportedAndParse(CardMngr.CLASS_RANDOMDATA, CardMngr.RANDOMDATA_STR, value, file, (byte) 0) == STAT_OK) {}
                        else { System.out.println("\nERROR: javacard.security.RandomData fail\r\n"); }
                        file.flush();

                        // Class javacard.security.KeyBuilder
                        value.setLength(0);
                        if (cardManager.GetSupportedAndParse(CardMngr.CLASS_KEYBUILDER, CardMngr.KEYBUILDER_STR, value, file, (byte) 0) == STAT_OK) {}
                        else { System.out.println("\nERROR: javacard.security.KeyBuilder fail\n"); }
                        file.flush();

                        // Class javacard.security.KeyPair RSA
                        value.setLength(0);
                        if (cardManager.GetSupportedAndParse(CardMngr.CLASS_KEYPAIR_RSA, CardMngr.KEYPAIR_RSA_STR, value, file, CardMngr.CLASS_KEYPAIR_RSA_P2) == STAT_OK) {}
                        else { System.out.println("\nERROR: javacard.security.KeyPair RSA fail\n"); }
                        file.flush();
                        // Class javacard.security.KeyPair RSA_CRT
                        value.setLength(0);
                        if (cardManager.GetSupportedAndParse(CardMngr.CLASS_KEYPAIR_RSA_CRT, CardMngr.KEYPAIR_RSACRT_STR, value, file, CardMngr.CLASS_KEYPAIR_RSACRT_P2) == STAT_OK) {}
                        else { System.out.println("\nERROR: javacard.security.KeyPair RSA_CRT fail\n"); }
                        file.flush();
                        // Class javacard.security.KeyPair DSA
                        value.setLength(0);
                        if (cardManager.GetSupportedAndParse(CardMngr.CLASS_KEYPAIR_DSA, CardMngr.KEYPAIR_DSA_STR, value, file, CardMngr.CLASS_KEYPAIR_DSA_P2) == STAT_OK) {}
                        else { System.out.println("\nERROR: javacard.security.KeyPair DSA fail\n"); }
                        file.flush();
                        // Class javacard.security.KeyPair EC_F2M
                        value.setLength(0);
                        if (cardManager.GetSupportedAndParse(CardMngr.CLASS_KEYPAIR_EC_F2M, CardMngr.KEYPAIR_EC_F2M_STR, value, file,  CardMngr.CLASS_KEYPAIR_EC_F2M_P2) == STAT_OK) {}
                        else { System.out.println("\nERROR: javacard.security.KeyPair EC_F2M fail\n"); }
                        file.flush();
                        // Class javacard.security.KeyPair EC_FP
                        value.setLength(0);
                        if (cardManager.GetSupportedAndParse(CardMngr.CLASS_KEYPAIR_EC_FP, CardMngr.KEYPAIR_EC_FP_STR, value, file, CardMngr.CLASS_KEYPAIR_EC_FP_P2) == STAT_OK) {}
                        else { System.out.println("\nERROR: javacard.security.KeyPair EC_FP fail\n"); }
                        file.flush();

                        // Class javacard.security.KeyAgreement
                        value.setLength(0);
                        if (cardManager.GetSupportedAndParse(CardMngr.CLASS_KEYAGREEMENT, CardMngr.KEYAGREEMENT_STR, value, file, (byte) 0) == STAT_OK) {}
                        else { System.out.println("\nERROR: javacard.security.KeyAgreement fail\n"); }
                        file.flush();

                        // Class javacard.security.Checksum
                        value.setLength(0);
                        if (cardManager.GetSupportedAndParse(CardMngr.CLASS_CHECKSUM, CardMngr.CHECKSUM_STR, value, file, (byte) 0) == STAT_OK) {}
                        else { System.out.println("\nERROR: javacard.security.Checksum fail\n"); }
                        file.flush();
                }

                if (bForceAllTests == 0) {                
                    System.out.println("\n\n#########################");
                    System.out.println("\n\nQ: Do you like to test support for variable RSA public exponent?");
                    System.out.println("Type 1 for yes, 0 for no: ");	
                    answ = Integer.decode(br.readLine());     
                }
                if (answ == 1) {
                    // Variable public exponent
                    value.setLength(0);
                    if (cardManager.TestVariableRSAPublicExponentSupport(value, file, (byte) 0) == STAT_OK) {}
                    else { System.out.println("\nERROR: Test variable public exponnet support fail\n"); }
                    file.flush();
                }

                if (bForceAllTests == 0) {                
                    System.out.println("\n\n#########################");
                    System.out.println("\n\nQ: Do you like to test RAM memory available for allocation?");
                    System.out.println("\n\nSTRONG WARNING: There is possibility that your card become unresponsive after this test. All cards I tested required just to delete AlgTest applet to reclaim allocated memory. But it might be possible that your card will be unusuable after this test.?");
                    System.out.println("\n\nWARNING: Your card should be free from other applets - otherwise memory already claimed by existing applets will not be included. Value is approximate +- 100B");
                    System.out.println("Type 1 for yes, 0 for no: ");	
                    answ = Integer.decode(br.readLine());                
                }
                if (answ == 1) {                    
                        // Available memory
                        value.setLength(0);
                        if (cardManager.TestAvailableRAMMemory(value, file, (byte) 0) == STAT_OK) {}
                        else { System.out.println("\nERROR: Get available RAM memory fail\n"); }
                        file.flush();
                }

                if (bForceAllTests == 0) {                
                    System.out.println("\n\n#########################");
                    System.out.println("\n\nQ: Do you like to test EEPROM memory available for allocation?");
                    System.out.println("\n\nSTRONG WARNING: There is possibility that your card become unresponsive after this test. All cards I tested required just to delete AlgTest applet to reclaim allocated memory. But it might be possible that your card will be unusuable after this test.");
                    System.out.println("\n\nWARNING: Your card should be free from other applets - otherwise memory already claimed by existing applets will not be included. Value is approximate +- 5KB");
                    System.out.println("Type 1 for yes, 0 for no: ");	
                    answ = Integer.decode(br.readLine());                
                }
                if (answ == 1) {
                        // Available memory
                        value.setLength(0);
                        if (cardManager.TestAvailableEEPROMMemory(value, file, (byte) 0) == STAT_OK) {}
                        else { System.out.println("\nERROR: Get available EEPROM memory fail\n"); }
                        file.flush();
                }
                
/* DISABLED FOR NOW AS new CommandAPDU(apdu) will fail with incorrect apdu length when 1024B apdu is used
                if (bForceAllTests == 0) {                
                    System.out.println("\n\n#########################");
                    System.out.println("\n\nQ: Do you like to test support for extended APDU?");
                    System.out.println("Type 1 for yes, 0 for no: ");	
                    answ = Integer.decode(br.readLine());                
                    * }
                if (answ == 1) {
                    // Extended APDU support
                    value.setLength(0);
                    if (cardManager.TestExtendedAPDUSupportSupport(value, file, (byte) 0) == STAT_OK) {}
                    else { System.out.println("\nERROR: Test extended APDU support fail\n"); }
                    file.flush();
                }                
                
*/ 
                file.close();

                cardManager.DisconnectFromCard();
            }
            else { System.out.println("\nERROR: fail to connect to card with AlgTest applet"); }

        } 
        catch (IOException ex) {
            System.out.println("IOException : " + ex);
        }
        catch (Exception ex) {
            System.out.println("Exception : " + ex);
        }
    }
}
