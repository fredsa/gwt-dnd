package com.allen_sauer.gwt.dnd.demo.client.example.clicktouch;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.TouchCancelEvent;
import com.google.gwt.event.dom.client.TouchCancelHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.user.client.ui.Button;

public class ClickTouchButton extends Button {

  private int leftClickCount = 0;
  private int middleClickCount = 0;
  private int rightClickCount = 0;
  private int doubleClickCount = 0;

  public ClickTouchButton() {
    super("Draggable button");

    addDoubleClickHandler(new DoubleClickHandler() {
      @Override
      public void onDoubleClick(DoubleClickEvent event) {
        doubleClickCount++;
        setText("Double click count = " + doubleClickCount);
      }
    });

    addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        int nativeButton = event.getNativeButton();
        switch (nativeButton) {
          case NativeEvent.BUTTON_LEFT:
            leftClickCount++;
            setText("LEFT click count = " + leftClickCount);
            break;
          case NativeEvent.BUTTON_MIDDLE:
            middleClickCount++;
            setText("MIDDLE click count = " + middleClickCount);
            break;
          case NativeEvent.BUTTON_RIGHT:
            rightClickCount++;
            setText("RIGHT click count = " + rightClickCount);
            break;
          default:
            setText("Unknown button clicked");
        }
      }
    });

    addTouchStartHandler(new TouchStartHandler() {
      @Override
      public void onTouchStart(TouchStartEvent event) {
        setText("onTouchStart()");
      }
    });

    addTouchEndHandler(new TouchEndHandler() {
      @Override
      public void onTouchEnd(TouchEndEvent event) {
        setText("onTouchEnd()");
      }
    });

    addTouchMoveHandler(new TouchMoveHandler() {
      @Override
      public void onTouchMove(TouchMoveEvent event) {
        setText("onTouchMove()");
      }
    });

    addTouchCancelHandler(new TouchCancelHandler() {
      @Override
      public void onTouchCancel(TouchCancelEvent event) {
        setText("onTouchCancel()");
      }
    });
  }
}
