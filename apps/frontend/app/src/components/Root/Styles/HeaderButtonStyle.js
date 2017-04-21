/**
 * Created by kwonohbin on 2016. 7. 26..
 */
import {StyleSheet, Platform} from 'react-native'
import { Colors, Metrics, Images as ThemeImages } from '../../../themes';

export default StyleSheet.create({
	navButtonLeft: {
		// marginLeft: Metrics.smallMargin,
		// marginTop: (Metrics.navBarHeight / 2) - (Metrics.icons.small / 2) - Metrics.topBarHeight + Platform.OS == 'android' ? 15 : null,
		marginTop: -(Metrics.navBarHeight / 2),
		marginLeft: -(Metrics.screenWidth / 2),
		width: Metrics.icons.medium,
		backgroundColor: Colors.transparent
	},
	navButtonRight: {
		marginTop: (Metrics.navBarHeight / 2) - (Metrics.icons.small / 2) - Metrics.topBarHeight + Platform.OS == 'android' ? 15 : null,
		marginRight: Metrics.smallMargin,
		width: Metrics.icons.small+2,
		height:Metrics.icons.small,
		backgroundColor: Colors.transparent,
		padding:10
	}
});

let Images = {
	calendar: ThemeImages.calendar,

};

export {Images};
