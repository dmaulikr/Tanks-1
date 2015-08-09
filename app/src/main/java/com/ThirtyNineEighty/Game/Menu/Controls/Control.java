package com.ThirtyNineEighty.Game.Menu.Controls;

import com.ThirtyNineEighty.Renderable.GL.GLSprite;
import com.ThirtyNineEighty.Renderable.IRenderable;
import com.ThirtyNineEighty.System.Bindable;
import com.ThirtyNineEighty.System.GameContext;

import java.util.ArrayList;

public abstract class Control
  extends Bindable
  implements IControl
{
  private int pointerId;
  private Runnable clickListener;
  private ArrayList<GLSprite> renderables;

  protected Control()
  {
    pointerId = -1;
    renderables = new ArrayList<>();
  }

  @Override
  public void uninitialize()
  {
    super.uninitialize();

    for (IRenderable renderable : renderables)
      GameContext.renderer.remove(renderable);
  }

  protected void addRenderable(GLSprite renderable)
  {
    if (!isInitialized())
      throw new IllegalStateException("control not initialized");

    renderables.add(renderable);
    GameContext.renderer.add(renderable);
  }

  public void setVisible(boolean value)
  {
    for (GLSprite renderable : renderables)
      renderable.setVisible(value);
  }

  public boolean isVisible()
  {
    if (renderables.size() == 0)
      return false;
    IRenderable first = renderables.get(0);
    return first.isVisible();
  }

  @Override
  public final void processDown(int pointerId, float x, float y)
  {
    if (canProcess(x, y) && this.pointerId == -1)
    {
      this.pointerId = pointerId;
      onDown(x, y);
    }
  }

  @Override
  public final void processMove(int pointerId, float x, float y)
  {
    if (this.pointerId == pointerId)
      onMove(x, y);
  }

  @Override
  public final void processUp(int pointerId, float x, float y)
  {
    if (this.pointerId == pointerId)
    {
      this.pointerId = -1;

      onUp(x, y);

      if (clickListener != null)
      {
        // execute on Update thread
        GameContext.content.postEvent(clickListener);
      }
    }
  }

  protected void onDown(float x, float y) { }
  protected void onMove(float x, float y) { }
  protected void onUp(float x, float y) { }

  protected abstract boolean canProcess(float x, float y);

  public void setClickListener(Runnable listener) { clickListener = listener; }
}
