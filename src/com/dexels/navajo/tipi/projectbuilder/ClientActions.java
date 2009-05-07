package com.dexels.navajo.tipi.projectbuilder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.WriteAbortedException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.StringTokenizer;
import java.util.TreeMap;

import com.dexels.navajo.tipi.extensionmanager.ExtensionManager;
import com.dexels.navajo.tipi.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.util.XMLElement;
import com.dexels.navajo.tipi.util.XMLParseException;

public class ClientActions {
	
	//public static final String DEFAULT_REPOSITORY = "http://spiritus.dexels.nl:34567/Tipi/";
	
	public static void downloadDemoFiles(String repository, File projectDir) throws IOException {
		File resources = new File(projectDir,"resource");
		File tipi = new File(projectDir,"tipi");
		File settingsDir = new File(projectDir,"settings");
		resources.mkdirs();
		tipi.mkdirs();
		URL baseUrl = new URL(repository);
		URL defProject = new URL(baseUrl,"TemplateProject/");
		URL resource = new URL(defProject,"resource/");
		URL settings = new URL(defProject,"settings/");
		URL tipiUrl = new URL(defProject,"tipi/");
		
		downloadFile(new URL(resource,"icon.png"), "icon.png", resources, false,true);
		downloadFile(new URL(resource,"splash.jpg"), "splash.png", resources, false,true);
		downloadFile(new URL(tipiUrl,"init.xml"), "init.xml", tipi, false,true);
		downloadFile(new URL(settings,"arguments.properties"), "arguments.properties", settingsDir, false,true);
		downloadFile(new URL(settings,"deploy.properties"), "deploy.properties", settingsDir, false,true);
		downloadFile(new URL(settings,"build.xml"), "build.xml", settingsDir, false,true);
		downloadFile(new URL(settings,"template.html"), "template.html", settingsDir, false,true);
		
		InputStream is = new URL(settings,"tipi.properties").openStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		copyResource(baos, is);
		String str = new String(baos.toByteArray());
		String aa = str.replaceAll("#repository#", repository);
		ByteArrayInputStream bais = new ByteArrayInputStream(aa.getBytes());
		FileOutputStream fos = new FileOutputStream(new File(settingsDir,"tipi.properties"));
		copyResource(fos, bais);
		fos.flush();
		fos.close();
		
		

//		downloadFile(new URL(defProject,"tipi.properties"), "tipi.properties", projectDir, false);

	}

	/**
	 * TODO Add versioning
	 * @param repository
	 * @param extensions
	 * @return
	 */
	
	public static List<String> checkExtensions(String repository, String extensions) {
		List<String> missing = new ArrayList<String>();
		try {
			Map<String, List<String>> available = ExtensionManager.getExtensions(repository);
			System.err.println("Available extensions: " + available);
			StringTokenizer st = new StringTokenizer(extensions, ",");
			while (st.hasMoreTokens()) {
				String ext = st.nextToken();
				if (!available.keySet().contains(ext)) {
					missing.add(ext);
				}
			}
		} catch (IOException e1) {
			System.err.println("Can not get extensionlist");
			e1.printStackTrace();
		}
		return missing;
	}

