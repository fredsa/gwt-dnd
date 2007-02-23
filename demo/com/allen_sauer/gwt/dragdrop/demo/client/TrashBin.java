package com.allen_sauer.gwt.dragdrop.demo.client;

import com.google.gwt.user.client.ui.Widget;

/**
 * Panel which updates its label to display the number of items in trash.
 */
public class TrashBin extends Bin {

  private static final String STYLE_DEMO_TRASHBIN = "demo-trashbin";

  private int count;

  public TrashBin(int width, int height) {
    super(width, height);
  }
  
  public void eatWidget(Widget widget) {
    widget.removeFromParent();
    count++;
    updateText();
  }

  public boolean isWidgetEater() {
    return true;
  }

  protected void onLoad() {
    super.onLoad();
    addStyleName(STYLE_DEMO_TRASHBIN);
  }
  
  protected void updateText() {
    String text;
    if (count == 0) {
      text = "currently empty";
    } else {
      text = "contains " + count + " item" + (count == 1 ? "" : "s");
    }
    setHTML("<b>Trash Bin</b><br>\n" + "(" + text + ")<br>\n<br>\n" + "<i>try dropping something on me</i>");
  }
}
