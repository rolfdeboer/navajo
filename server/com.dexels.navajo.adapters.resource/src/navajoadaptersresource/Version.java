package navajoadaptersresource;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.adapter.resource.bundle.ResourceAdapterLibrary;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.functions.util.FunctionFactoryInterface;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Dexels BV</p>
 * @author
 * @version $Id$.
 *
 * DISCLAIMER
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL DEXELS BV OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */


public class Version extends com.dexels.navajo.version.AbstractVersion {


	private static BundleContext bundleContext;
	
	public Version() {
	}


	// TODO Refactor into more reusable code

	
	@Override
	public void start(BundleContext bc) throws Exception {
		super.start(bc);
		bundleContext = bc;
		try {
			FunctionFactoryInterface fi= FunctionFactoryFactory.getInstance();
			fi.init();
			
			fi.clearFunctionNames();


			ResourceAdapterLibrary library = new ResourceAdapterLibrary();
			fi.injectExtension(library);
			for(String adapterName: fi.getAdapterNames(library)) {
//			FunctionDefinition fd = fi.getAdapterDefinition(adapterName,extensionDef);
				fi.getAdapterConfig(library).get(adapterName);
//			FunctionDefinition fd = fi.getDef(extensionDef, adapterName);
				
				String adapterClass = fi.getAdapterClass(adapterName,library);
				Class<?> c = null;
				
				try {
					c = Class.forName(adapterClass);
					 Dictionary<String, Object> props = new Hashtable<String, Object>();
					 props.put("adapterName", adapterName);
					 props.put("adapterClass", c.getName());

					if(adapterClass!=null) {
						context.registerService(Class.class.getName(), c, props);
					}
				} catch (Exception e) {
					logger.error("Error loading class for adapterClass: "+adapterClass,e);
				}
				
			}
		} catch (Throwable e) {
			logger.error("Trouble starting NavajoAdapters bundle",e);
			e.printStackTrace();
		}
	}
	
	@Override
	public void shutdown() {
	}


	public static BundleContext getDefaultBundleContext() {
		return bundleContext;
	}
	

}