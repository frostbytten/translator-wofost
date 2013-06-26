package org.agmip.translators.wofost;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.Map;
import org.agmip.common.Functions;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WofostOutputRunopt extends WofostOutput {
	
        private static final Logger LOG = LoggerFactory.getLogger(WofostOutputRunopt.class);
	public void writeFile(String filePath, Map input) {
		// TODO map all variables of input file with values in input map (json string)
			Velocity.init();        
			VelocityContext context = new VelocityContext();
			
			//outputFileName = String.format("%s.out", expName);
			
			String fName = getRunOptFileName(filePath);
			
			context.put( "FILENAME", fName);
			
			context.put( "DATE_TIME", new Date().toString());
			
			context.put( "TIMFIL", getTimerFileName(expName));
			context.put( "SITFIL", getSiteFileName(expName));
			context.put( "WOFOUT", getOutputFileName(expName, true));
			context.put( "FIXNAM", getOutputFileName(expName, false));
			
			// Write template.        
//			Template template = Velocity.getTemplate(templatePath + "wofost_template.wcc");        
			FileWriter F;        
			try {            
				F = new FileWriter(fName);            
//				template.merge( context, F );         
                                Reader R = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("wofost_template.wcc"));
                                Velocity.evaluate(context, F, "wofost_template.wcc", R);   
				F.close();                    
			} catch (FileNotFoundException e) {
				LOG.error("file not found");
                                LOG.error(Functions.getStackTrace(e));
			} catch (IOException e) {
				LOG.error("IO error");
                                LOG.error(Functions.getStackTrace(e));
			} 
	}

}
