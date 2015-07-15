package AlgTest;

import static AlgTest.AlgTestSinglePerApdu.CLASS_SIGNATURE;
import algtestjclient.PerformanceTesting;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.FileOutputStream;
import static java.lang.System.out;
import java.lang.reflect.*;
import static org.testng.Assert.*;
import org.testng.annotations.Test;


/**
 *
 * @author xsvenda
 */
public class PerformanceTestingNGTest {
    static PerformanceTesting perfTesting = new PerformanceTesting();
    static boolean bTestRealCards = true;
    
    public PerformanceTestingNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        if (perfTesting.m_perfResultsFile != null) perfTesting.m_perfResultsFile.close();
    }
    
    @Test
    void perftest_testAll() throws Exception {    
        perfTesting.m_perfResultsFile = (bTestRealCards) ? perfTesting.m_cardManager.establishConnection(null) : perfTesting.m_cardManager.establishConnection(AlgTestSinglePerApdu.class);   
        assertNotEquals(perfTesting.m_perfResultsFile, null);

        short numRepeatWholeOperation = 10;
        short numRepeatWholeMeasurement = 3;
        
        perfTesting.testAllMessageDigests(numRepeatWholeOperation, numRepeatWholeMeasurement);
        perfTesting.m_cardManager.UploadApplet(0);
        perfTesting.testAllRandomGenerators(numRepeatWholeOperation, numRepeatWholeMeasurement);
        perfTesting.m_cardManager.UploadApplet(0);
        perfTesting.testAllCiphers(numRepeatWholeOperation, numRepeatWholeMeasurement);
        perfTesting.m_cardManager.UploadApplet(0);
        perfTesting.testAllSignatures(numRepeatWholeOperation, numRepeatWholeMeasurement);
        perfTesting.m_cardManager.UploadApplet(0);
        perfTesting.testAllChecksums(numRepeatWholeOperation, numRepeatWholeMeasurement);     
        perfTesting.m_cardManager.UploadApplet(0);
        
        perfTesting.testAllKeyPairs(1, numRepeatWholeMeasurement);   
        perfTesting.m_cardManager.UploadApplet(0);

        perfTesting.testAllKeys(numRepeatWholeOperation, numRepeatWholeMeasurement);     
        perfTesting.m_cardManager.UploadApplet(0);
    }                   

    @Test
    void perftest_testClass_AESKey() throws Exception {
        perfTesting.m_perfResultsFile = (bTestRealCards) ? perfTesting.m_cardManager.establishConnection(null) : perfTesting.m_cardManager.establishConnection(AlgTestSinglePerApdu.class);   
        assertNotEquals(perfTesting.m_perfResultsFile, null);

        // Prepare test
        TestSettings testSet = null;
        testSet = perfTesting.prepareTestSettings(Consts.CLASS_KEYBUILDER, Consts.UNUSED, JCConsts.KeyBuilder_TYPE_AES, JCConsts.KeyBuilder_LENGTH_AES_128, JCConsts.AESKey_setKey, 
                Consts.UNUSED, Consts.UNUSED, Consts.UNUSED, (short) 1, Consts.UNUSED, (short) 1);      

        //perfTesting.perftest_prepareClass(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_KEY, testSet);
        
        testSet.numRepeatWholeOperation = 100;
        testSet.numRepeatSubOperation = 1;
        testSet.numRepeatWholeMeasurement = 5;
        testSet.algorithmMethod = JCConsts.AESKey_setKey;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_KEY, Consts.INS_PERF_TEST_CLASS_KEY, testSet, "AESKey TYPE_AES LENGTH_AES_128 setKey()") > -1);
        testSet.algorithmMethod = JCConsts.AESKey_getKey;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_KEY, Consts.INS_PERF_TEST_CLASS_KEY, testSet, "AESKey TYPE_AES LENGTH_AES_128 getKey()") > -1);
        testSet.algorithmMethod = JCConsts.AESKey_clearKey;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_KEY, Consts.INS_PERF_TEST_CLASS_KEY, testSet, "AESKey TYPE_AES LENGTH_AES_128 clearKey()") > -1);
        
        testSet.numRepeatWholeOperation = 3;
        testSet.algorithmMethod = JCConsts.AESKey_setKey;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_KEY, Consts.INS_PERF_TEST_CLASS_KEY, testSet, "AESKey TYPE_AES LENGTH_AES_128 setKey()") > -1);
        testSet.algorithmMethod = JCConsts.AESKey_getKey;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_KEY, Consts.INS_PERF_TEST_CLASS_KEY, testSet, "AESKey TYPE_AES LENGTH_AES_128 getKey()") > -1);
        testSet.algorithmMethod = JCConsts.AESKey_clearKey;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_KEY, Consts.INS_PERF_TEST_CLASS_KEY, testSet, "AESKey TYPE_AES LENGTH_AES_128 clearKey()") > -1);
        
        // BUGBUG: add check for Exception in output
    }
    
    
    @Test
    void perftest_testClass_Cipher() throws Exception {
        perfTesting.m_perfResultsFile = (bTestRealCards) ? perfTesting.m_cardManager.establishConnection(null) : perfTesting.m_cardManager.establishConnection(AlgTestSinglePerApdu.class);   
        assertNotEquals(perfTesting.m_perfResultsFile, null);

        // Prepare test
        TestSettings testSet = null;
        testSet = perfTesting.prepareTestSettings(Consts.CLASS_CIPHER, JCConsts.Cipher_ALG_AES_BLOCK_128_CBC_NOPAD, JCConsts.KeyBuilder_TYPE_AES, JCConsts.KeyBuilder_LENGTH_AES_128, JCConsts.Cipher_update, 
                Consts.TEST_DATA_LENGTH, Consts.UNUSED, Consts.UNUSED, (short) 1, (short) 1, (short) 1);      

        
        //perfTesting.perftest_prepareClass(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_CIPHER, testSet);
        
        testSet.numRepeatWholeMeasurement = 2;
        testSet.numRepeatWholeOperation = 100;
        testSet.numRepeatSubOperation = 1;
        testSet.algorithmMethod = JCConsts.Cipher_update;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_CIPHER, Consts.INS_PERF_TEST_CLASS_CIPHER, testSet, "Cipher ALG_AES_BLOCK_128_CBC_NOPAD LENGTH_AES_128 Cipher_update()") > -1);
        testSet.algorithmMethod = JCConsts.Cipher_doFinal;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_CIPHER, Consts.INS_PERF_TEST_CLASS_CIPHER, testSet, "Cipher ALG_AES_BLOCK_128_CBC_NOPAD LENGTH_AES_128 Cipher_doFinal()") > -1);
        testSet.algorithmMethod = JCConsts.Cipher_init;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_CIPHER, Consts.INS_PERF_TEST_CLASS_CIPHER, testSet, "Cipher ALG_AES_BLOCK_128_CBC_NOPAD LENGTH_AES_128 Cipher_init()") > -1);

        testSet.numRepeatWholeOperation = 3;
        testSet.algorithmMethod = JCConsts.Cipher_update;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_CIPHER, Consts.INS_PERF_TEST_CLASS_CIPHER, testSet, "Cipher ALG_AES_BLOCK_128_CBC_NOPAD LENGTH_AES_128 Cipher_update()") > -1);
        testSet.algorithmMethod = JCConsts.Cipher_doFinal;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_CIPHER, Consts.INS_PERF_TEST_CLASS_CIPHER, testSet, "Cipher ALG_AES_BLOCK_128_CBC_NOPAD LENGTH_AES_128 Cipher_doFinal()") > -1);
        testSet.algorithmMethod = JCConsts.Cipher_init;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_CIPHER, Consts.INS_PERF_TEST_CLASS_CIPHER, testSet, "Cipher ALG_AES_BLOCK_128_CBC_NOPAD LENGTH_AES_128 Cipher_init()") > -1);
        
