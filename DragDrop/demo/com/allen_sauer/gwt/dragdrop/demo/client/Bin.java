package com.allen_sauer.gwt.dragdrop.demo.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * Panel which updates its label to display the number of items in trash.
 */
public class Bin extends HTML {

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
