package com.dexels.navajo.studio.eclipse;


/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version 1.0
 *
 * $Id$
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.zip.ZipException;

import org.dexels.utils.JarResources;
import org.dexels.utils.MultiClassLoader;

/**
* This class is an EXACT copy of NavajoClassLoader. Only difference:
* 
* jarResources is not static.
* 
* In the script plugin I use a different classloader for every project,
* so the static version will not work.

 */

class JarFilter implements FilenameFilter {
    public boolean accept(File f, String name) {
        if (name.endsWith("jar"))
            return true;
        else
            return false;
    }
}


class BetaJarFilter implements FilenameFilter {
    public boolean accept(File f, String name) {
        if (name.endsWith("jar_beta"))
            return true;
        else
            return false;
    }
}


public class DynamicClassLoader extends MultiClassLoader {

    private String adapterPath = "";
    private String compiledScriptPath = "";
    private static Object mutex1 = new Object();
    private static Object mutex2 = new Object();
    private HashSet jarResources = null;

    /**
     * beta flag denotes whether beta versions of jar files should be used (if present).
     */
    private boolean beta;

    public DynamicClassLoader(String adapterPath, String compiledScriptPath, boolean beta,ClassLoader cl) {
    	super(cl);
    	this.adapterPath = adapterPath;
        this.beta = beta;
        this.compiledScriptPath = compiledScriptPath;
    }

    public DynamicClassLoader(String adapterPath, String compiledScriptPath,ClassLoader cl) {
    	super(cl);
        this.adapterPath = adapterPath;
        this.beta = false;
        this.compiledScriptPath = compiledScriptPath;
    }

    public synchronized void clearCache(String className) {
    }

    public final void clearJarResources() {
        jarResources = null;
       
    }
    
    /**
     * Use clearCache() to clear the Class cache, allowing a re-load of new jar files.
     */
    public final void clearCache() {
        clearJarResources();
      super.clearCache();
      System.err.println("MESSAGE: clearCache() called in NavajoClassLoader");
    }

    /**
     * Use clearScriptCache() to clear the Class cache, but does not reload jar files
     */
    public final void clearScriptCache() {
       super.clearCache();
      System.err.println("MESSAGE: clearScriptCache() called in NavajoClassLoader");
    }
    
    
    /**
     * Get the class definition for a compiled NavaScript.
     * Method is run in synchronized mode to prevent multiple definitions of the same class in case
     * of multiple threads.
     *
     * 1. Try to fetch class from caching HashMap.
     * 2. Try to load class via classloader.
     * 3. Try to read class bytes from .class file, load it and store it in caching HashMap.
     *
     * @param script
     * @return
     * @throws ClassNotFoundException
     */
    public final Class getCompiledNavaScript(String script) throws ClassNotFoundException {

      String className = script;

      Class c = null;
      try {
        c = Class.forName(className, false, this);
        return c;
      }
      catch (Exception cnfe) {
        // Class not found using classloader, try compiled scripts directory.
        c = null;
      }

      synchronized (mutex1) {

          try {
            script = script.replaceAll("\\.", "/");
            String classFileName = this.compiledScriptPath + "/" + script + ".class";
            File fi = new File(classFileName);
            FileInputStream fis = new FileInputStream(fi);
            int size = (int) fi.length();
            byte[] b = new byte[ (int) size];
            int rb = 0;
            int chunk = 0;

            while ( ( (int) size - rb) > 0) {
              chunk = fis.read(b, rb, (int) size - rb);
              if (chunk == -1) {
                break;
              }
              rb += chunk;
            }

            fis.close();

            c = loadClass(b, className, true, false);

            return c;
          }
          catch (Exception e) {
            e.printStackTrace();
            throw new ClassNotFoundException(script);
          }
      }
    }

    /**
     * Always use this method to load a class. It uses the cache first before retrieving the class from a jar resource.
     */
    public Class getClass(String className) throws ClassNotFoundException {
    	//System.err.println("in getClass("+className+")");
        return Class.forName(className, false, this);
    }

    public File [] getJarFiles(String path, boolean beta) {
         File f = new File(adapterPath);
         File [] files = null;
         if (beta)
           files = f.listFiles(new BetaJarFilter());
         else
           files = f.listFiles(new JarFilter());
        return files;
    }

    public final void initializeJarResources() {
      if (jarResources == null) {

    	System.err.println("MESSAGE: INITIALIZING ADAPTER JAR RESOURCES............");
        File[] files = getJarFiles(adapterPath, beta);
        if (files == null) {
          jarResources = null;
          return;
        }

        synchronized (mutex2) {
          if (jarResources == null) {
            jarResources = new HashSet();
            for (int i = 0; i < files.length; i++) {
              JarResources d;
			try {
				d = new JarResources(files[i]);
	              jarResources.add(d);
			} catch (ZipException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            }
          }
        }
      }

    }

    public InputStream getResourceAsStream(String name) {

      //System.err.println("in NavajoClassLoader (v2). getResourceAsStream(" + name + ")");
      initializeJarResources();
      if (jarResources == null) {
        return getSystemClassLoader().getResourceAsStream(name);
      }

      Iterator allResources = jarResources.iterator();
           /// for (int i = 0; i < files.length; i++) {
      	   //System.err.println("NavajoClassLoader: Locating " + name + " in jar file");
           while (allResources.hasNext()) {

             JarResources d = (JarResources) allResources.next();

             try {
             
               //JarResources d = new JarResources(files[i]);
               byte [] resource = d.getResource(name);
               if (resource != null) {
                 return new java.io.ByteArrayInputStream(resource);
               }
             }
             catch (Exception e) {
             }
           }

      //System.err.println("Did not find resource, trying parent classloader....: " + this.getClass().getClassLoader() );
      return this.getClass().getClassLoader().getResourceAsStream(name);
    }


    /**
     * This method loads the class from a jar file.
     * Beta jars are supported if the beta flag is on.
     */
    protected byte[] loadClassBytes(String className) {

        //System.err.println("NavajoClassLoader: in loadClassBytes(), className = " + className);
        // Support the MultiClassLoader's class name munging facility.
        className = formatClassName(className);
        byte[] resource = null;

        initializeJarResources();

        if (jarResources == null) {
          return null;
        }

        // If beta flag is on first check beta versions of jar files before other jars.
//        if (beta) {
//
//
//            for (int i = 0; i < files.length; i++) {
//                try {
//                    //System.err.println("NavajoClassLoader: Locating " + className + " in jar file: " + files[i].getName());
//                    JarResources d = new JarResources(files[i]);
//
//                    resource = d.getResource(className);
//                    if (resource != null) {
//                        break;
//                    }
//                } catch (Exception e) {
//                    //System.err.println("ERROR: " + e.getMessage());
//                }
//            }
//        }


        if (resource == null) {

           Iterator allResources = jarResources.iterator();
           /// for (int i = 0; i < files.length; i++) {
           
           //System.err.println("Message: NavajoClassLoader: Locating " + className + " in jar file");
           while (allResources.hasNext()) {

              JarResources d = (JarResources) allResources.next();

                try {
                    
                    //JarResources d = new JarResources(files[i]);
                    resource = d.getResource(className);

                    if (resource != null) {
                        break;
                    }
                } catch (Exception e) {
                    //System.err.println("ERROR: " + e.getMessage());
                }
            }
        }

        //System.err.println("NavajoClassLoader: resource = " + resource);

        return resource;

    }

    public void finalize() {
        //System.out.println("In NavajoClassLoader finalize(): Killing class loader");
    }

}
