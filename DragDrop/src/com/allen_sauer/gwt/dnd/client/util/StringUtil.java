/*
 * Copyright 2009 Fred Sauer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.allen_sauer.gwt.dnd.client.util;

/**
 * Shared String utility methods.
 */
public class StringUtil {

  /**
   * Determined a short name from a class.
   * @param clazz the Java class
   * @return a short name for the class
   */
  public static String getShortTypeName(Class<?> clazz) {
    String typeName = clazz.getName();
    return typeName.substring(typeName.lastIndexOf('.') + 1);
  }

  /**
   * Return short classname of <code>obj</code>.
   * 
   * @param obj the object whose name is to be determined
   * @return the short class name
   */
  public static String getShortTypeName(Object obj) {
    String typeName = obj.getClass().getName();
    return typeName.substring(typeName.lastIndexOf('.') + 1);
  }
}
