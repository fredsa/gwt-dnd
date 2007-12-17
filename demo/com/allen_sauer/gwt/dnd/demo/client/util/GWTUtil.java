/*
 * Copyright 2007 Fred Sauer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.allen_sauer.gwt.dnd.demo.client.util;

import com.allen_sauer.gwt.dnd.demo.client.DragDropDemo;

/**
 * Shared utility methods for examples.
 */
public class GWTUtil {
  private static final String SUBVERSION_TRUNK = "http://gwt-dnd.googlecode.com/svn/trunk/DragDrop/";

  public static String getClassAnchorHTML(Class clazz) {
    String className = getClassName(clazz);
    String url = SUBVERSION_TRUNK;
    if (className.startsWith(DragDropDemo.DEMO_CLIENT_PACKAGE)) {
      url += "demo/";
    } else {
      url += "src/";
    }
    url += className.replace('.', '/') + ".java";
    String baseName = className.substring(className.lastIndexOf('.') + 1);
    return "<code><a target='_blank' href='" + url + "'>" + baseName + ".java</a></code>";
  }

  public static String getPackageName(Class clazz) {
    String className = getClassName(clazz);
    return className.substring(0, className.lastIndexOf('.'));
  }

  private static String getClassName(Class clazz) {
    String className = clazz.toString();
    className = className.replaceFirst(".* ", "");
    return className;
  }
}
