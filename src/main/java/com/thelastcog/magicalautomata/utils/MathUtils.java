package com.thelastcog.magicalautomata.utils;

public class MathUtils
{
	public static <T extends Number> String ToSI(T value, String format)
	{
		double castValue = value.doubleValue();
		if (format == null)
			format = "%s";

		char[] incPrefixes = { 'k', 'M', 'G', 'T', 'P', 'E', 'Z', 'Y' };
		char[] decPrefixes = { 'm', '\u03bc', 'n', 'p', 'f', 'a', 'z', 'y' };

		if (Math.abs(castValue) > 0.0)
		{
			int degree = (int)Math.floor(Math.log10(Math.abs(castValue)) / 3);
			double scaled = castValue * Math.pow(1000, -degree);

			char prefix = 0;
			switch ((int)(Math.signum(scaled)))
			{
			case 1:
				prefix = incPrefixes[degree - 1];
				break;
			case -1:
				prefix = decPrefixes[-degree - 1];
				break;
			}

			return String.format(format, scaled) + prefix;
		}

		return String.format(format, castValue);
	}
}
