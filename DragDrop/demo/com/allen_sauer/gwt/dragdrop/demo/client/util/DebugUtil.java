package com.allen_sauer.gwt.dragdrop.demo.client.util;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Utility class for debugging.
 */
public class DebugUtil {

  private static HTML debugHTML;
  private static String debugText = "";
  private static ScrollPanel scrollPanel;

  public static void debug(String text) {
    if (scrollPanel == null) {
      scrollPanel = new ScrollPanel();
      scrollPanel.addStyleName("debug");
      scrollPanel.setWidth("95%");
      scrollPanel.setHeight("80%");

      debugHTML = new HTML();
      debugHTML.setWidth("100%");
      debugHTML.setHeight("100%");
      DOM.setStyleAttribute(debugHTML.getElement(), "whiteSpace", "pre");
      scrollPanel.add(debugHTML);

      debugHTML.addMouseListener(new MouseListenerAdapter() {

        private boolean dragging = false;
        int dragStartX;
        int dragStartY;

        public void onMouseDown(Widget sender, int x, int y) {
          dragging = true;
          DOM.setCapture(debugHTML.getElement());
          dragStartX = x;
          dragStartY = y;
        }

        public void onMouseMove(Widget sender, int x, int y) {
          if (dragging) {
            int absX = x + scrollPanel.getAbsoluteLeft();
            int absY = y + scrollPanel.getAbsoluteTop();
            RootPanel.get().setWidgetPosition(scrollPanel, absX - dragStartX, absY - dragStartY);
          }
        }

        public void onMouseUp(Widget sender, int x, int y) {
          dragging = false;
          DOM.releaseCapture(debugHTML.getElement());
        }
      });
    }
    System.err.println(text);
    debugText = debugText + "\n" + text;
    debugHTML.setHTML(("DEBUG:\n\n" + debugText).replaceAll("\n", "<br>").replaceAll("\t", "    "));
    scrollPanel.setScrollPosition(1000000);
    center(scrollPanel, RootPanel.get());
  }

  public static void debug(Throwable ex) {
    StackTraceElement[] stackTraceElements = ex.getStackTrace();
    String text = new String(ex.toString() + "\n");
    for (int i = 0; i < stackTraceElements.length; i++) {
      text = text + "  at " + stackTraceElements[i] + "\n";
    }
    System.err.println(text);
    debug(text);
  }

  private static void center(Widget widget) {
    AbsolutePanel absolutePanel = (AbsolutePanel) widget.getParent();
    int left = (absolutePanel.getOffsetWidth() - widget.getOffsetWidth()) / 2;
    int top = (absolutePanel.getOffsetHeight() - widget.getOffsetHeight()) / 2;
    absolutePanel.setWidgetPosition(widget, left, top);
  }

  private static void center(Widget widget, AbsolutePanel panel) {
    panel.add(widget, 0, 0);
    center(widget);
  }

}