	public static XMLElement getExtensionXml(String extension, String version,String repository) throws IOException {
		try {
			URL rep = new URL(repository);
			URL projectURL = new URL(rep, extension + "/");
			URL versionURL = new URL(projectURL, version + "/");
			URL extensionURL = new URL(versionURL, "definition.xml");
			XMLElement result = getXMLElement(extensionURL);
			return result;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	public static XMLElement getExtensionXml(String extension,String repository) throws IOException {
//		try {
//			URL rep = new URL(repository);
//			URL projectURL = new URL(rep, extension + "/");
//			URL extensionURL = new URL(projectURL, "definition.xml");
//			XMLElement result = getXMLElement(extensionURL);
//			return result;
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	public static void downloadExtensionJars(String project, URL projectURL, XMLElement result, File baseDir, boolean clean)
			throws MalformedURLException {
//		System.err.println("PRoject dir: " + projectURL);
		URL unsigned = new URL(projectURL, "lib/");

		List<XMLElement> jars = result.getAllElementsByTagName("jar");
		File f = new File(baseDir, "lib");
		if (f.exists()) {
			f.delete();
		}
		f.mkdirs();
		for (XMLElement element : jars) {
			String path = element.getStringAttribute("path");
			URL jar = new URL(unsigned, path);
//			System.err.println("URL: " + jar);
			try {
				downloadFile(jar, path, f,clean,false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		XMLElement main = result.getElementByTagName("main");
		if (main != null) {
			String path = main.getStringAttribute("proxyjar");
			URL jar = new URL(unsigned, path);
			try {
				downloadFile(jar, path, f,clean,false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

//	ClientActions.downloadProxyJars(ext,new URL(repository+ext+"/"),extensionXml,projectPath);
	public static void downloadProxyJars(String project, URL projectURL, XMLElement result, File baseDir,boolean clean) throws MalformedURLException {
		System.err.println("PRoject dir: " + projectURL);
		URL unsigned = new URL(projectURL, "lib/");

		File f = new File(baseDir, "lib");
		if (f.exists()) {
			f.delete();
		}
		f.mkdirs();

		XMLElement main = result.getElementByTagName("main");
		if (main != null) {
			String path = main.getStringAttribute("proxyjar");
			URL jar = new URL(unsigned, path);
			try {
				downloadFile(jar, path, f,clean,false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * If clean = false, don't cache, download everything
	 * @param remote
	 * @param path
	 * @param directory
	 * @param clean
	 * @throws IOException
	 */
	public static void downloadFile(URL remote, String path, File directory, boolean clean, boolean dontOverwrite) throws IOException, WriteAbortedException {
		//System.err.println("File: " + directory.getAbsolutePath()+ " url: "+remote);
		directory.mkdirs();
		File file = new File(directory, path);
		if(dontOverwrite && file.exists()) {
			System.err.println("Not overwriting existing file: "+file.getAbsolutePath());
			return;
		}
		InputStream iss = checkNeedsUpdate(file, remote,clean);
		if(iss == null) {
			return;
		}
		FileOutputStream fos = new FileOutputStream(file);
		InputStream is = remote.openStream();
		copyResource(fos, is);
	}

	private static InputStream checkNeedsUpdate(File f, URL jar, boolean clean) throws IOException {
		if(!f.exists()) {
			return jar.openStream();
		}
		long local = f.lastModified();
		URLConnection uc = jar.openConnection();
		long remote = uc.getLastModified();
		if(remote > local || clean) {
			return uc.getInputStream();
		}
//		System.err.println("Skipping: "+jar);
		
		return null;
	}

	public static XMLElement getXMLElement(URL extensionURL) {
//		System.err.println("Getting url: "+extensionURL);
		try {
			XMLElement result = new CaseSensitiveXMLElement();
			InputStream is = extensionURL.openStream();
			Reader r = new InputStreamReader(is);
			result.parseFromReader(r);
			r.close();
			is.close();
			return result;
		} catch (XMLParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void buildJnlp(String projectName, String repository, File inputPath, File outputPath) throws XMLParseException,
			IOException {
		XMLElement xe = new CaseSensitiveXMLElement();
		FileReader fr = new FileReader(inputPath);
		xe.parseFromReader(fr);
		XMLElement output = new CaseSensitiveXMLElement("jnlp");
		buildup(projectName, repository, xe, output);
		FileWriter fw = new FileWriter(outputPath);
		output.write(fw);
		fw.flush();
		fw.close();
	}

	private static void buildup(String projectName, String repository, XMLElement input, XMLElement output) {

		XMLElement information = output.addTagKeyValue("information", "");
		information.addTagKeyValue("vendor", input.getStringAttribute("vendor"));
		information.addTagKeyValue("homepage", input.getStringAttribute("homepage"));
		information.addTagKeyValue("title", input.getStringAttribute("title"));

		output.setAttribute("codebase", repository + "/" + input.getStringAttribute("project"));
		output.setAttribute("href", projectName + ".jnlp");
		XMLElement resources = output.addTagKeyValue("resources", "");
		XMLElement description = input.getElementByTagName("description");
		if (description != null && description.getContent() != null) {
			input.addTagKeyValue("description", description.getContent().trim());
		}

		XMLElement jars = input.getElementByTagName("jars");
		if (jars != null) {
			for (XMLElement jar : jars.getChildren()) {
				if (jar.getName().equals("jar")) {
					String path = jar.getStringAttribute("path");
					String main = jar.getStringAttribute("main");
					XMLElement resource = new CaseSensitiveXMLElement("jar");
					resource.setAttribute("href", path);
					resource.setAttribute("main", main == null ? "false" : true);
					resources.addChild(resource);
				}
				if (jar.getName().equals("remotejnlp")) {
					String path = jar.getStringAttribute("path");
					String name = jar.getStringAttribute("name");
					XMLElement resource = new CaseSensitiveXMLElement("extension");
					resource.setAttribute("href", path);
					resource.setAttribute("name", name);
					resources.addChild(resource);
				}

			}
		}
		XMLElement extensions = input.getElementByTagName("extensions");
		if (extensions != null) {
			for (XMLElement extension : extensions.getChildren()) {
				// <extension name="jogl" href=
				// "http://download.java.net/media/jogl/builds/archive/jsr-231-webstart-current/jogl.jnlp"
				// />
				// <extension name="jogl" href="http://www.jogl.com"/>
//				System.err.println("Copying: " + extension);
				resources.addChild(extension.copy());
			}
		}

		XMLElement requires = input.getElementByTagName("requires");
		if (requires != null) {
			for (XMLElement require : requires.getChildren()) {
				String id = require.getStringAttribute("id");
				String path = require.getStringAttribute("path");
				XMLElement xe = resources.addTagKeyValue("extension", "");
				xe.setAttribute("name", id);
				xe.setAttribute("href", assemblePath(repository, path, id));
			}
		}
		output.addTagKeyValue("component-desc", "");
		output.setAttribute("spec", "1.0+");
	}

	private static String assemblePath(String repository, String path, String id) {
		return repository + "/" + path + "/" + id + ".jnlp";
	}

	protected static final void copyResource(OutputStream out, InputStream in) throws IOException {
		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		byte[] buffer = new byte[1024];
		int read;
		while ((read = bin.read(buffer)) > -1) {
			bout.write(buffer, 0, read);
		}
		bin.close();
		bout.flush();
		bout.close();
	}


	// copied from extensions
	public static Map<String, List<String>> getExtensions(String repository) throws IOException {
		return ExtensionManager.getExtensions(repository);
	
	}
}
