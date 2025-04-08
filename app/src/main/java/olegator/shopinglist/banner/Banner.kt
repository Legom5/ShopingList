package olegator.shopinglist.banner

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mobile.ads.banner.AdSize
import com.yandex.mobile.ads.banner.BannerAdView
import kotlinx.coroutines.delay
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


@Composable
fun Banner(id: Int) {


    AndroidView(factory = {context->


        BannerAdView(context).apply {


            setAdUnitId(context.getString(id))
            setAdSize(AdSize.stickySize(context, 300))
            val adRequest = com.yandex.mobile.ads.common.AdRequest.Builder().build()
            Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate({
                // your code here

                loadAd(adRequest)
            }, 0, 6, TimeUnit.SECONDS)




        }


    })


}

