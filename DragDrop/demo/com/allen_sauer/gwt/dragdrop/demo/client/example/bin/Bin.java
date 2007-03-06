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
package com.allen_sauer.gwt.dragdrop.demo.client.example.bin;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * Panel which which does not 'eat' widgets.
 */
class Bin extends HTML {

  private static final String STYLE_DEMO_BIN = "demo-bin";

  public Bin(int width, int height) {
    setPixelSize(width, height);
    updateText();
  }

  public void eatWidget(Widget widget) {
  }

  public boolean isWidgetEater() {
    return false;
  }

  protected void onLoad() {
    super.onLoad();
    addStyleName(STYLE_DEMO_BIN);
  }

  protected void updateText() {
    setHTML("<b>Closed Bin</b><br>\n" + "(does not currently accept trash)<br>\n<br>\n" + "<i>try dropping something on me</i>");
  }
}
