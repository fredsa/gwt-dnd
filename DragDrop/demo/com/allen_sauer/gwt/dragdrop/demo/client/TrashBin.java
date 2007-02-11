package com.allen_sauer.gwt.dragdrop.demo.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * Panel which updates its label to display the number of items in trash.
 */
public class TrashBin extends HTML {

  private static final String STYLE_TRASHBIN = "trashbin";

  private int count;

  public TrashBin(int width, int height) {
    setPixelSize(width, height);
    updateText();
  }

  public void eatWidget(Widget widget) {
    widget.removeFromParent();
    count++;
    updateText();
  }

  protected void onLoad() {
    super.onLoad();
    addStyleName(STYLE_TRASHBIN);
  }

  private void updateText() {
    String text;
    if (count == 0) {
      text = "currently empty";
    } else {
      text = "contains " + count + " item" + (count == 1 ? "" : "s");
    }
    setHTML("<b>Trash Bin</b><br>\n" + "(" + text + ")<br>\n<br>\n" + "<i>try dropping something on me</i>");
  }

}
