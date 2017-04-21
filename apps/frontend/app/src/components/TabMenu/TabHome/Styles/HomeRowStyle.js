/**
 * Created by moojae on 2017. 4. 18..
 */
import {StyleSheet} from 'react-native'
import {Colors} from '../../../../themes';

export default StyleSheet.create({
	renderRowContainer: {
		flex: 1
	},
	renderRowTitle: {
		marginTop: 5,
		color: Colors.black
	},
	renderRowInfo: {
		fontSize: 10,
		color: Colors.darkGray
	},
	renderRowThumbnailImage: {
		resizeMode: 'contain',
		position: 'absolute',
		left: 20,
		right: 0,
		bottom: 0,
	},
});
