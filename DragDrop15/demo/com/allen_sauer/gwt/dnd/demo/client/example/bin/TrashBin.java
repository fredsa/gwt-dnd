/*
 * Copyright 2008 Fred Sauer
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
package com.allen_sauer.gwt.dnd.demo.client.example.bin;

import com.google.gwt.user.client.ui.Widget;

/**
 * Panel which updates its label to display the number of items in the trash.
 */
final class TrashBin extends Bin {

  private static final String CSS_DEMO_TRASHBIN = "demo-trashbin";

  private static final String CSS_DEMO_TRASHBIN_ENGAGE = "demo-trashbin-engage";

  private int count;

  public TrashBin(int width, int height) {
    super(width, height);
    addStyleName(CSS_DEMO_TRASHBIN);
  }

  @Override
  public void eatWidget(Widget widget) {
    widget.removeFromParent();
    count++;
    updateText();
  }

  @Override
  public boolean isWidgetEater() {
    return true;
  }

  @Override
  public void setEngaged(boolean engaged) {
    if (engaged) {
      addStyleName(CSS_DEMO_TRASHBIN_ENGAGE);
    } else {
      removeStyleName(CSS_DEMO_TRASHBIN_ENGAGE);
    }
  }

  @Override
  protected void updateText() {
    String text;
    if (count == 0) {
      text = "currently empty";
    } else {
      text = "contains " + count + " item" + (count == 1 ? "" : "s");
    }
    setHTML("<b>Trash Bin</b><br>\n" + "(" + text + ")<br>\n<br>\n"
        + "<i>try dropping something on me</i>");
  }
}
