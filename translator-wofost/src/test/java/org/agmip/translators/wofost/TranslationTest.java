package org.agmip.translators.wofost;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

import org.agmip.util.JSONAdapter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class TranslationTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TranslationTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TranslationTest.class );
    }

    /**
     * Rigourous Test :-)
     * @throws IOException 
     */
    public void test01_Translation() throws IOException
    {
        System.out.println("start");
        
        FileInputStream fstream = new FileInputStream(this.getClass().getResource("/mach_fast.json").getPath());
        DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String json = br.readLine();
        
		Map <String, Object> theMap = JSONAdapter.fromJSON(json);
        
		WofostOutputController wc = new WofostOutputController();
		String outputPath = new File(this.getClass().getResource("/OUTPUT/ACMO_meta.dat").getFile()).getParent();
		wc.writeFiles(outputPath, theMap);
		
		System.out.println("finished");
        
    }
    
    /**
     * Rigourous Test :-)
     * @throws IOException 
     */
    public void test02_ACMOoutput() throws IOException
    {
    	System.out.println("start ACMO");
    	   	
    	WofostACMO wo = new WofostACMO();
    	String sourceFolder = new File(this.getClass().getResource("/OUTPUT/ACMO_meta.dat").getFile()).getParent();
    	String destFolder   = sourceFolder;
    	
    	wo.execute(sourceFolder, destFolder);
    	
    	System.out.println("finished ACMO");
 	
    }
    
}