/*        
        // Test operation on splitted chunks of data (256 / 8)
        testSet.numRepeatSubOperation = 8;
        testSet.algorithmMethod = Cipher_update;
        perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_CIPHER, Consts.INS_PERF_TEST_CLASS_CIPHER, testSet, "Cipher ALG_AES_BLOCK_128_CBC_NOPAD LENGTH_AES_128 8x Cipher_update()");
        testSet.algorithmMethod = Cipher_doFinal;
        perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_CIPHER, Consts.INS_PERF_TEST_CLASS_CIPHER, testSet, "Cipher ALG_AES_BLOCK_128_CBC_NOPAD LENGTH_AES_128 8x Cipher_doFinal()");
        testSet.algorithmMethod = Cipher_init;
        perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_CIPHER, Consts.INS_PERF_TEST_CLASS_CIPHER, testSet, "Cipher ALG_AES_BLOCK_128_CBC_NOPAD LENGTH_AES_128 8x Cipher_init()");
*/        
    }    

    @Test
    void perftest_testClass_Cipher_RSA() throws Exception {
        bTestRealCards = true;
        perfTesting.m_perfResultsFile = (bTestRealCards) ? perfTesting.m_cardManager.establishConnection(null) : perfTesting.m_cardManager.establishConnection(AlgTestSinglePerApdu.class);   
        assertNotEquals(perfTesting.m_perfResultsFile, null);

        perfTesting.testCipher(JCConsts.KeyBuilder_TYPE_RSA_CRT_PRIVATE, JCConsts.KeyBuilder_LENGTH_RSA_512, JCConsts.Cipher_ALG_RSA_NOPAD, "TYPE_RSA_CRT_PRIVATE LENGTH_RSA_512 ALG_RSA_NOPAD", JCConsts.Cipher_MODE_DECRYPT, (short) 1, (short) 3);
    }    
    
    @Test
    void perftest_testClass_Signature() throws Exception {
        perfTesting.m_perfResultsFile = (bTestRealCards) ? perfTesting.m_cardManager.establishConnection(null) : perfTesting.m_cardManager.establishConnection(AlgTestSinglePerApdu.class);   
        assertNotEquals(perfTesting.m_perfResultsFile, null);


        // Prepare test
        TestSettings testSet = null;
        testSet = perfTesting.prepareTestSettings(CLASS_SIGNATURE, JCConsts.Signature_ALG_AES_MAC_128_NOPAD, JCConsts.KeyBuilder_TYPE_AES, JCConsts.KeyBuilder_LENGTH_AES_128, JCConsts.Signature_sign, 
                Consts.TEST_DATA_LENGTH, Consts.UNUSED, Consts.UNUSED, (short) 1, (short) 1, (short) 1);      

        //perfTesting.perftest_prepareClass(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_SIGNATURE, testSet);
        
        // Test single execution of operation
        testSet.numRepeatWholeMeasurement = 2;
        testSet.numRepeatWholeOperation = 100;
        testSet.numRepeatSubOperation = 1;
        testSet.algorithmMethod = JCConsts.Signature_update;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_SIGNATURE, Consts.INS_PERF_TEST_CLASS_SIGNATURE, testSet, "Cipher ALG_AES_MAC_128_NOPAD LENGTH_AES_128 Signature_update()") > -1);
        testSet.algorithmMethod = JCConsts.Signature_sign;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_SIGNATURE, Consts.INS_PERF_TEST_CLASS_SIGNATURE, testSet, "Cipher ALG_AES_MAC_128_NOPAD LENGTH_AES_128 Signature_sign()") > -1);
        testSet.algorithmMethod = JCConsts.Signature_init;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_SIGNATURE, Consts.INS_PERF_TEST_CLASS_SIGNATURE, testSet, "Cipher ALG_AES_MAC_128_NOPAD LENGTH_AES_128 Signature_init()") > -1);
        testSet.algorithmMethod = JCConsts.Signature_verify;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_SIGNATURE, Consts.INS_PERF_TEST_CLASS_SIGNATURE, testSet, "Cipher ALG_AES_MAC_128_NOPAD LENGTH_AES_128 Signature_verify()") > -1);

        testSet.numRepeatWholeOperation = 3;
        testSet.algorithmMethod = JCConsts.Signature_update;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_SIGNATURE, Consts.INS_PERF_TEST_CLASS_SIGNATURE, testSet, "Cipher ALG_AES_MAC_128_NOPAD LENGTH_AES_128 Signature_update()") > -1);
        testSet.algorithmMethod = JCConsts.Signature_sign;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_SIGNATURE, Consts.INS_PERF_TEST_CLASS_SIGNATURE, testSet, "Cipher ALG_AES_MAC_128_NOPAD LENGTH_AES_128 Signature_sign()") > -1);
        testSet.algorithmMethod = JCConsts.Signature_init;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_SIGNATURE, Consts.INS_PERF_TEST_CLASS_SIGNATURE, testSet, "Cipher ALG_AES_MAC_128_NOPAD LENGTH_AES_128 Signature_init()") > -1);
        testSet.algorithmMethod = JCConsts.Signature_verify;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_SIGNATURE, Consts.INS_PERF_TEST_CLASS_SIGNATURE, testSet, "Cipher ALG_AES_MAC_128_NOPAD LENGTH_AES_128 Signature_verify()") > -1);
    
    }
    
    @Test
    void perftest_testClass_RandomData() throws Exception {
        perfTesting.m_perfResultsFile = (bTestRealCards) ? perfTesting.m_cardManager.establishConnection(null) : perfTesting.m_cardManager.establishConnection(AlgTestSinglePerApdu.class);   
        assertNotEquals(perfTesting.m_perfResultsFile, null);


        // Prepare test
        TestSettings testSet = null;
        testSet = perfTesting.prepareTestSettings(Consts.CLASS_RANDOMDATA, JCConsts.RandomData_ALG_SECURE_RANDOM, Consts.UNUSED, Consts.UNUSED, JCConsts.RandomData_generateData, 
                Consts.TEST_DATA_LENGTH, Consts.UNUSED, Consts.UNUSED, (short) 1, (short) 1, (short) 1);      

        // Test single execution of operation
        testSet.numRepeatWholeMeasurement = 2;
        testSet.numRepeatWholeOperation = 100;
        testSet.numRepeatSubOperation = 1;
        testSet.algorithmMethod = JCConsts.RandomData_generateData;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_RANDOMDATA, Consts.INS_PERF_TEST_CLASS_RANDOMDATA, testSet, "RandomData ALG_SECURE_RANDOM RandomData_generateData()") > -1);
        testSet.algorithmMethod = JCConsts.RandomData_setSeed;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_RANDOMDATA, Consts.INS_PERF_TEST_CLASS_RANDOMDATA, testSet, "RandomData ALG_SECURE_RANDOM RandomData_setSeed()") > -1);

        testSet.algorithmSpecification = JCConsts.RandomData_ALG_PSEUDO_RANDOM;
        testSet.algorithmMethod = JCConsts.RandomData_generateData;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_RANDOMDATA, Consts.INS_PERF_TEST_CLASS_RANDOMDATA, testSet, "RandomData ALG_PSEUDO_RANDOM RandomData_generateData()") > -1);
        testSet.algorithmMethod = JCConsts.RandomData_setSeed;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_RANDOMDATA, Consts.INS_PERF_TEST_CLASS_RANDOMDATA, testSet, "RandomData ALG_PSEUDO_RANDOM RandomData_setSeed()") > -1);
    }    
    
   @Test
    void perftest_testClass_MessageDigest() throws Exception {
        perfTesting.m_perfResultsFile = (bTestRealCards) ? perfTesting.m_cardManager.establishConnection(null) : perfTesting.m_cardManager.establishConnection(AlgTestSinglePerApdu.class);   
        assertNotEquals(perfTesting.m_perfResultsFile, null);


        // BUGBUG: other types from MessageDigest
        
        // Prepare test
        TestSettings testSet = null;
        testSet = perfTesting.prepareTestSettings(Consts.CLASS_MESSAGEDIGEST, JCConsts.MessageDigest_ALG_SHA, JCConsts.MessageDigest_ALG_SHA, Consts.UNUSED, JCConsts.MessageDigest_update, 
                Consts.TEST_DATA_LENGTH, Consts.UNUSED, Consts.UNUSED, (short) 1, (short) 1, (short) 1);      

        // Test single execution of operation
        testSet.numRepeatWholeMeasurement = 2;
        testSet.numRepeatWholeOperation = 100;
        testSet.numRepeatSubOperation = 1;
        testSet.algorithmMethod = JCConsts.MessageDigest_update;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_MESSAGEDIGEST, Consts.INS_PERF_TEST_CLASS_MESSAGEDIGEST, testSet, "MessageDigest ALG_SHA MessageDigest_update()") > -1);
        testSet.algorithmMethod = JCConsts.MessageDigest_doFinal;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_MESSAGEDIGEST, Consts.INS_PERF_TEST_CLASS_MESSAGEDIGEST, testSet, "MessageDigest ALG_SHA MessageDigest_doFinal()") > -1);
        testSet.algorithmMethod = JCConsts.MessageDigest_reset;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_MESSAGEDIGEST, Consts.INS_PERF_TEST_CLASS_MESSAGEDIGEST, testSet, "MessageDigest ALG_SHA MessageDigest_reset()") > -1);

    }    
    
   @Test
    void perftest_testClass_Checksum() throws Exception {
        perfTesting.m_perfResultsFile = (bTestRealCards) ? perfTesting.m_cardManager.establishConnection(null) : perfTesting.m_cardManager.establishConnection(AlgTestSinglePerApdu.class);   
        assertNotEquals(perfTesting.m_perfResultsFile, null);


        // BUGBUG: other types from MessageDigest
        
        // Prepare test
        TestSettings testSet = null;
        testSet = perfTesting.prepareTestSettings(Consts.CLASS_CHECKSUM, JCConsts.Checksum_ALG_ISO3309_CRC16, JCConsts.Checksum_ALG_ISO3309_CRC16, Consts.UNUSED, JCConsts.Checksum_update, 
                Consts.TEST_DATA_LENGTH, Consts.UNUSED, Consts.UNUSED, (short) 1, (short) 1, (short) 1);      
        // BUGBUG: ALG_ISO3309_CRC32
        
        // Test single execution of operation
        testSet.numRepeatWholeMeasurement = 2;
        testSet.numRepeatWholeOperation = 100;
        testSet.numRepeatSubOperation = 1;
        testSet.algorithmMethod = JCConsts.Checksum_update;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_CHECKSUM, Consts.INS_PERF_TEST_CLASS_CHECKSUM, testSet, "Checksum ALG_ISO3309_CRC16 Checksum_update()") > -1);
        testSet.algorithmMethod = JCConsts.Checksum_doFinal;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_CHECKSUM, Consts.INS_PERF_TEST_CLASS_CHECKSUM, testSet, "Checksum ALG_ISO3309_CRC16 Checksum_doFinal()") > -1);

        testSet.numRepeatSubOperation = 1;
        testSet.keyType = testSet.algorithmSpecification = JCConsts.Checksum_ALG_ISO3309_CRC32;
        testSet.algorithmMethod = JCConsts.Checksum_update;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_CHECKSUM, Consts.INS_PERF_TEST_CLASS_CHECKSUM, testSet, "Checksum ALG_ISO3309_CRC32 Checksum_update()") > -1);
        testSet.algorithmMethod = JCConsts.Checksum_doFinal;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_CHECKSUM, Consts.INS_PERF_TEST_CLASS_CHECKSUM, testSet, "Checksum ALG_ISO3309_CRC32 Checksum_doFinal()") > -1);
        
    }     
    
   @Test
    void perftest_testClass_KeyPair() throws Exception {
        perfTesting.m_perfResultsFile = (bTestRealCards) ? perfTesting.m_cardManager.establishConnection(null) : perfTesting.m_cardManager.establishConnection(AlgTestSinglePerApdu.class);   
        assertNotEquals(perfTesting.m_perfResultsFile, null);


        // BUGBUG: other types from KeyPair
        
        // Prepare test
        TestSettings testSet = null;
        testSet = perfTesting.prepareTestSettings(Consts.CLASS_KEYPAIR, JCConsts.KeyPair_ALG_RSA_CRT, JCConsts.KeyPair_ALG_RSA_CRT, JCConsts.KeyBuilder_LENGTH_RSA_1024, JCConsts.KeyPair_genKeyPair, 
                Consts.UNUSED, Consts.UNUSED, Consts.UNUSED, (short) 1, (short) 1, (short) 1);      

        // BUGBUG: ALG_RSA, ALG_DSA, ALG_EC_F2M, ALG_EC_FP
        
        // Test single execution of operation
        testSet.numRepeatWholeMeasurement = 2;  // TODO: measure repeatedly
        testSet.algorithmMethod = JCConsts.KeyPair_genKeyPair;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_KEYPAIR, Consts.INS_PERF_TEST_CLASS_KEYPAIR, testSet, "KeyPair ALG_RSA_CRT LENGTH_RSA_1024 KeyPair_genKeyPair()") > -1);
    }        
    
   @Test
    void perftest_testClass_KeyAgreement() throws Exception {
        perfTesting.m_perfResultsFile = (bTestRealCards) ? perfTesting.m_cardManager.establishConnection(null) : perfTesting.m_cardManager.establishConnection(AlgTestSinglePerApdu.class);   
        assertNotEquals(perfTesting.m_perfResultsFile, null);


        // BUGBUG: other types from KeyAgreement
        
        // Prepare test
        TestSettings testSet = null;
        testSet = perfTesting.prepareTestSettings(Consts.CLASS_KEYAGREEMENT, JCConsts.KeyAgreement_ALG_EC_SVDP_DH, JCConsts.KeyPair_ALG_EC_FP, JCConsts.KeyBuilder_LENGTH_EC_FP_192, JCConsts.KeyAgreement_init, 
                Consts.UNUSED, Consts.UNUSED, Consts.UNUSED, (short) 1, (short) 1, (short) 1);      

        // BUGBUG: ALG_EC_SVDP_DHC, ALG_EC_SVDP_DH_PLAIN, ALG_EC_SVDP_DHC_PLAIN
        
        testSet.numRepeatWholeMeasurement = 2;  
        testSet.algorithmMethod = JCConsts.KeyAgreement_init;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_KEYAGREEMENT, Consts.INS_PERF_TEST_CLASS_KEYAGREEMENT, testSet, "CLASS_KEYAGREEMENT ALG_EC_SVDP_DH LENGTH_EC_FP_192 KeyAgreement_init()") > -1);
        testSet.algorithmMethod = JCConsts.KeyAgreement_generateSecret;
        assertTrue(perfTesting.perftest_measure(Consts.CLA_CARD_ALGTEST, Consts.INS_PREPARE_TEST_CLASS_KEYAGREEMENT, Consts.INS_PERF_TEST_CLASS_KEYAGREEMENT, testSet, "CLASS_KEYAGREEMENT ALG_EC_SVDP_DH LENGTH_EC_FP_192 KeyAgreement_generateSecret()") > -1);
    }     

   @Test
    void perftest_HOTP() throws Exception {
        perfTesting.m_perfResultsFile = (bTestRealCards) ? perfTesting.m_cardManager.establishConnection(null) : perfTesting.m_cardManager.establishConnection(AlgTestSinglePerApdu.class);   
        assertNotEquals(perfTesting.m_perfResultsFile, null);

        perfTesting.testSWAlg_HOTP("HOTPMeasurement", 1);
    }        
    @Test
    void debug() throws Exception { 
        perfTesting.m_perfResultsFile = (bTestRealCards) ? perfTesting.m_cardManager.establishConnection(null) : perfTesting.m_cardManager.establishConnection(AlgTestSinglePerApdu.class);   
        perfTesting.testSignatureWithKeyClass(JCConsts.KeyPair_ALG_RSA, JCConsts.KeyBuilder_TYPE_RSA_PRIVATE, JCConsts.KeyBuilder_LENGTH_RSA_512,JCConsts.Signature_ALG_RSA_SHA_ISO9796,"ALG_RSA LENGTH_RSA_512 ALG_RSA_SHA_ISO9796", (short) 1, (short) 1);
    }
}
