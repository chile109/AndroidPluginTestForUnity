using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class Switchmanager : MonoBehaviour {
	
	public void Jump(string where)
	{
		SceneManager.LoadSceneAsync(where);
	}
}
