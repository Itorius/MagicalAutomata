package com.thelastcog.magicalautomata.utils;

public class MathUtils
{
	public static int clamp(int value, int min, int max)
	{
		if (value < min)
			value = min;
		else if (value > max)
			value = max;
		return value;
	}
}
