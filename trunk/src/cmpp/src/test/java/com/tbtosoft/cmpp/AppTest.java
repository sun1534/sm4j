package com.tbtosoft.cmpp;

import com.tbtosoft.cmpp.exception.CmppException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     * @throws CmppException 
     */
    public void testApp() throws CmppException
    {
        assertTrue( true );  
        ConnectReqPkg connectReqPkg = new ConnectReqPkg();
        byte[] reulst =connectReqPkg.createAuthenticatorSource("1234", "1234", "0718103806");
        System.out.println(ConnectReqPkg.byteToHexString(reulst));
    }
}
