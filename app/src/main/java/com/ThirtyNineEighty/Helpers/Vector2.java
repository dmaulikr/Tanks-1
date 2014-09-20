package com.ThirtyNineEighty.Helpers;

/*
 * Operation with prefix get - immutable;
 */
public class Vector2 extends Vector
{
  public static Vector2 xAxis = new Vector2(1.0f, 0.0f);
  public static Vector2 yAxis = new Vector2(0.0f, 1.0f);
  public static Vector2 invertXAxis = new Vector2(-1.0f,  0.0f);
  public static Vector2 invertYAxis = new Vector2( 0.0f, -1.0f);

  protected float[] value;

  public Vector2()
  {
    this(0, 0);
  }

  public Vector2(float x, float y)
  {
    value = new float[2];
    setFrom(x, y);
  }

  public Vector2(float[] raw)
  {
    value = raw;
  }

  public Vector2(Vector2 vec)
  {
    value = new float[2];
    setFrom(vec.getX(), vec.getY());
  }

  public void setFrom(float x, float y)
  {
    value[0] = x;
    value[1] = y;
  }

  public void setFrom(Vector2 vec)
  {
    setFrom(vec.getX(), vec.getY());
  }

  public float getX() { return value[0]; }
  public float getY() { return value[1]; }

  public void setX(float v) { value[0] = v; }
  public void setY(float v) { value[1] = v; }

  public void addToX(float v) { value[0] += v; }
  public void addToY(float v) { value[1] += v; }

  public float[] getRaw() { return value; }

  public float getLength()
  {
    double powX = Math.pow(getX(), 2);
    double powY = Math.pow(getY(), 2);

    return (float)Math.sqrt(powX + powY);
  }

  public float getAngle(Vector2 other)
  {
    float scalar = getScalar(other);
    float lengthOne = this.getLength();
    float lengthTwo = other.getLength();
    float angle = (float)Math.toDegrees(Math.acos(scalar / (lengthOne * lengthTwo)));

    return getCross(other) > 0 ? angle : 360 - angle;
  }

  public float getScalar(Vector2 other)
  {
    float multOne   = getX() * other.getX();
    float multTwo   = getY() * other.getY();

    return multOne + multTwo;
  }

  public float getCross(Vector2 other)
  {
    return getX() * other.getY() - getY() * other.getX();
  }

  public void normalize()
  {
    float length = getLength();

    value[0] /= length;
    value[1] /= length;
  }

  public void subtract(Vector2 other)
  {
    setFrom(getX() - other.getX(), getY() - other.getY());
  }

  public Vector2 getNormalize()
  {
    Vector2 result = new Vector2(this);
    result.normalize();
    return result;
  }

  public Vector2 getSubtract(Vector2 other)
  {
    Vector2 result = new Vector2(this);
    result.subtract(other);
    return result;
  }

  @Override
  public boolean equals(Object o)
  {
    Vector2 other = o instanceof Vector2 ? (Vector2)o : null;

    return other != null
      && Math.abs(other.getX() - getX()) < epsilon
      && Math.abs(other.getY() - getY()) < epsilon;
  }

  @Override
  public String toString()
  {
    return String.format("{%f; %f}", value[0], value[1]);
  }

  @Override
  public float get(int num)
  {
    return value[num];
  }

  @Override
  public void set(int num, float v)
  {
    value[num] = v;
  }
}