/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dexels.navajo.runtime.osgi.j2ee;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.dexels.navajo.osgi.runtime.FrameworkInstance;

public final class StartupListener
    implements ServletContextListener
{
    private FrameworkInstance service;

    public void contextInitialized(ServletContextEvent event)
    {
        try {
        	event.getServletContext().log("context Initialized");
        	this.service = new WebFrameworkInstance(event.getServletContext());
        	event.getServletContext().log("Instance created");
			this.service.start("");
        	event.getServletContext().log("Service started");
        } catch (Throwable e) {
			event.getServletContext().log("Error starting context", e);
		}
    }

    public void contextDestroyed(ServletContextEvent event)
    {
        this.service.stop();
        // see if this helps TODO think of something better
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}
