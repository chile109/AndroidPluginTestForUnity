using UnityEngine;
using UnityEngine.UI;

public class UnityPlayerPlugin : MonoBehaviour {
	
	public string className = "com.example.unity.FragmentDemo";  
    public Text callbackText = null;  
    public Text resultText = null;

	public InputField cal_1;
	public InputField cal_2;
    private AndroidJavaObject pluginObject = null;  
  
    public void Calculate()  
    {
		int a = int.Parse(cal_1.text);
		int b = int.Parse(cal_2.text);
#if UNITY_ANDROID && !UNITY_EDITOR  
        pluginObject = new AndroidJavaClass(className).CallStatic<AndroidJavaObject>("GetInstance", gameObject.name);  
        pluginObject.Call("SayHello");                                                                                 
        resultText.text = pluginObject.Call<int>("CalculateAdd", a, b).ToString();   
#endif  
    }  
  
    public void PluginCallBack(string text)  
    {  
        callbackText.text = text;  
    }  
}
