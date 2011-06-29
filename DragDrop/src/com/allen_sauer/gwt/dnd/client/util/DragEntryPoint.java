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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.StyleInjector;

/**
 * Global initialization for gwt-dnd.
 */
class DragEntryPoint implements EntryPoint {

  private static native void setVersion()
  /*-{
    $wnd.$GWT_DND_VERSION = "@GWT_DND_VERSION@";
  }-*/;

  @Override
  public void onModuleLoad() {
    setVersion();
    StyleInjector.injectAtStart(DragClientBundle.INSTANCE.css().getText());
  }

}
