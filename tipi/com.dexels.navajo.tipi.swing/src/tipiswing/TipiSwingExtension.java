package tipiswing;

import java.util.Collection;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import navajo.ExtensionDefinition;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.TipiAbstractXMLExtension;
import tipi.TipiExtension;
import tipi.TipiMainExtension;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.swing.functions.TipiSwingFunctionDefinition;
import com.dexels.navajo.tipi.swing.laf.api.LookAndFeelWrapper;
import com.dexels.navajo.tipi.tipixml.XMLParseException;

public class TipiSwingExtension extends TipiAbstractXMLExtension implements
		TipiExtension,TipiMainExtension {

	private static final long serialVersionUID = 3083008630338044274L;

	private static TipiSwingExtension instance = null;

	private static final Logger logger = LoggerFactory.getLogger(TipiSwingExtension.class); 

	
	public TipiSwingExtension() throws XMLParseException {
		instance = this;
	}

	
	@Override
	public void start(BundleContext context) throws Exception {
		registerTipiExtension(context);
		ExtensionDefinition extensionDef = new TipiSwingFunctionDefinition();
		registerAll(extensionDef);
//		FunctionFactoryInterface fi= FunctionFactoryFactory.getInstance();
//		fi.init();
//		fi.clearFunctionNames();
//		fi.injectExtension(extensionDef);
//		if(context!=null) {
//			// OSGi only:
//			for (String functionName : fi.getFunctionNames(extensionDef)) {
//				FunctionDefinition fd = fi.getDef(extensionDef,functionName);
//				 Dictionary<String, Object> props = new Hashtable<String, Object>();
//				 props.put("functionName", functionName);
//				 props.put("functionDefinition", fd);
//				 ServiceRegistration sr = context.registerService(FunctionInterface.class.getName(), fi.instantiateFunctionClass(fd,getClass().getClassLoader()), props);
//				 functionRegs.add(sr);
//			}
//			
//		}
				
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		deregisterTipiExtension(context);
	}
	
	public static TipiSwingExtension getInstance() {
		return instance;
	}
	
	public void initialize(TipiContext tc) {
		// Do nothing

	}

	public LookAndFeelWrapper getLookAndFeel(String tipiLaf) {
		try {
			Collection<ServiceReference<LookAndFeelWrapper>> srl = getBundleContext().getServiceReferences(LookAndFeelWrapper.class, "(className="+tipiLaf+")");
			if(srl.isEmpty()) {
				return null;
			}
			ServiceReference<LookAndFeelWrapper> laf = srl.iterator().next();
			return getBundleContext().getService(laf);
		} catch (InvalidSyntaxException e) {
			logger.error("Odd OSGi error:",e);
		}
		return null;
	}

	public void setLookAndFeel(String tipiLaf) {
		if(getBundleContext()==null) {
			// not OSGi, go vintage:
			try {
				UIManager.setLookAndFeel(tipiLaf);
			} catch (ClassNotFoundException e) {
				logger.error("Error setting look and feel: "+tipiLaf,e);
			} catch (InstantiationException e) {
				logger.error("Error setting look and feel: "+tipiLaf,e);
			} catch (IllegalAccessException e) {
				logger.error("Error setting look and feel: "+tipiLaf,e);
			} catch (UnsupportedLookAndFeelException e) {
				logger.error("Error setting look and feel: "+tipiLaf,e);
			}
			return;
		}
		try {
			Collection<ServiceReference<LookAndFeelWrapper>> srl = getBundleContext().getServiceReferences(LookAndFeelWrapper.class, "(className="+tipiLaf+")");
			if(srl.isEmpty()) {
				return;
			}
			ServiceReference<LookAndFeelWrapper> laf = srl.iterator().next();
			LookAndFeelWrapper lwd = getBundleContext().getService(laf);
			lwd.loadLookAndFeel();
		} catch (InvalidSyntaxException e) {
			logger.error("Odd OSGi error:",e);
		}
	}

}
