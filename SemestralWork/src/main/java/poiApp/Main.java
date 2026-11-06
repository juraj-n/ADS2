package poiApp;

import poiApp.presenter.PoiAppNavigator;

public class Main {
    public static void main(String[] args) {
        //PoiAppPresenter presenter = new PoiAppPresenter();
        PoiAppNavigator navigator = new PoiAppNavigator();
        navigator.startApp();
    }
}
