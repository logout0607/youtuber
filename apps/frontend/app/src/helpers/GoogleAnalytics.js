/**
 * Created by parkjemin on 2017. 4. 17..
 */
import {GoogleAnalyticsTracker, GoogleAnalyticsSettings} from 'react-native-google-analytics-bridge';

let tracker = null;

export function getTracker() {
    if(tracker == null) {
        tracker = new GoogleAnalyticsTracker('UA-97509397-1');

        GoogleAnalyticsSettings.setDispatchInterval(20);
        GoogleAnalyticsSettings.setDryRun(false); //true: release에서만 적용, false: debug 모드에서도 적용
    }

    return tracker;
}