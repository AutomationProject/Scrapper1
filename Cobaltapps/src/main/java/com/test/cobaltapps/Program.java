
package com.test.cobaltapps;

public class Program
{
	public static String value;
	public static void main()
	{
		String key = "8544174325111181684966f4cc6d4a5383237c6b";
		String img_fn = "images.png";

		System.out.println("Decoding");
		ApiResult ret = BypassCaptchaApi.Submit(key, img_fn);
		if(!ret.IsCallOk)
		{
			System.out.println("Error: " + ret.Error);
			return;
		}

		value = ret.DecodedValue;
		System.out.println("Using the decoded value: " + value);
		System.out.println("Suppose it is correct.");
		ret = BypassCaptchaApi.SendFeedBack(key, ret, true);
		if(!ret.IsCallOk)
		{
			System.out.println("Error: " + ret.Error);
			return;
		}

		ret = BypassCaptchaApi.GetLeft(key);
		if(!ret.IsCallOk)
		{
			System.out.println("Error: " + ret.Error);
			return;
		}

		System.out.println("There are " + ret.LeftCredits + " credits left on this key");
		System.out.println("OK");
	}
}
