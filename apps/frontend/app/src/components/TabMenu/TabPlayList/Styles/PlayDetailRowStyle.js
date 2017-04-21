/**
 * Created by moojae on 2017. 4. 18..
 */
import {StyleSheet} from 'react-native'
import {Colors} from '../../../../themes';

export default StyleSheet.create({
	renderRowContainer: {
		flex: 1,
		flexDirection: 'row',
		paddingHorizontal: 10,
		paddingVertical: 5,
		top: 5,
		backgroundColor: Colors.white
	},
	renderRowImage: {
		width: 120,
		height: 68,
	},
	renderRowTitleView: {
		flex: 1,
		marginLeft: 10
	},
	renderRowTitle: {
		color: Colors.black
	}
});