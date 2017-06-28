package com.wz.fuel.mvp.presenter;

import android.text.TextUtils;

import com.wz.fuel.mvp.bean.FuelPriceBean;
import com.wz.fuel.mvp.view.IView;
import com.wz.network.NetClient;
import com.wz.util.WLog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.GET;

/**
 * 获取今日油价
 */
public class FuelPricePresenter extends IPresenter<FuelPriceBean> {

    private static final String TAG = FuelPricePresenter.class.getSimpleName();

    public FuelPricePresenter(IView<FuelPriceBean> iView) {
        super(iView);
    }

    public void queryPrice() {
        mView.showProgressDialog();
        FuelPriceService fuelPriceService = NetClient.createService(FuelPriceService.class);
        fuelPriceService.getFuelPrice()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        List<FuelPriceBean> fuelBeanList = parseHtml(s);
                        mView.onSuccess(fuelBeanList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e.getMessage());
                        mView.hideProgressDialog();
                    }

                    @Override
                    public void onComplete() {
                        mView.hideProgressDialog();
                    }
                });
    }

    public interface FuelPriceService {
        @GET("http://www.bitauto.com/youjia/")
        Observable<String> getFuelPrice();
    }

    private List<FuelPriceBean> parseHtml(String html) {
        List<FuelPriceBean> fuelBeanList = new ArrayList<>();
        Document document = Jsoup.parse(html);
        if (document != null) {
            Elements elements = document.select("div[class=oilTableOut] tbody tr");
            for (Element element : elements) {
                Elements provinceElements = element.select("th a");
                Elements priceElements = element.select("td");

                try {
                    FuelPriceBean fuelBean1 = new FuelPriceBean();
                    fuelBean1.province = provinceElements.get(0).text();
                    fuelBean1.price_gas_89 = getPriceFromStr(priceElements.get(0).text());
                    fuelBean1.price_gas_92 = getPriceFromStr(priceElements.get(1).text());
                    fuelBean1.price_gas_95 = getPriceFromStr(priceElements.get(2).text());
                    fuelBean1.price_diesel_0 = getPriceFromStr(priceElements.get(3).text());
                    fuelBeanList.add(fuelBean1);
                    WLog.d(TAG, fuelBean1.toString());
                    if (provinceElements.size() == 2) {
                        FuelPriceBean fuelBean2 = new FuelPriceBean();
                        fuelBean2.province = provinceElements.get(1).text();
                        fuelBean2.price_gas_89 = getPriceFromStr(priceElements.get(4).text());
                        fuelBean2.price_gas_92 = getPriceFromStr(priceElements.get(5).text());
                        fuelBean2.price_gas_95 = getPriceFromStr(priceElements.get(6).text());
                        fuelBean2.price_diesel_0 = getPriceFromStr(priceElements.get(7).text());
                        WLog.d(TAG, fuelBean2.toString());
                        fuelBeanList.add(fuelBean2);
                    }
                } catch (Exception e) {
                    WLog.e(TAG, e.getMessage(), e);
                }
            }
        }
        return fuelBeanList;
    }

    private float getPriceFromStr(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (str.contains("(")) {
                String tmpStr = str.substring(0, str.indexOf("("));
                return Float.parseFloat(tmpStr);
            } else {
                return Float.parseFloat(str);
            }
        }
        return 0.00f;
    }
}
