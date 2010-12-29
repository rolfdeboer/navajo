/*
 * Created on Apr 4, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;

import com.dexels.navajo.studio.script.plugin.NavajoPluginException;
import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FindJavaSource extends BaseNavajoAction {

    public FindJavaSource() {
        super();
    }

    public void run(IAction action) {
        try {
            IFile scriptFile = NavajoScriptPluginPlugin.getDefault().getCompiledScriptFile(file.getProject(), scriptName);
            if (scriptFile != null) {
                //                System.err.println("not null");
                //                System.err.println("TML: "+tmlFile.getFullPath().toString());
                if (scriptFile.exists()) {
                    //                    System.err.println("And it exists");
                    NavajoScriptPluginPlugin.getDefault().openInEditor(scriptFile);
                } else {
                    NavajoScriptPluginPlugin.getDefault().showInfo("Java file for: "+scriptName+" not found. Make sure the script compiled properly.");
                }
            }else {
                NavajoScriptPluginPlugin.getDefault().showInfo("Java file for: "+scriptName+" not found. Make sure the script compiled properly.\n("+scriptFile.getFullPath()+")");
            }
        } catch (NavajoPluginException e) {
            NavajoScriptPluginPlugin.getDefault().log("Error finding java source ",e);
        }
    }

}
