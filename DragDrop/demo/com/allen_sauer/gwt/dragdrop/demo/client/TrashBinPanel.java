package com.allen_sauer.gwt.dragdrop.demo.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Panel which updates its label to display the number of items in trash.
 */
public class TrashBinPanel extends SimplePanel {

  private int count;

  public TrashBinPanel(int width, int height) {
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
    addStyleName("trashbin");
  }

  private void updateText() {
    String text;
    if (count == 0) {
      text = "currently empty";
    } else {
      text = "contains " + count + " item" + (count == 1 ? "" : "s");
    }
    setWidget(new HTML("<b>Trash Bin</b><br>\n" + "(" + text + ")<br>\n<br>\n"
        + "<i>try dropping something on me</i>", true));
  }

}
