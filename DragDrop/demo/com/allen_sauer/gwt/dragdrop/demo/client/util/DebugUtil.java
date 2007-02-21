package com.allen_sauer.gwt.dragdrop.demo.client.util;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

/**
 * Utility class for debugging.
 */
public class DebugUtil {

  private static final int UPDATE_INTERVAL_MILLIS = 500;

  private static FlexTable debugTable = new FlexTable();
  private static TextArea debugTextArea;
  private static String debugText = "";
  private static boolean dirty = false;
  private static Timer timer;

  static {
    timer = new Timer() {

      public void run() {
        if (dirty) {
          dirty = false;
          debugTextArea.setText(debugText);
          //        debugTextArea.setScrollPosition(1000000);
          if (!debugTable.isAttached()) {
            RootPanel.get().add(debugTable, 5, 5);
          }
        }
        schedule(UPDATE_INTERVAL_MILLIS);
      }
    };
  }

  public static void debug(String text) {
    if (debugTextArea == null) {
      debugTextArea = new TextArea();
      debugTable.addStyleName("debug");
      debugTextArea.setWidth("700px");
      debugTextArea.setHeight("6em");
      DOM.setStyleAttribute(debugTextArea.getElement(), "whiteSpace", "pre");

      final Label header = new Label("DEBUG");
      header.addStyleName("debug-header");
      debugTable.setWidget(0, 0, header);
      debugTable.setWidget(1, 0, debugTextArea);
      debugTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
      timer.run();

      header.addMouseListener(new MouseListenerAdapter() {

        private boolean dragging = false;
        int dragStartX;
        int dragStartY;

        public void onMouseDown(Widget sender, int x, int y) {
          dragging = true;
          DOM.setCapture(header.getElement());
          dragStartX = x;
          dragStartY = y;
        }

        public void onMouseMove(Widget sender, int x, int y) {
          if (dragging) {
            int absX = x + debugTable.getAbsoluteLeft();
            int absY = y + debugTable.getAbsoluteTop();
            RootPanel.get().setWidgetPosition(debugTable, absX - dragStartX, absY - dragStartY);
          }
        }

        public void onMouseUp(Widget sender, int x, int y) {
          dragging = false;
          DOM.releaseCapture(header.getElement());
        }
      });
    }
    debugText = debugText + (text.replaceAll("\t", "    ")) + "\n";
    dirty = true;
    System.err.println(text);
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
}
