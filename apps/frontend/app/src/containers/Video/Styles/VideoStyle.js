/**
 * Created by kwonohbin on 2016. 7. 26..
 */
import { StyleSheet, Platform } from 'react-native'
import { Metrics, Colors } from '../../../themes';

export default StyleSheet.create({
	container: {
		flex: 1,
		backgroundColor: Colors.white
	},
	youtube: {
		width: Metrics.screenWidth,
		height: (Metrics.screenWidth / 16 * 9),
		// top: Metrics.screenWidth/2
	},
	youtubeVideo: {
		backgroundColor: Colors.black,
		alignItems: 'center',
		justifyContent: 'center'
	}
});
