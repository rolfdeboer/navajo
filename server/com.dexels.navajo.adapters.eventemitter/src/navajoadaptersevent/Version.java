package navajoadaptersevent;

import navajoextension.AbstractCoreExtension;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.adapter.eventemitter.EventAdapterLibrary;


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


public class Version extends AbstractCoreExtension {


	private static BundleContext bundleContext;
	
	public Version() {
	}


	@Override
	public void start(BundleContext bc) throws Exception {
		super.start(bc);
		bundleContext = bc;
		try {
			EventAdapterLibrary library = new EventAdapterLibrary();
			registerAll(library);
		} catch (Throwable e) {
			logger.error("Trouble starting NavajoEventEmitters bundle",e);
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