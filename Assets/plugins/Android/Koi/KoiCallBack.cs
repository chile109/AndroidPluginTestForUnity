using UnityEngine;
using System.Collections;
using com.grepgame.android.plugin;
public class KoiCallBack : MonoBehaviour {

    void KoiCamera_PickDone(string param)
    {
        Koi.getInstance().handleImage(param);
    }

    void KoiGallery_PickDone(string param)
    {

        Koi.getInstance().handleImage(param);
    }

   
}
