package com.allen_sauer.gwt.dragdrop.demo.client;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.DragController;

/**
 * Allows the user to select the desired drag behavior
 * for this demonstration.
 */
public class BehaviorPanel extends SimplePanel {

  public BehaviorPanel(final DragController dragController) {
    VerticalPanel verticalPanel = new VerticalPanel();
    final RadioButton classicButton = newButton("Classic", "widget is directly draggable");
    final RadioButton proxyButton = newButton("Proxy", "a separate proxy widget is dragged");

    verticalPanel.add(new Label("You may select your preferred drag behavior..."));
    verticalPanel.add(classicButton);
    verticalPanel.add(proxyButton);

    if (dragController.isDragProxyEnabled()) {
      proxyButton.setChecked(true);
    } else {
      classicButton.setChecked(true);
    }

    ClickListener listener = new ClickListener() {
      public void onClick(Widget sender) {
        dragController.setDragProxyEnabled(proxyButton.isChecked());
      }
    };

    classicButton.addClickListener(listener);
    proxyButton.addClickListener(listener);
    
    setWidget(verticalPanel);
  }

  protected void onLoad() {
    super.onLoad();
    addStyleName("demo-behavior-selection");
  }

  private RadioButton newButton(String name, String description) {
    return new RadioButton("behavior", "<b>" + name + "</b>: <i>" + description + "</i>", true);
  }
}
