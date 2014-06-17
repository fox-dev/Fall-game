package com.me.TweenAccessors;

import aurelienribon.tweenengine.TweenAccessor;

public class ValueAccessor implements TweenAccessor<Value>
{
	public int getValues(Value target, int tweenType, float[] returnValues)
	{
		returnValues[0] = target.getValue();
		return 1;
	}
	
	public void setValues(Value target, int tweenType, float[] newValues)
	{
		target.setValue(newValues[0]);
	}
}
