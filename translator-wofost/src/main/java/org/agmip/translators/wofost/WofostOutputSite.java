package org.agmip.translators.wofost;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.agmip.common.Functions;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WofostOutputSite extends WofostOutput {
	
	private VelocityContext context = new VelocityContext();
        private static final Logger LOG = LoggerFactory.getLogger(WofostOutputSite.class);
	
	private void getPutVariable(HashMap<String, String> aMap, String varName, String tag)
	{
		String aVal = getValue(aMap, varName, noValue, true, Section, expName);
		context.put(tag, ensureFloat(aVal));
	}
	
	public void writeFile(String filePath, Map _input) {
    		HashMap<String, String> input = (HashMap<String, String>) _input;
			Velocity.init();        
					
			String siteFilePath = getSiteFilePath(expName, filePath);
			
			context.put( "FILENAME", siteFilePath);
			context.put( "DATE_TIME", new Date().toString());
			
			String soilID = getValue(input, "soil_id", noValue, true, Section, expName);
			String soilFileName = getSoilFileName(soilID);
			context.put( "SOFILE", soilFileName);
			
			// not in icasa list should come from DOME
			getPutVariable(input, "wofost_ssmax", "SSMAX");
			getPutVariable(input, "wofost_ssi", "SSI");
			getPutVariable(input, "wofost_wav", "WAV");
			getPutVariable(input, "wofost_notinf", "NOTINF");
			getPutVariable(input, "wofost_nbase", "NBASE");
			getPutVariable(input, "wofost_nrec", "NREC");
			getPutVariable(input, "wofost_pbase", "PBASE");
			getPutVariable(input, "wofost_prec", "PREC");
			getPutVariable(input, "wofost_kbase", "KBASE");
			getPutVariable(input, "wofost_krec", "KREC");
			getPutVariable(input, "wofost_smlim", "SMLIM");

			// todo; should come from variable ibcl of last soilayer (initial conditions)
			getPutVariable(input, "wofost_rdmsol", "RDMSOL");

			// Write template.        
//			Template template = Velocity.getTemplate(templatePath + "wofost_template.sit");        
			FileWriter F;        
			try {            
				F = new FileWriter(siteFilePath);            
//				template.merge( context, F );            
                                Reader R = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("wofost_template.sit"));
                                Velocity.evaluate(context, F, "wofost_template.sit", R);
				F.close();                    
				} 
			catch (IOException ex) 
			{            
				LOG.error("IO error");
                                LOG.error(Functions.getStackTrace(ex));
			}        
	}
			

}
