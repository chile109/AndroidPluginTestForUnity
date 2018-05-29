using UnityEngine;
using UnityEngine.UI;

public class UnityPlayerPlugin : MonoBehaviour {
	
	public string className = "com.example.unity.FragmentDemo";  
    public Text callbackText = null;  
    public Text resultText = null;  
    private AndroidJavaObject pluginObject = null;  
  
    void Start()  
    {  
#if UNITY_ANDROID && !UNITY_EDITOR  
        pluginObject = new AndroidJavaClass(className).CallStatic<AndroidJavaObject>("GetInstance", gameObject.name);  
        pluginObject.Call("SayHello");                                                                                 
        resultText.text = pluginObject.Call<int>("CalculateAdd", 22, 33).ToString();   
#endif  
    }  
  
    public void PluginCallBack(string text)  
    {  
        callbackText.text = text;  
    }  
}
